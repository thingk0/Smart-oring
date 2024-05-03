import Charger from './Charger';
import Destination from './Destination';
import Logistic from './Logistic';
import { Point2D } from '../../../shared/types';
import Robot from './Robot';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import InstancedRobot from './InstancedRobot';
import { Instances, Plane, useGLTF } from '@react-three/drei';
import Logistics from './Logistics';
import Path from './Path';
import Wall from './Wall';
import Floor from './Floor';
import Destinations from './Destinations';
interface MapProps {
  resource: {
    read(): MapData;
  };
}

type MapData = {
  charger: PositionData[];
  destination: PositionData[];
  logistic: PositionData[];
};

type PositionData = {
  start: Point2D;
  end: Point2D;
  direction: number;
};

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
          return <Charger start={c.start} end={c.end} key={index} />;
        })}
      </Instances>
      <Destinations data={data} />
      <Logistics data={data} />
    </>
  );
}

export default Map;
