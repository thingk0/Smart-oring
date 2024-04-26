import axios from 'axios';
import { robotData } from '../types';

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

//   {
//     "status": "success",
//     "fetchStatus": "fetching",
//     "isPending": false,
//     "isSuccess": true,
//     "isError": false,
//     "isInitialLoading": false,
//     "isLoading": false,
//     "data": [],
//     "dataUpdatedAt": 1714096041610,
//     "error": null,
//     "errorUpdatedAt": 0,
//     "failureCount": 0,
//     "failureReason": null,
//     "errorUpdateCount": 0,
//     "isFetched": true,
//     "isFetchedAfterMount": true,
//     "isFetching": true,
//     "isRefetching": true,
//     "isLoadingError": false,
//     "isPaused": false,
//     "isPlaceholderData": false,
//     "isRefetchError": false,
//     "isStale": true
// }
