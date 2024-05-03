import datetime
import json
import time
from collections import deque

from confluent_kafka import Producer, Consumer, Message

from app_env import env
from domain.factory_map import FactoryMap
from robot.mission import mission_util
from robot.mission.entity.mission import Mission
from robot.robot_manager import RobotManager
from usecase import UseCase

missions: deque[Mission] = deque([mission_util.get_random_mission(4, 5, 5)])
rm: RobotManager = RobotManager.instance()
producer: Producer
consumer: Consumer


def get_map():
    factory_map = UseCase.get_map()
    return FactoryMap(factory_map, 100, 50)


def init_robot():
    rm.add_robot(0, 0)
    rm.add_robot(2, 0)


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


def start():
    init_kafka()
    init_robot()
    factory_map = get_map()
    rm.set_map(factory_map)

    while True:
        time.sleep(1)
        send_robot_stat()
        # rm.print_factory_map()
        rm.process_robots()
        get_mission()

        while missions:
            mission_assigned: bool = rm.assign_mission(missions[0])
            if mission_assigned:
                missions.popleft()
            else:
                break


def send_robot_stat():
    robots = rm.get_all_robots()
    current_time = datetime.datetime.now().isoformat()
    for robot in robots:
        current_mission = get_current_mission(robot)
        robot_stat = {
            "amrId": robot.robot_id,
            "xCoordinate": robot.current_node.x,
            "yCoordinate": robot.current_node.y,
            "battery": 100,
            "amrHistoryCreatedAt": current_time
        }
        producer.produce("robot-stat", key=str(robot.robot_id), value=json.dumps(robot_stat).encode('utf-8'))
        producer.flush()


def get_current_mission(robot):
    current_mission = None if not robot.current_mission else {
        "mission_id": robot.current_mission.mission_id,
    }
    return current_mission


start()
