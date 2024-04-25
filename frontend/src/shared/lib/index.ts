import { Vector3 } from 'three';

export const convertPosition = (
  start: [number, number],
  end: [number, number]
) => {
  return new Vector3(
    start[1] + (end[1] - start[1]) / 2,
    1,
    start[0] + (end[0] - start[0]) / 2
  );
};
export const calculateScale = (
  start: [number, number],
  end: [number, number]
) => {
  return new Vector3(end[1] - start[1], 1, end[0] - start[0]);
};
