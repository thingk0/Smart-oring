import { Object3D } from 'three';

export type Point2D = [number, number];

export type robotData = {
  xcoordinate: number;
  ycoordinate: number;
  battery: number;
  amrId: number;
  mission: string;
};

export type MapData = {
  charger: PositionData[];
  storage: PositionData[];
  destination: PositionData[];
  conveyorBelt: PositionData[];
};
export type PositionData = {
  start: Point2D;
  end: Point2D;
  direction: number;
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

export type GraphicsQuality = 'low' | 'medium' | 'high' | 'custom';
export type ShadowDetail = 'off' | 'low' | 'high';
export type RenderingScale = 0.5 | 0.75 | 1 | 1.25 | 1.5 | 1.75 | 2 | 'auto';
export type LightQuality = 'low' | 'medium' | 'high';
export type EffectQuality = 'low' | 'high';
export type AmrStatus = {
  battery: number;
  amrId: number;
  missionId: number;
  amrStatus: string;
  currentStopDuration: number;
  amrHistoryCreatedAt: string;
  ycoordinate: number;
  xcoordinate: number;
  amrRoute: Point2D[];
  routeVisitedForMission: Point2D[];
  routeRemainingForMission: Point2D[];
  hasStuff: boolean;
};
