from collections import deque
from enum import Enum
from typing import List

from domain.factory_map import FactoryMap
from robot.mission.entity.mission import Mission
from robot.mission.mission_processor import get_route_point_to_point
from robot.mission.path.point import Point


class Robot:
    def __init__(self, robot_id, robot_status, current_node):
        self.robot_id = robot_id
        self.robot_status: RobotStatus = robot_status
        self.current_node: Point = current_node
        self.current_mission: Mission | None = None
        self.next_nodes: deque[Point] = deque()
        self.last_event: RobotEvent | None = None

    def assign_mission(self, mission, route: deque):
        self.current_mission = mission
        self.next_nodes = route
        self.robot_status = RobotStatus.PROCESSING
        self.last_event = RobotEvent.MOVE_FOR_MISSION

    def go_next_node(self, locked_nodes: set):
        next_node = self.get_next_node()
        if next_node in locked_nodes:
            self.last_event = RobotEvent.CANT_MOVE
            return
        if not self.next_nodes:
            self.last_event = RobotEvent.COMPLETE_MISSION
            return
        self.current_node = self.next_nodes.popleft()
        self.last_event = RobotEvent.MOVE_FOR_MISSION

    def get_next_node(self) -> Point | None:
        if not self.next_nodes:
            return None
        return self.next_nodes[0]

    def finish_mission(self):
        self.last_event = RobotEvent.COMPLETE_MISSION
        self.current_mission = None

    def __str__(self):
        return f"로봇 ID : {self.robot_id}, 로봇 상태 : {self.robot_status}, 현재 노드 : {self.current_node}, 현재 미션 : {self.current_mission}"

    def get_next_nodes(self) -> List[Point]:
        return list(self.next_nodes)

    def process(self, locked_nodes: set, factory_map: FactoryMap):
        next_node = self.get_next_node()

        if not next_node:
            if not self.current_mission:
                charger = factory_map.get_chargers()[self.robot_id]

                route = get_route_point_to_point(factory_map, current_point=self.current_node,
                                                 target_point=Point(charger.x_coordinate, charger.y_coordinate))
                self.next_nodes = route
                self.last_event = RobotEvent.COMPLETE_MISSION
                return
            return

        if next_node in locked_nodes:
            self.last_event = RobotEvent.CANT_MOVE
            return

        self.current_node = self.next_nodes.popleft()
        if self.current_mission:
            self.last_event = RobotEvent.MOVE_FOR_MISSION
        else:
            self.last_event = RobotEvent.MOVE_FOR_CHARGE


class RobotStatus(Enum):
    PROCESSING = 'PROCESSING'
    BOTTLENECK = 'BOTTLENECK'
    CHARGING = 'CHARGING'
    ERROR = 'ERROR'
    DISCHARGING = 'DISCHARGING'


class RobotEvent(Enum):
    COMPLETE_MISSION = 'COMPLETE_MISSION'
    MOVE_FOR_MISSION = 'MOVE_FOR_MISSION'
    CANT_MOVE = 'CANT_MOVE'
    MOVE_FOR_CHARGE = 'MOVE_FOR_CHARGE'
    CHARGE_START = 'CHARGE_START'
