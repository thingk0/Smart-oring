import Destinations from './Destinations';
import Storages from './Storages';
import { MapData } from '../../../shared/types';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import Path from './Path';
import Wall from './Wall';
import Floor from './Floor';
import Chargers from './Chargers';
import AGVInstance from './AGVInstance';
interface MapProps {
  resource: {
    read(): MapData;
  };
}

const queryClient = new QueryClient();

function Map({ resource }: MapProps) {
  const data: MapData = resource.read();
  // console.log(data);

  console.log('데이터', data);

  return (
    <>
      <QueryClientProvider client={queryClient}>
        {/* <InstancedRobot /> */}
        <AGVInstance />
      </QueryClientProvider>
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

export default Map;
