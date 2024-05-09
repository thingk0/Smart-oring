import { Vector3 } from 'three';
import { convertPosition } from '../../../shared/lib';
import { MapData, Point2D } from '../../../shared/types';
import { Model, Instances } from './Conveyor';

function Conveyor({ data }: { data: MapData }) {
  function getScale(s: Point2D, e: Point2D) {
    const height = e[0] - s[0];
    const width = e[1] - s[1];
    // return new Vector3(height / 1.6, 1, width / 20);
    return new Vector3(width / 8.05, 1, height / 1.6);
  }
  return (
    <>
      <Instances>
        {data.conveyor.map((c, i) => {
          return (
            <Model
              position={convertPosition(c.start, c.end)}
              scale={getScale(c.start, c.end)}
              key={i}
            />
          );
        })}
      </Instances>
    </>
  );
}
export default Conveyor;
