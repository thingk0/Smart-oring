from datetime import datetime
from typing import List, Optional

from robot.mission.path.node import Node


# class Node:
#     def __init__(self, data):
#         self.created_at = datetime.fromisoformat(data['createdAt'])
#         self.updated_at = datetime.fromisoformat(data['updatedAt'])
#         self.id = data['id']
#         self.entrance_direction = data['entranceDirection']
#         self.ycoordinate = data['ycoordinate']
#         self.xcoordinate = data['xcoordinate']
#
# class Submission:
#     def __init__(self, data):
#         self.created_at = datetime.fromisoformat(data['createdAt'])
#         self.updated_at = datetime.fromisoformat(data['updatedAt'])
#         self.id = data['id']
#         self.mission = data['mission']
#         self.arrive_node = Node(data['arriveNode'])
#         self.submission_order = data['submissionOrder']
#
# class Mission:
#     def __init__(self, data):
#         self.created_at = datetime.fromisoformat(data['createdAt'])
#         self.updated_at = datetime.fromisoformat(data['updatedAt'])
#         self.id = data['id']
#         self.mission_started_at = datetime.fromisoformat(data['missionStartedAt'])
#         self.mission_finished_at = datetime.fromisoformat(data['missionFinishedAt'])
#         self.mission_estimated_time = datetime.fromisoformat(data['missionEstimatedTime'])
#         self.submission_list = [Submission(submission) for submission in data['submissionList']]

class Mission:
    def __init__(
            self,
            id: int,
            created_at: Optional[datetime] = None,
            mission_started_at=None,
            mission_finished_at=None,
            mission_estimated_time=None,
            updated_at=None,
            full_path=None,
            submission_list=None
    ):
        self.mission_id = id
        self.created_at = created_at
        self.submissions: List[Submission] = submission_list

    def add_order(self, order):
        self.submissions.append(order)

    def __str__(self):
        return (f"미션 ID : {self.mission_id}, 미션 생성 시간 : {self.created_at}, 미션 처리 시간 : {self.mission_process_time},"
                f"\n{'\n'.join([str(i) for i in self.submissions])}")


class Submission:
    def __init__(self, mission_order_id: int, arrive_node: Node,
                 mission_order: Optional[int] = None):
        self.mission_order_id = mission_order_id
        self.arrive_node: Node = arrive_node
        self.mission_order = mission_order

    def __str__(self):
        return f"미션 오더 ID : {self.mission_order_id}, 미션 오더 : {self.mission_order}, 타겟 노드 : {self.arrive_node}"
