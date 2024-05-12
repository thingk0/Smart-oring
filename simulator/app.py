import datetime
import json
import time
import traceback
from collections import deque
from dataclasses import asdict

from confluent_kafka import Producer, Consumer, Message

from app_env import env
from domain.factory_map import FactoryMap
from robot.mission.entity.mission import Mission
from robot.mission.entity.robot import RobotEvent
from robot.robot_manager import RobotManager
from usecase import UseCase

# missions: deque[Mission] = deque([mission_util.get_random_mission(4, 5, 5)])
missions: deque[Mission] = deque([])

rm: RobotManager = RobotManager.instance()
producer: Producer
consumer: Consumer


def get_map():
    factory_map = UseCase.get_map()
    return FactoryMap(factory_map, 100, 50)


def init_robot():
    rm.add_robot(0, 0)
    rm.add_robot(2, 0)
    rm.add_robot(4, 0)


def init_kafka():
    global producer, consumer
    server = env["kafka"]["server"]
    producer = Producer(
        {
            'bootstrap.servers': server,
        }
    )
    consumer = Consumer({
        'bootstrap.servers': env["kafka"]["server"],
        'group.id': 'simulator',
        'auto.offset.reset': 'earliest'
    })
    consumer.subscribe(['mission'])


def get_mission():
    msg: Message = consumer.poll(0.1)
    if msg is None:
        return
    mission_dic = json.loads(msg.value().decode('utf-8'))
    # request to server using requests
    try:
        missions.append(UseCase.get_mission(mission_dic["id"]))
    except Exception as e:
        traceback.print_exception(e)


def start():
    init_kafka()
    init_robot()
    factory_map = get_map()
    rm.set_map(factory_map)

    while True:
        time.sleep(1)
        # rm.print_factory_map()
        current_time = datetime.datetime.now().isoformat()
        rm.process_robots()
        send_robot_stat(current_time=current_time)
        get_mission()

        while missions:
            mission_assigned: bool = rm.assign_mission(missions[0], current_time=current_time)
            if mission_assigned:
                missions.popleft()
            else:
                break


def send_robot_stat(current_time):
    robots = rm.get_all_robots()
    for robot in robots:
        current_mission = get_current_mission(robot)
        robot_stat = {
            "amrId": robot.robot_id,
            "xCoordinate": robot.current_node.x,
            "yCoordinate": robot.current_node.y,
            "battery": 100,
            "amrHistoryCreatedAt": current_time,
            "amrRoute": [[e.x, e.y] for e in robot.get_next_nodes()],
            "amrStatus": robot.robot_status.value,
            "robotEvent": robot.last_event.value,
            "visited_node_until_mission_complete": list(robot.visited_node_until_mission_complete),
            "current_mission": current_mission
        }

        producer.produce("amr-history-log", key=str(robot.robot_id),
                         value=json.dumps(robot_stat, default=asdict).encode('utf-8'), )

        if robot.last_event == RobotEvent.COMPLETE_MISSION:
            complete_msg = {
                "id": robot.robot_id,
                "missionStartedAt": robot.last_mission_started_at,
                "missionFinishedAt": current_time,
                "missionEstimatedTime": len(robot.visited_node_until_mission_complete),
                "fullPath": list(robot.visited_node_until_mission_complete)
            }
            producer.produce("mission-complete", key=str(robot.robot_id),
                             value=json.dumps(complete_msg, default=asdict).encode('utf-8'), )

        producer.flush()


def get_current_mission(robot):
    current_mission = None if not robot.current_mission else {
        "mission_id": robot.current_mission.mission_id
    }
    return current_mission


try:
    start()
finally:
    producer.flush()
    consumer.close()
