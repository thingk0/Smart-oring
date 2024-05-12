from collections import deque
from enum import Enum
from typing import List

from domain.factory_map import FactoryMap
from robot.mission.entity.mission import Mission
from robot.mission.mission_processor import get_route_point_to_point
from robot.mission.path.point import Point
from util import StructureUtil


class Robot:
    def __init__(self, robot_id, robot_status, current_node):
        self.robot_id = robot_id
        self.robot_status: RobotStatus = robot_status
        self.current_node: Point = current_node
        self.current_mission: Mission | None = None
        self.next_nodes: deque[Point] = deque()
        self.visited_node_until_mission_complete: deque[Point] = deque()
        self.last_event: RobotEvent = RobotEvent.IDLE
        self.last_mission_started_at = None
        self.last_mission_processing_time = 0

    def assign_mission(self, mission, route: deque, current_time=None):
        self.current_mission = mission
        self.next_nodes = route
        self.robot_status = RobotStatus.PROCESSING
        self.visited_node_until_mission_complete: deque[Point] = deque()
        self.last_event = RobotEvent.MOVE_FOR_MISSION
        self.last_mission_processing_time = 0
        self.last_mission_started_at = current_time

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

    def __str__(self):
        return f"로봇 ID : {self.robot_id}, 로봇 상태 : {self.robot_status}, 현재 노드 : {self.current_node}, 현재 미션 : {self.current_mission}"

    def get_next_nodes(self) -> List[Point]:
        return list(self.next_nodes)

    def process(self, locked_nodes: set, factory_map: FactoryMap):
        if self.robot_status == RobotStatus.CHARGING:
            return

        next_node = self.get_next_node()

        current_mission = self.current_mission
        last_event = self.last_event

        if not next_node:
            if last_event == RobotEvent.MOVE_FOR_CHARGE:
                self.charge()
                return
            elif current_mission:
                self.finish_mission()
                return
            else:
                self.go_to_charge(factory_map)
                return

        if current_mission:
            self.last_mission_processing_time += 1
            if next_node in locked_nodes:
                self.cant_move()
                return
            self.move_to_complete_mission()
        else:
            if next_node in locked_nodes:
                self.cant_move()
                return
            self.move_for_charge()

    def move_for_charge(self):
        self.current_node = self.next_nodes.popleft()
        self.last_event = RobotEvent.MOVE_FOR_CHARGE

    def move_to_complete_mission(self):
        self.visited_node_until_mission_complete.append(self.current_node)
        self.last_event = RobotEvent.MOVE_FOR_MISSION
        self.current_node = self.next_nodes.popleft()

    def cant_move(self):
        self.last_event = RobotEvent.CANT_MOVE

    def go_to_charge(self, factory_map):
        charger = factory_map.get_chargers()[self.robot_id]
        front_node = StructureUtil.get_front_entrance_from_node(charger)
        route = get_route_point_to_point(factory_map.to_zero_one_array(), current_point=self.current_node,
                                         target_point=Point(*front_node))
        self.next_nodes = route
        self.move_for_charge()

    def charge(self):
        self.robot_status = RobotStatus.CHARGING
        self.last_event = RobotEvent.CHARGE_START

    def finish_mission(self):
        self.last_event = RobotEvent.COMPLETE_MISSION
        self.current_mission = None


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
    IDLE = 'IDLE'
