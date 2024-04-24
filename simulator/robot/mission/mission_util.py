import random

from robot.mission.entity import mission as mi


def get_random_mission(mission_order_num=4, width=5, height=5):
    mission = mi.Mission(
        mission_id=1
    )
    for i in range(mission_order_num):
        submission = get_random_submission(i, width, height)
        mission.add_order(submission)
    return mission


def get_random_submission(mission_order, width, height):
    x = random.randrange(0, width)
    y = random.randrange(0, width)
    return mi.Submission(
        mission_order_id=1,
        target_node=mi.Node(x, y),
        mission_order=mission_order
    )
