from robot.mission.path.algorithm import a_star
from robot.mission.path.node import Node
import numpy as np
from collections import deque


def find_path(points, array, road) -> deque[Node]:
    final_path = deque()
    for i in range(len(points) - 1):
        path = a_star(array=array, start=points[i], dest=points[i + 1], path=road)
        path.pop()
        final_path.extend(path)
    final_path.append(points[-1])
    return final_path


if __name__ == '__main__':
    factory_map = [[0] * 6 for _ in range(6)]
    factory_map = np.array(factory_map)
    factory_map[2:4, 2:4] = 1
    print(factory_map)
    find_path(points=[Node(0, 0), Node(1, 3), Node(0, 4), Node(5, 5)], array=factory_map)
