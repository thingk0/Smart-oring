import { Object3D } from 'three';

export type Point2D = [number, number];

export type robotData = {
  xcoordinate: number;
  ycoordinate: number;
  battery: number;
  amrId: number;
  mission: string;
};

export type TRobot = {
  geo_aluminium_3: Object3D;
  geo_black_7: Object3D;
  geo_black_matte_1: Object3D;
  geo_black_smoke_glass_8: Object3D;
  geo_light_cyan_1: Object3D;
  geo_orange_1: Object3D;
  geo_rubber_6: Object3D;
};
