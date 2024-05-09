from domain.node import Node, EntranceDirection


def get_front_entrance_from_node(e: Node):
    if not hasattr(e, 'entranceDirection'):
        return None
    entrance: EntranceDirection = e.entranceDirection
    x = e.x_coordinate
    y = e.y_coordinate

    dic = {
        EntranceDirection.WEST: (x, y - 1),
        EntranceDirection.EAST: (x, y + 1),
        EntranceDirection.NORTH: (x - 1, y),
        EntranceDirection.SOUTH: (x + 1, y)
    }

    return dic[entrance]
