from dataclasses import dataclass, astuple


@dataclass
class Point:
    x: int
    y: int

    def __str__(self):
        return f"({self.x}\t{self.y})"

    def __iter__(self):
        return iter(astuple(self))

    def __lt__(self, other):
        return self.x < other.x or self.y < other.y

    def __eq__(self, other):
        return self.x == other.x and self.y == other.y

    def __hash__(self):
        return hash((self.x, self.y))
