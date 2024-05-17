import random
from collections import deque
from enum import Enum
from typing import List

from domain.factory_map import FactoryMap
from robot.mission import mission_processor
from robot.mission.entity.mission import Mission, Submission
from robot.mission.mission_processor import get_route_point_to_point
from robot.mission.path.point import Point
from util import StructureUtil


class Robot:
    def __init__(self, robot_id, robot_status, current_node, factory_map):
        self.robot_id = robot_id
        self.robot_status: RobotStatus = robot_status
        self.current_point: Point = current_node
        self.current_mission: Mission | None = None
        self.next_points: deque[Point] = deque()
        self.visited_node_until_mission_complete: deque[Point] = deque()
        self.last_event: RobotEvent = RobotEvent.IDLE
        self.last_mission_started_at = None
        self.last_mission_processing_time = 0
        self.cant_move_duration = 0
        self.factory_map: FactoryMap = factory_map
        self.processing_submission_idx: int | None = None
        self.estimated_time_when_mission_first_set = 0
        self.last_mission: Mission | None = None

    def assign_mission(self, mission, locked_nodes, current_time=None):
        if self.robot_status != RobotStatus.CHARGING:
            return False
        route = mission_processor.process_mission(mission,
                                                  self.get_map_with_locked_nodes(locked_nodes),
                                                  self.current_point,
                                                  road=[0])
        if not route:
            return False

        self.current_mission = mission
        self.next_points = route
        self.robot_status = RobotStatus.PROCESSING
        self.visited_node_until_mission_complete: deque[Point] = deque()
        self.last_event = RobotEvent.MOVE_FOR_MISSION
        self.last_mission_processing_time = 0
        self.last_mission_started_at = current_time
        self.estimated_time_when_mission_first_set = len(route)

        e: Submission
        self.processing_submission_idx = 0
        return True

    def get_next_point(self) -> Point | None:
        if not self.next_points:
            return None
        return self.next_points[0]

    def __str__(self):
        return f"로봇 ID : {self.robot_id}, 로봇 상태 : {self.robot_status}, 현재 노드 : {self.current_point}, 현재 미션 : {self.current_mission}"

    def get_next_nodes(self) -> List[Point]:
        return list(self.next_points)

    def process(self, locked_points: set, factory_map: FactoryMap):
        if self.robot_status == RobotStatus.CHARGING:
            return

        next_node = self.get_next_point()

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
                self.set_route_to_charge_and_go(locked_points)
                return

        if current_mission:
            self.last_mission_processing_time += 1
            if next_node in locked_points:
                self.cant_move_for_mission(locked_points)
                return
            self.move_to_complete_mission()
        else:
            if next_node in locked_points:
                self.cant_move_to_charger(locked_points)
                return
            self.move_for_charge()

    def move_for_charge(self):
        self.current_point = self.next_points.popleft()
        self.last_event = RobotEvent.MOVE_FOR_CHARGE
        self.robot_status = RobotStatus.PROCESSING
        self.cant_move_duration = 0

    def move_to_complete_mission(self):
        self.visited_node_until_mission_complete.append(self.current_point)

        have_to_go_node = self.current_mission.submissions[self.processing_submission_idx].arrive_node
        if self.current_point == Point(have_to_go_node.x_coordinate, have_to_go_node.y_coordinate):
            self.processing_submission_idx += 1

        self.last_event = RobotEvent.MOVE_FOR_MISSION
        self.robot_status = RobotStatus.PROCESSING

        self.current_point = self.next_points.popleft()
        self.cant_move_duration = 0

    def set_new_route_for_mission(self, locked_points):
        nodes = [submission.arrive_node for submission in
                 self.current_mission.submissions[self.processing_submission_idx:]]
        factory_map = self.get_map_with_locked_nodes(locked_points)
        route = mission_processor.get_route_to_structure(nodes, factory_map,
                                                         self.current_point)
        if route:
            self.next_points = route

    def set_new_route_for_charge(self, locked_points):
        nodes = [submission.arrive_node for submission in
                 self.current_mission.submissions[self.processing_submission_idx:]]
        factory_map = self.get_map_with_locked_nodes(locked_points)
        route = mission_processor.get_route_to_structure(nodes, factory_map,
                                                         self.current_point)
        if route:
            self.next_points = route

    def cant_move_for_mission(self, locked_points):
        self.cant_move_duration += 1
        possibility = random.random()
        self.robot_status = RobotStatus.BOTTLENECK
        if self.cant_move_duration < 3 and possibility < 0.5:
            return
        self.set_new_route_for_mission(locked_points)

    def cant_move_to_charger(self, locked_points):
        self.cant_move_duration += 1
        self.last_event = RobotEvent.CANT_MOVE
        self.robot_status = RobotStatus.BOTTLENECK

        possibility = random.random()
        if self.cant_move_duration < 3 and possibility < 0.5:
            return
        charger = self.get_charger()
        array = self.get_map_with_locked_nodes(locked_points)
        route = mission_processor.get_route_to_structure([charger], array, self.current_point)
        if route:
            self.next_points = route

    def get_charger(self):
        charger = self.factory_map.get_chargers()[self.robot_id - 1]
        return charger

    def get_map_with_locked_nodes(self, locked_nodes: set[Point]):
        array = self.factory_map.to_zero_one_array()
        for node in locked_nodes:
            array[node.x][node.y] = 2
        return array

    def set_route_to_charge_and_go(self, locked_nodes: set[Point]):
        charger = self.get_charger()
        front_node = StructureUtil.get_front_entrance_from_node(charger)

        array = self.get_map_with_locked_nodes(locked_nodes)

        route = get_route_point_to_point(array, current_point=self.current_point,
                                         target_point=Point(*front_node))
        if route:
            self.next_points = route
            self.move_for_charge()

    def charge(self):
        self.robot_status = RobotStatus.CHARGING
        self.last_event = RobotEvent.CHARGE_START

    def finish_mission(self):
        self.last_event = RobotEvent.COMPLETE_MISSION
        self.last_mission = self.current_mission
        self.current_mission = None
        self.processing_submission_idx = None


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
