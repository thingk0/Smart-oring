from typing import List

import numpy as np

from domain.structure import Structure


class FactoryMap:
    def __init__(self, structures: List[Structure], width: int, height: int):
        self.structures = structures
        self.width = width
        self.height = height

    def to_zero_one_array(self):
        array = [[0 for _ in range(self.width)] for _ in range(self.height)]
        for structure in self.structures:
            array[structure.y_coordinate][structure.x_coordinate] = 1
        return np.array(array)
