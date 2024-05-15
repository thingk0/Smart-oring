import Destinations from './Destinations';
import Storages from './Storages';
import { MapData } from '../../../shared/types';
import {
  QueryClient,
  QueryClientProvider,
  useQuery,
} from '@tanstack/react-query';
import InstancedRobot from './InstancedRobot';
import Path from './Path';
import Wall from './Wall';
import Floor from './Floor';
import Chargers from './Chargers';
import { getMap } from '@shared/api';

function Map() {
  const { data, isPending } = useQuery({
    queryKey: ['map'],
    // queryFn: getRobotPosition, : mocking api
    queryFn: getMap,
  });
  if (data) {
    return (
      <>
        <InstancedRobot />
        {/* <AGVInstance /> */}
        <Path />
        <Wall />
        <Floor />
        <Chargers data={data} />
        <Destinations data={data} />
        <Storages data={data} />
        {/* <Conveyors data={data} /> */}
      </>
    );
  }
}

export default Map;
