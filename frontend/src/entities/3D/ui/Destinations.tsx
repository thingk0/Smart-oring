import { convertPosition } from '../../../shared/lib';
import { Point2D } from '../../../shared/types';
import { Instances, Model } from './Destination';
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

function Destinations({ data }: { data: MapData }) {
  return (
    <>
      <Instances>
        {data.destination.map((d, i) => {
          return (
            <Model
              position={convertPosition(d.start, d.end, 0.2)}
              rotation={[0, Math.PI / 2, 0]}
              key={i}
            ></Model>
          );
        })}
      </Instances>
    </>
  );
}
export default Destinations;
