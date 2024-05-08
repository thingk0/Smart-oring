import ChargeArea from './ChargeArea';
import Destinations from './Destinations';
import Storages from './Storages';
import { MapData } from '../../../shared/types';
import Robot from './Robot';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import InstancedRobot from './InstancedRobot';
import { Instances } from '@react-three/drei';
import Path from './Path';
import Wall from './Wall';
import Floor from './Floor';
import Chargers from './Chargers';
import Conveyors from './Conveyors';
interface MapProps {
  resource: {
    read(): MapData;
  };
}

const queryClient = new QueryClient();

function Map({ resource }: MapProps) {
  const data: MapData = resource.read();
  // console.log(data);

  console.log(data);

  return (
    <>
      <QueryClientProvider client={queryClient}>
        <Robot />
        <InstancedRobot />
      </QueryClientProvider>
      <Path />
      <Wall />
      <Floor />
      <Instances>
        {data.charger.map((c, index: number) => {
          return <ChargeArea start={c.start} end={c.end} key={index} />;
        })}
      </Instances>
      <Chargers data={data} />
      <Destinations data={data} />
      <Storages data={data} />
      <Conveyors data={data} />
    </>
  );
}

export default Map;
