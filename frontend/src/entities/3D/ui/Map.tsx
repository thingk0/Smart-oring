import Destinations from './Destinations';
import Storages from './Storages';
import { MapData } from '../../../shared/types';
import {
  QueryClient,
  QueryClientProvider,
  useQueries,
  useQuery,
} from '@tanstack/react-query';
import InstancedRobot from './InstancedRobot';
import Path from './Path';
import Wall from './Wall';
import Floor from './Floor';
import Chargers from './Chargers';
import { getMap, getReplayData } from '@shared/api';
import ReplayInstancedRobot from './ReplayInstancedRobot';
import { useLocation } from 'react-router-dom';

function Map() {
  const results = useQueries({
    queries: [
      {
        queryKey: ['map'],
        // queryFn: getRobotPosition, : mocking api
        queryFn: getMap,
      },
      {
        queryKey: ['replay'],
        queryFn: getReplayData,
      },
    ],
  });
  const { pathname } = useLocation();
  if (results[0].data) {
    return (
      <>
        {pathname !== '/replay' && <InstancedRobot />}
        {pathname === '/replay' && results[1].data && (
          <ReplayInstancedRobot replayData={results[1].data} />
        )}
        {/* <AGVInstance /> */}
        <Path />
        <Wall />
        <Floor />
        <Chargers data={results[0].data} />
        <Destinations data={results[0].data} />
        <Storages data={results[0].data} />
        {/* <Conveyors data={data} /> */}
      </>
    );
  }
}

export default Map;
