export type Point2D = [number, number];

export type robotData = {
  position: Point2D;
  battery: number;
  id: string;
  mission: string;
};

export type MapData = {
  charger: PositionData[];
  storage: PositionData[];
  destination: PositionData[];
  conveyor: PositionData[];
};
export type PositionData = {
  start: Point2D;
  end: Point2D;
  direction: number;
};
