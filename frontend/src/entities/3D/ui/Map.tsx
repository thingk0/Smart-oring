import Charger from './Charger';
import Destination from './Destination';
import Logistic from './Logistic';
import { Point2D } from '../../../shared/types';
import { Instances, useGLTF } from '@react-three/drei';
import Logistics from './Logistics';
import Path from './Path';
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

function Map({ resource }: MapProps) {
  const data: MapData = resource.read();

  console.log(data);

  return (
    <>
      <Path />
      <Instances>
        {data.charger.map((c, index: number) => {
          return <Charger start={c.start} end={c.end} key={index} />;
        })}
      </Instances>
      <Instances>
        {data.destination.map((d, index) => {
          return <Destination start={d.start} end={d.end} key={index} />;
        })}
      </Instances>

      <Logistics data={data} />
    </>
  );
}

export default Map;
