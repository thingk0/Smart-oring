from typing import List

import numpy as np

from robot.mission import mission_processor
from robot.mission.entity.robot import RobotStatus, Robot
from robot.mission.path.node import Node


class RobotManager:
    __instance = None

    @classmethod
    def instance(cls):
        if cls.__instance is None:
            cls.__instance = cls()
        return cls.__instance

    def __init__(self):
        self.factory_map = None
        self.idle_robots: List[Robot] = []
        self.working_robots: List[Robot] = []
        self.num_robots = 0

    def add_robot(self, x, y):
        robot = Robot(robot_id=self.num_robots, robot_status=RobotStatus.IDLE, current_node=Node(x, y))
        self.idle_robots.append(robot)
        self.num_robots += 1

    def assign_mission(self, mission):
        if not self.idle_robots:
            return False
        robot: Robot = self.idle_robots.pop(0)

        new_factory_map = np.array([row[:] for row in self.factory_map])

        route = mission_processor.process_mission(mission, new_factory_map, robot.current_node, road=[0])
        print(route)
        robot.assign_mission(mission, route)
        self.working_robots.append(robot)
        return True

    def set_map(self, factory_map):
        self.factory_map = factory_map

    def get_all_robots(self) -> List[Robot]:
        return self.idle_robots + self.working_robots

    def process_robots(self):
        locked_nodes = set()
        for robot in self.working_robots:
            locked_nodes.add(robot.current_node)
        for robot in self.idle_robots:
            locked_nodes.add(robot.current_node)

        for robot in self.working_robots:
            next_node = robot.get_next_node()
            if next_node in locked_nodes:
                continue
            if robot.current_mission:
                robot.go_next_node()
                continue

            self.working_robots.remove(robot)
            self.idle_robots.append(robot)

    def print_factory_map(self):

        new_factory_map = np.array([row[:] for row in self.factory_map])
        for robot in self.get_all_robots():
            new_factory_map[robot.current_node.x][robot.current_node.y] = 1

        print(new_factory_map)
