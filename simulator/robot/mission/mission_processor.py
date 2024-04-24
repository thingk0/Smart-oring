import numpy as np

from robot.mission.entity.mission import Mission, Submission
from robot.mission.path.path_finder import find_path
from robot.mission.path.node import Node


def _generator():
    target = [Node(0, 0), Node(0, 4), Node(4, 2), Node(1, 4), Node(2, 3)]

    for node in target:
        yield node


def process_mission(mission: Mission, factory_map, start_point: Node, road):
    points = [start_point]
    height = len(factory_map)
    width = len(factory_map[0])
    for i in mission.submissions:
        points.append(i.target_node)
    path = find_path(points, np.array([[0] * width for _ in range(height)]), road=road)
    path.popleft()
    return path


if __name__ == '__main__':

    num_missions = 1
    orders_per_mission = 4

    mission = Mission(mission_id=1)
    for target_node in _generator():
        order = Submission(
            mission_order_id=1,
            target_node=target_node,
            mission_order=1
        )
        mission.add_order(order)
    process_mission(mission)
