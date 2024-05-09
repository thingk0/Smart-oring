from typing import List

import numpy as np

from domain.node import Node


class FactoryMap:
    def __init__(self, structures: List[Node], width: int, height: int):
        self.structures = structures
        self.width = width
        self.height = height

    def to_zero_one_array(self):
        array = [[0 for _ in range(self.width)] for _ in range(self.height)]
        for structure in self.structures:
            array[structure.x_coordinate][structure.y_coordinate] = 1
        return np.array(array)
