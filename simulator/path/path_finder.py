import algorithm
from point import Point
import numpy as np


def find_path(points, array):
    final_path = []
    for i in range(len(points) - 1):
        path = algorithm.a_star(array=array, start=points[i], dest=points[i + 1], path=[0])
        path.pop()
        final_path.extend(path)
    final_path.append(points[-1])
    return final_path


if __name__ == '__main__':
    factory_map = [[0] * 6 for _ in range(6)]
    factory_map = np.array(factory_map)
    factory_map[2:4, 2:4] = 1
    print(factory_map)
    find_path(points=[Point(0, 0), Point(1, 3), Point(0, 4), Point(5, 5)], array=factory_map)
