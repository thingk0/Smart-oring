import { convertPosition } from '../../../shared/lib';
import { Point2D } from '../../../shared/types';
import { Instances, Model } from './Logistic';
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
function Logistics({ data }: { data: MapData }) {
  return (
    <>
      <Instances>
        {data.logistic.map((l, i) => {
          return (
            <Model
              position={convertPosition(l.start, l.end)}
              rotation={[0, l.direction === 1 ? Math.PI / 2 : 0, 0]}
              key={i}
            ></Model>
          );
        })}
      </Instances>
    </>
  );
}
export default Logistics;
