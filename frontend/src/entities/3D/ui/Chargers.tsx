import { convertPosition } from '../../../shared/lib';
import { MapData } from '../../../shared/types';
import { Instances, Model } from './Charger';

function Chargers({ data }: { data: MapData }) {
  return (
    <>
      <Instances>
        {/* {LocateChargers(data.charger[0].end[0])} */}
        {data.charger.map((c, i) => {
          return (
            <Model
              position={convertPosition(c.start, c.end)}
              key={i}
              rotation={[0, Math.PI, 0]}
            ></Model>
          );
        })}
      </Instances>
    </>
  );
}
export default Chargers;
