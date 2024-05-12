from typing import List

import numpy as np

from domain.factory_map import FactoryMap
from robot.mission import mission_processor
from robot.mission.entity.robot import RobotStatus, Robot
from robot.mission.path.point import Point


class RobotManager:
    __instance = None

    @classmethod
    def instance(cls):
        if cls.__instance is None:
            cls.__instance = cls()
        return cls.__instance

    def __init__(self):
        self.factory_map: FactoryMap | None = None
        # self.idle_robots: List[Robot] = []
        # self.working_robots: List[Robot] = []
        self.robots: List[Robot] = []
        self.num_robots = 0

    def add_robot(self, x, y):
        robot = Robot(robot_id=self.num_robots, robot_status=RobotStatus.CHARGING, current_node=Point(x, y))
        self.robots.append(robot)
        self.num_robots += 1

    def assign_mission(self, mission, current_time) -> bool:
        for robot in self.robots:
            if robot.robot_status == RobotStatus.CHARGING:
                new_factory_map = self.factory_map.to_zero_one_array()
                route = mission_processor.process_mission(mission, new_factory_map, robot.current_node, road=[0])
                print(route)
                robot.assign_mission(mission, route, current_time=current_time)
                return True
        return False

    def set_map(self, factory_map):
        self.factory_map = factory_map

    def get_all_robots(self) -> List[Robot]:
        return self.robots

    def process_robots(self):

        for robot in self.robots:
            locked_nodes = set()

            for other_robot in self.robots:
                locked_nodes.add(other_robot.current_node)
            robot.process(locked_nodes, self.factory_map)

        # for robot in self.working_robots:
        #     if robot.last_event == RobotEvent.
        #         pass
        #
        #     next_node = robot.get_next_node()
        #     if next_node:
        #         if next_node in locked_nodes:
        #             continue
        #         robot.go_next_node()
        #     else:
        #         if robot.current_mission:
        #             robot.finish_mission()
        #             continue
        #         self.working_robots.remove(robot)
        #         self.idle_robots.append(robot)

    def print_factory_map(self):

        new_factory_map = np.array([row[:] for row in self.factory_map])
        for robot in self.get_all_robots():
            new_factory_map[robot.current_node.x][robot.current_node.y] = 1

        print(new_factory_map)
