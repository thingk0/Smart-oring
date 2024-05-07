import { Vector3 } from 'three';
import { robotData } from '../types';

export const convertPosition = (
  start: [number, number],
  end: [number, number],
  height: number = 0
) => {
  return new Vector3(
    start[1] + (end[1] - start[1]) / 2,
    height,
    start[0] + (end[0] - start[0]) / 2
  );
};
export const calculateScale = (
  start: [number, number],
  end: [number, number]
) => {
  return new Vector3(end[1] - start[1], 1, end[0] - start[0]);
};

// robot.tsx utils
const arr = [0, Math.PI / 2, 0, Math.PI * 1.5, Math.PI];

export const getRotationIndex = (before: robotData, current: robotData) => {
  const y = before.position[0] - current.position[0];
  const x = before.position[1] - current.position[1];
  const radian = arr[2 * y + x + 2];

  return [y, x, radian];
};
