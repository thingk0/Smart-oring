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
