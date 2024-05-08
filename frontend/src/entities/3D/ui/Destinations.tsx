import { convertPosition } from '../../../shared/lib';
import { MapData } from '../../../shared/types';
import { Instances, Model } from './Destination';

function Destinations({ data }: { data: MapData }) {
  return (
    <>
      <Instances>
        {data.destination.map((d, i) => {
          console.log(convertPosition(d.start, d.end, 0.2));
          console.log('start', d.start);
          console.log('end', d.end);
          return (
            <Model
              position={convertPosition(d.start, d.end)}
              scale={[d.end[0] - d.start[0], 2, d.end[1] - d.start[1]]}
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
