import { Vector3 } from 'three';
import { robotData } from '../types';
import dayjs from 'dayjs';
import duration from 'dayjs/plugin/duration';
dayjs.extend(duration);

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
  const y = before.xcoordinate - current.xcoordinate;
  const x = before.ycoordinate - current.ycoordinate;
  const radian = arr[2 * y + x + 2];

  return [y, x, radian];
};

export function secondsToHMS(seconds: number) {
  const duration = dayjs.duration(seconds, 'seconds');
  const hours = duration.hours().toString().padStart(2, '0');
  const minutes = duration.minutes().toString().padStart(2, '0');
  const secs = duration.seconds().toString().padStart(2, '0');
  return `${hours}:${minutes}:${secs}`;
}

export function convert2DTo3D(
  value: [number, number][]
): [number, number, number][] {
  return value.map(e => [e[1], 1, e[0]]);
}
