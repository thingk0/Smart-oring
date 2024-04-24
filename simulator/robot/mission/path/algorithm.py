import numpy as np
import math
import heapq
import collections

from robot.mission.path.node import Node

# 방향 벡터
dx = [1, -1, 0, 0]
dy = [0, 0, 1, -1]


def get_euclidean_distance(p1: Node, p2: Node):
    """
    유클리디안 거리를 구하는 함수
    :param p1:
    :param p2:
    :return:
    """
    a, b = p1
    c, d = p2
    return math.sqrt((a - c) ** 2 + (b - d) ** 2)


def a_star(array, start: Node, dest: Node, path):
    n = len(array)
    m = len(array[0])

    heuristic_cost = [[float("inf")] * m for _ in range(n)]

    # 휴리스틱 코스트 구하기
    for i in range(n):
        for j in range(m):
            if array[i][j] in path:
                heuristic_cost[i][j] = round(get_euclidean_distance(Node(i, j), dest))

    distance = [[float("inf")] * m for _ in range(n)]
    distance[start.x][start.y] = 0

    heap = []
    heapq.heappush(heap, (heuristic_cost[start.x][start.y], 0, Node(start.x, start.y), Node(-1, -1)))

    estimated_cost = 0
    parent = [[Node(-1, -1)] * m for _ in range(n)]

    while heap:

        estimated_cost, current_distance, point, parent_point = heapq.heappop(heap)
        point: Node

        x, y = point.x, point.y
        if current_distance != distance[x][y]:
            continue
        parent[x][y] = parent_point
        if x == dest.x and y == dest.y:
            break

        for i in range(4):
            nx = x + dx[i]
            ny = y + dy[i]

            if not (0 <= nx < n and 0 <= ny < m):
                continue
            if distance[nx][ny] <= current_distance + 1:
                continue
            if array[nx][ny] not in path:
                continue
            distance[nx][ny] = current_distance + 1
            heapq.heappush(heap, (heuristic_cost[nx][ny], current_distance + 1, Node(nx, ny), Node(x, y)))

    path = find_path(parent=parent, start=dest)
    return path


def find_path(parent, start: Node):
    current = Node(start.x, start.y)
    dq = collections.deque()
    while True:
        if parent[current.x][current.y] == Node(-1, -1):
            dq.appendleft(current)
            break
        dq.appendleft(current)
        current = parent[current.x][current.y]
    return dq


if __name__ == '__main__':
    factory_map = [[0] * 6 for _ in range(6)]
    factory_map = np.array(factory_map)
    factory_map[2:4, 2:4] = 1
    print(factory_map)

    a_star(array=factory_map, start=Node(1, 1), dest=Node(5, 5), path=[0])
