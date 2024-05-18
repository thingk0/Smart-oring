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
    return FactoryMap(factory_map, 77, 37)


def init_robot():
    rm.add_robot(1, 14)
    rm.add_robot(1, 16)
    rm.add_robot(1, 18)
    rm.add_robot(1, 20)
    rm.add_robot(1, 22)
    rm.add_robot(1, 24)
    rm.add_robot(1, 26)
    rm.add_robot(1, 28)
    rm.add_robot(1, 30)
    rm.add_robot(1, 32)
    rm.add_robot(1, 34)
    rm.add_robot(1, 36)
    rm.add_robot(1, 38)
    rm.add_robot(1, 40)
    rm.add_robot(1, 42)
    rm.add_robot(1, 44)
    rm.add_robot(1, 46)
    rm.add_robot(1, 48)
    rm.add_robot(1, 50)
    rm.add_robot(1, 52)
    rm.add_robot(1, 54)
    rm.add_robot(1, 56)
    rm.add_robot(1, 58)
    rm.add_robot(1, 60)


def init_kafka():
    global producer, consumer
    server = env["kafka"]["server"]
    producer = Producer(
        {
            'bootstrap.servers': server,
        }
    )
    consumer = Consumer({
        'bootstrap.servers': server,
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
    factory_map = get_map()
    rm.set_map(factory_map)
    init_robot()

    while True:
        time.sleep(1)
        # rm.print_factory_map()
        now = datetime.datetime.now()
        current_time = now.isoformat()
        current_time_utc = now.astimezone(datetime.timezone.utc).isoformat()
        rm.process_robots()
        send_robot_stat(current_time=current_time, current_time_utc=current_time_utc)
        get_mission()

        while missions:
            mission_assigned: bool = rm.assign_mission(missions[0], current_time=current_time)
            if mission_assigned:
                missions.popleft()
            else:
                break


def send_robot_stat(current_time, current_time_utc):
    robots = rm.get_all_robots()
    for robot in robots:
        current_mission_id = get_current_mission_id(robot)
        robot_stat = {
            "amrId": robot.robot_id,
            "xCoordinate": robot.current_point.x,
            "yCoordinate": robot.current_point.y,
            "battery": 100,
            "amrHistoryCreatedAt": current_time,
            "amrRoute": [[e.x, e.y] for e in robot.get_next_nodes()],
            "amrStatus": robot.robot_status.value,
            "robotEvent": robot.last_event.value,
            "visited_node_until_mission_complete": list(robot.visited_node_until_mission_complete),
            "missionId": current_mission_id,
            "cant_move_duration": robot.cant_move_duration,
            "amrHistoryCreatedAInstant": current_time_utc,
            "currentStopDuration": robot.cant_move_duration,
            "routeRemainingForMission": [[e.x, e.y] for e in robot.get_next_nodes()] if robot.current_mission else None,
            "routeVisitedForMission": [[e.x, e.y] for e in
                                       robot.visited_node_until_mission_complete] if robot.current_mission else None,
            "hasStuff": get_has_stuff(robot),
            "processing_submission_idx": robot.processing_submission_idx,
        }

        producer.produce("amr-history-log", key=str(robot.robot_id),
                         value=json.dumps(robot_stat, default=asdict).encode('utf-8'), )

        if robot.last_event == RobotEvent.COMPLETE_MISSION:
            complete_msg = {
                "id": robot.last_mission.mission_id,
                "amrId": robot.robot_id,
                "missionStartedAt": robot.last_mission_started_at,
                "missionFinishedAt": current_time,
                "missionEstimatedTime": robot.estimated_time_when_mission_first_set,
                "fullPath": [[i.x, i.y] for i in robot.visited_node_until_mission_complete]
            }
            producer.produce("mission-complete", key=str(robot.robot_id),
                             value=json.dumps(complete_msg, default=asdict).encode('utf-8'), )

        producer.flush()


def get_has_stuff(robot):
    has_stuff = robot.current_mission and robot.processing_submission_idx is not None and robot.processing_submission_idx > 0
    return True if has_stuff else False


def get_current_mission_id(robot):
    if not robot.current_mission:
        return None
    return robot.current_mission.mission_id


try:
    start()
finally:
    producer.flush()
    consumer.close()
