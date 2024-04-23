import numpy as np
from node import Node

from mission import Mission, MissionOrder
from path import path_finder
from path.point import Point


def _generator():
    target = [Node(0, 0), Node(0, 4), Node(4, 2), Node(1, 4), Node(2, 3)]

    for node in target:
        yield node


def node_to_point(node: Node):
    return Point(node.x, node.y)


def process_mission(mission: Mission, factory_map):

    points = []
    for i in mission.orders:
        points.append(node_to_point(i.target_node))
    path = path_finder.find_path(points, np.array([[0] * 6 for _ in range(6)]))


if __name__ == '__main__':

    num_missions = 1
    orders_per_mission = 4

    mission = Mission(mission_id=1)
    for target_node in _generator():
        order = MissionOrder(
            mission_order_id=1,
            target_node=target_node,
            mission_order=1
        )
        mission.add_order(order)
    process_mission(mission)
