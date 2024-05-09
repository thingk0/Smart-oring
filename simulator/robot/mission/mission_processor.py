import numpy as np

from robot.mission.entity.mission import Mission, Submission
from robot.mission.path.path_finder import find_path
from robot.mission.path.point import Point
from util import StructureUtil


def _generator():
    target = [Point(0, 0), Point(0, 4), Point(4, 2), Point(1, 4), Point(2, 3)]

    for node in target:
        yield node


def process_mission(mission: Mission, factory_map, start_point: Point, road):
    points = [start_point]
    height = len(factory_map)
    width = len(factory_map[0])
    for i in mission.submissions:
        node = i.arrive_node
        front_node = StructureUtil.get_front_entrance_from_node(node)
        point = Point(*front_node)
        points.append(point)
    path = find_path(points, np.array([[0] * width for _ in range(height)]), road=road)
    path.popleft()
    return path


if __name__ == '__main__':

    num_missions = 1
    orders_per_mission = 4

    mission = Mission(id=1)
    for target_node in _generator():
        order = Submission(
            mission_order_id=1,
            arrive_node=target_node,
            mission_order=1
        )
        mission.add_order(order)
    process_mission(mission)
