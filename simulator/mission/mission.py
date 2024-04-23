from datetime import datetime
from typing import List, Optional
from node import Node


class Mission:
    def __init__(self, mission_id: int, mission_process_time: Optional[datetime] = None,
                 created_at: Optional[datetime] = None):
        self.mission_id = mission_id
        self.mission_process_time = mission_process_time
        self.created_at = created_at
        self.orders: List[MissionOrder] = []

    def add_order(self, order):
        self.orders.append(order)


class MissionOrder:
    def __init__(self, mission_order_id: int, target_node: Node,
                 mission_order: Optional[int] = None):
        self.mission_order_id = mission_order_id
        self.target_node: Node = target_node
        self.mission_order = mission_order
