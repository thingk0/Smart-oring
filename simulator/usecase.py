from typing import List

import requests

from domain.structure import Structure, Charger, Storage, Destination


class UseCase:
    base_url = "http://localhost:8080"

    @staticmethod
    def get_map() -> List[Structure]:
        json = UseCase.get("/map/simul")

        nodes = []
        for item in json['resultData']:
            print(item)
            if item['nodeType'] == 'CHARGER':
                nodes.append(Charger(item['xcoordinate'], item['ycoordinate'], item['entranceDirection']))
            if item['nodeType'] == 'STORAGE':
                nodes.append(Storage(item['xcoordinate'], item['ycoordinate'], item['entranceDirection']))
            if item['nodeType'] == 'DESTINATION':
                nodes.append(Destination(item['xcoordinate'], item['ycoordinate'], item['entranceDirection']))
        print(nodes)

        return json

    @staticmethod
    def get(url, params=None):
        return requests.get(UseCase.base_url + url, params=params).json()
