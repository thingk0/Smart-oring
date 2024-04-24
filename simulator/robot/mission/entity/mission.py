from datetime import datetime
from typing import List, Optional
from robot.mission.path.node import Node


class Mission:
    def __init__(self, mission_id: int, mission_process_time: Optional[datetime] = None,
                 created_at: Optional[datetime] = None):
        self.mission_id = mission_id
        self.mission_process_time = mission_process_time
        self.created_at = created_at
        self.submissions: List[Submission] = []

    def add_order(self, order):
        self.submissions.append(order)

    def __str__(self):
        return (f"미션 ID : {self.mission_id}, 미션 생성 시간 : {self.created_at}, 미션 처리 시간 : {self.mission_process_time},"
                f"\n{'\n'.join([str(i) for i in self.submissions])}")


class Submission:
    def __init__(self, mission_order_id: int, target_node: Node,
                 mission_order: Optional[int] = None):
        self.mission_order_id = mission_order_id
        self.target_node: Node = target_node
        self.mission_order = mission_order

    def __str__(self):
        return f"미션 오더 ID : {self.mission_order_id}, 미션 오더 : {self.mission_order}, 타겟 노드 : {self.target_node}"
