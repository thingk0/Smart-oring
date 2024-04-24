import time
from collections import deque

from robot.mission import mission_util
from robot.mission.entity.structure import Structure
from robot.mission.entity.mission import Mission
from robot.robot_manager import RobotManager

missions: deque[Mission] = deque([mission_util.get_random_mission(4, 5, 5)])
rm: RobotManager = RobotManager.instance()
rm.add_robot(0, 0)


# rm.add_robot(2, 0)
# rm.add_robot(4, 0)

def get_map():
    factory_map = [[0] * 5 for _ in range(5)]
    factory_map[0][0] = Structure.CHARGER.value
    factory_map[2][0] = Structure.CHARGER.value
    factory_map[4][0] = Structure.CHARGER.value

    return factory_map


def start():
    factory_map = get_map()
    rm.set_map(factory_map)

    while True:
        time.sleep(1)
        send_robot_stat()
        rm.print_factory_map()
        rm.process_robots()

        if not missions:
            continue
        mission_assigned: bool = rm.assign_mission(missions[0])
        if mission_assigned:
            missions.popleft()


def send_robot_stat():
    robots = rm.get_all_robots()
    # for robot in robots:
    #     print(
    #         f'Robot ID: {robot.robot_id}, '
    #         f'Status: {robot.robot_status} '
    #         f'Current Node: {robot.current_node} '
    #         f'Current Mission: {robot.current_mission} '
    #         f'next route: {robot.route}'
    #     )


start()
