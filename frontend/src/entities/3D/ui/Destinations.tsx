import { convertPosition } from '../../../shared/lib';
import { MapData } from '../../../shared/types';
import { Instances, Model } from './Destination';

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
