from collections import deque
from typing import List

from domain.node import Node, EntranceDirection
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
    path = find_path(points, factory_map, road=road)
    if not path:
        return None
    path.popleft()
    return path


def get_route_to_structure(nodes: List[Node], factory_map, start_point: Point, road=None):
    if road is None:
        road = [0]
    points = [start_point]
    for node in nodes:
        node: Node
        front_node = StructureUtil.get_front_entrance_from_node(node)
        point = Point(*front_node)
        points.append(point)
    path = find_path(points, factory_map, road=road)
    if not path:
        return None
    path.popleft()
    return path


def get_route_point_to_point(factory_map, current_point: Point, target_point: Point, road=None) -> deque[Point]:
    if road is None:
        road = [0]
    points = [current_point, target_point]
    path = find_path(points, factory_map, road=road)
    if not path:
        return None
    path.popleft()
    return path


if __name__ == '__main__':
    num_missions = 1
    orders_per_mission = 4

    mission = Mission(id=1, submission_list=[
        #         15,43,2024-05-18 01:19:26.051795,238,2024-05-18 01:19:26.051795,STORAGE,SOUTH
        # 22,35,2024-05-18 01:19:26.072731,248,2024-05-18 01:19:26.072731,STORAGE,NORTH
        # 7,54,2024-05-18 01:19:26.210159,329,2024-05-18 01:19:26.210159,CONVEYOR_BELT,WEST
        Submission(
            mission_order_id=1,
            arrive_node=Node(15, 43, EntranceDirection.NORTH),
            mission_order=1
        ),
        Submission(
            mission_order_id=1,
            arrive_node=Node(22, 35, EntranceDirection.NORTH),
            mission_order=1
        ),
        Submission(
            mission_order_id=1,
            arrive_node=Node(7, 54, EntranceDirection.NORTH),
            mission_order=1
        ),
        # Submission(
        #     mission_order_id=1,
        #     arrive_node=Node(9, 43, EntranceDirection.NORTH),
        #     mission_order=1
        # ),
        # Submission(
        #     mission_order_id=1,
        #     arrive_node=Node(19, 26, EntranceDirection.SOUTH),
        #     mission_order=1
        # )
    ])
    # for target_node in _generator():
    #     order = Submission(
    #         mission_order_id=1,
    #         arrive_node=target_node,
    #         mission_order=1
    #     )
    #     mission.add_order(order)
    factory_map = [[0] * 100 for _ in range(100)]
    factory_map[1][1] = 1
    factory_map[1][2] = 1
    factory_map[1][3] = 1

    factory_map[2][1] = 1
    factory_map[2][2] = 1
    factory_map[2][3] = 1

    factory_map[3][1] = 1
    factory_map[3][2] = 1
    factory_map[21][35] = 1

    # print factoru_map
    for row in factory_map:
        print(row)
    #         15,43,2024-05-18 01:19:26.051795,238,2024-05-18 01:19:26.051795,STORAGE,SOUTH
    # 22,35,2024-05-18 01:19:26.072731,248,2024-05-18 01:19:26.072731,STORAGE,NORTH
    # 7,54,2024-05-18 01:19:26.210159,329,2024-05-18 01:19:26.210159,CONVEYOR_BELT,WEST

    # path = process_mission(mission, factory_map=factory_map, start_point=Point(0, 0), road=[0])
    path = process_mission(mission, factory_map=factory_map, start_point=Point(0, 0), road=[0])
    # path = get_route_to_structure([Node(2, 2, EntranceDirection.NORTH)], factory_map, start_point=Point(0, 0), road=[0])
    print(path)
