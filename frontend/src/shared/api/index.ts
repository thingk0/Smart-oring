import axios from 'axios';
import { robotData } from '../types';
const url = import.meta.env.VITE_BACKEND_SERVER;
const mockUrl = 'http://localhost:3001';
const getRandomPosition = (position: number[]) => {
  const tmp = Math.round(Math.random());
  return [position[0] + tmp, position[1] + (tmp ^ 1)];
};

// robot movement mocking-api
const instanceAPI = axios.create();

instanceAPI.interceptors.response.use(res => {
  res.data.forEach((robot: robotData) => {
    axios.patch(`http://localhost:3001/robot/${robot.id}`, {
      position: getRandomPosition(robot.position),
    });
  });

  return res;
});

export const getRobotPosition = () =>
  instanceAPI.get(`http://localhost:3001/robot`).then(res => res.data);

export const BackendRobotPosition = () =>
  axios.get(url + '/amr/state').then(res => {
    return res.data.resultData;
  });

export const getMap = () =>
  axios.get(url + '/map').then(res => res.data.resultData);

export const getReplayData = () =>
  axios.get(mockUrl + '/replay').then(res => res.data.resultData);
