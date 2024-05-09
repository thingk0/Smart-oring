from typing import List

import requests

from domain.structure import Structure, Charger, Storage, Destination, EntranceDirection
from robot.mission.entity.mission import Mission, Submission
from robot.mission.path.point import Point


class UseCase:
    base_url = "http://localhost:8080"

    @staticmethod
    def get_map() -> List[Structure]:
        json = UseCase.get("/map/simul")

        nodes = []
        for item in json['resultData']:
            print(item)
            if item['nodeType'] == 'CHARGER':
                nodes.append(
                    Charger(item['xcoordinate'], item['ycoordinate'], EntranceDirection(item['entranceDirection'])))
            if item['nodeType'] == 'STORAGE':
                nodes.append(
                    Storage(item['xcoordinate'], item['ycoordinate'], EntranceDirection(item['entranceDirection'])))
            if item['nodeType'] == 'DESTINATION':
                nodes.append(
                    Destination(item['xcoordinate'], item['ycoordinate'], EntranceDirection(item['entranceDirection'])))

        return nodes

    @staticmethod
    def get(url, params=None):
        result = requests.get(UseCase.base_url + url, params=params)
        if result.status_code != 200:
            raise Exception(f"Failed to get {url}")

        return result.json()

    @staticmethod
    def get_mission(mission_id):
        json = UseCase.get(f"/missions/{mission_id}")
        # json['resultData']['submission_list'] = json['resultData']['submissionList']
        mission_json = json['resultData']
        submission_json = json['resultData']['submission_list']
        submission_list = []
        for e in submission_json:
            node_json = e['arrive_node']
            node = Point(node_json['xcoordinate'], node_json['ycoordinate'])
            submission = Submission(e['id'], node, e['submission_order'])
            submission_list.append(submission)

        mission = Mission(
            mission_json['id'],
            mission_json['created_at'],
            mission_json['mission_started_at'],
            mission_json['mission_finished_at'],
            mission_json['mission_estimated_time'],
            mission_json['updated_at'],
            mission_json['full_path'],
            submission_list
        )
        return mission
