import { convertPosition } from '../../../shared/lib';
import { MapData, Point2D } from '../../../shared/types';
import { Instances, Model } from './Destination';

function Destinations({ data }: { data: MapData }) {
  return (
    <>
      <Instances>
        {data.destination.map((d, i) => {
          const end: Point2D = [d.start[0] + 3, d.start[1] + 3];
          return (
            <Model
              position={convertPosition(d.start, end)}
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
