from collections import deque
from enum import Enum

from robot.mission.entity.mission import Mission
from robot.mission.path.node import Node


class Robot:
    def __init__(self, robot_id, robot_status, current_node):
        self.robot_id = robot_id
        self.robot_status: RobotStatus = robot_status
        self.current_node: Node = current_node
        self.current_mission: Mission = None
        self.next_nodes: deque[Node] = deque()

    def assign_mission(self, mission, route: deque):
        self.current_mission = mission
        self.next_nodes = route
        self.robot_status = RobotStatus.WORKING

    def go_next_node(self):
        if not self.next_nodes:
            return
        self.current_node = self.next_nodes.popleft()

    def get_next_node(self) -> Node | None:
        if not self.next_nodes:
            return None
        return self.next_nodes[0]

    def __str__(self):
        return f"로봇 ID : {self.robot_id}, 로봇 상태 : {self.robot_status}, 현재 노드 : {self.current_node}, 현재 미션 : {self.current_mission}"


class RobotStatus(Enum):
    IDLE = 'IDLE'
    WORKING = 'WORKING'
    CHARGING = 'CHARGING'
    ERROR = 'ERROR'
    STOP = 'STOP'
    EMERGENCY = 'EMERGENCY'
