from enum import Enum


class Node:

    def __init__(self, xcoordinate, ycoordinate, entranceDirection):
        self.x_coordinate = xcoordinate
        self.y_coordinate = ycoordinate
        self.entranceDirection = entranceDirection

    def __str__(self):
        return f"x: {self.x_coordinate}, y: {self.y_coordinate}"


class Charger(Node):

    def __init__(self, xcoordinate, ycoordinate, entranceDirection):
        super().__init__(xcoordinate, ycoordinate, entranceDirection)


class Storage(Node):
    def __init__(self, xcoordinate, ycoordinate, entranceDirection):
        super().__init__(xcoordinate, ycoordinate, entranceDirection)


class Destination(Node):
    def __init__(self, xcoordinate, ycoordinate, entranceDirection):
        super().__init__(xcoordinate, ycoordinate, entranceDirection)


class ConveyorBelt(Node):
    def __init__(self, xcoordinate, ycoordinate, entranceDirection):
        super().__init__(xcoordinate, ycoordinate, entranceDirection)


class EntranceDirection(Enum):
    WEST = "WEST"
    EAST = "EAST"
    NORTH = "NORTH"
    SOUTH = "SOUTH"
