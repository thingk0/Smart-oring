import json
import os
import time
from collections import deque

from confluent_kafka import Producer
from spring_config import ClientConfigurationBuilder
from spring_config.client import SpringConfigClient

from robot.mission import mission_util
from robot.mission.entity.mission import Mission
from robot.mission.entity.structure import Structure
from robot.robot_manager import RobotManager

missions: deque[Mission] = deque([mission_util.get_random_mission(4, 5, 5)])
rm: RobotManager = RobotManager.instance()
producer: Producer
env: dict


def get_map():
    factory_map = [[0] * 5 for _ in range(5)]
    factory_map[0][0] = Structure.CHARGER.value
    factory_map[2][0] = Structure.CHARGER.value
    factory_map[4][0] = Structure.CHARGER.value

    return factory_map


def init_robot():
    rm.add_robot(0, 0)
    rm.add_robot(2, 0)


def init_kafka():
    global producer
    server = env["kafka"]["server"]
    producer = Producer(
        {
            'bootstrap.servers': server,
        }
    )


def init_env():
    # 환경변수 불러오기
    global env
    profile = os.environ["profile"]
    config = (ClientConfigurationBuilder()
              .app_name("simulator")
              .address("http://localhost:8888")
              .profile(profile)
              .branch("master")
              .build())

    c = SpringConfigClient(config)
    env = c.get_config()


def start():
    init_env()
    init_kafka()
    init_robot()
    factory_map = get_map()
    rm.set_map(factory_map)

    while True:
        time.sleep(1)
        send_robot_stat()
        rm.print_factory_map()
        rm.process_robots()

        while missions:
            mission_assigned: bool = rm.assign_mission(missions[0])
            if mission_assigned:
                missions.popleft()
            else:
                break


def send_robot_stat():
    robots = rm.get_all_robots()
    for robot in robots:
        current_mission = get_current_mission(robot)
        robot_stat = {
            "robot_id": robot.robot_id,
            "robot_status": robot.robot_status.value,
            "current_node": {
                "x": robot.current_node.x,
                "y": robot.current_node.y
            },
            "current_mission": current_mission
        }
        producer.produce("robot-stat", key=str(robot.robot_id), value=json.dumps(robot_stat))
        producer.flush()


def get_current_mission(robot):
    current_mission = None if not robot.current_mission else {
        "mission_id": robot.current_mission.mission_id,
    }
    return current_mission


start()
