import { convertPosition } from '../../../shared/lib';
import { MapData } from '../../../shared/types';
import { Instances, Model } from './Conveyor';

function Conveyors({ data }: { data: MapData }) {
  if (data.conveyorBelt)
    return (
      <>
        <Instances>
          {data.conveyorBelt.map((d, i) => {
            console.log(convertPosition(d.start, d.end, 0.2));
            console.log('start', d.start);
            console.log('end', d.end);
            return (
              <Model
                position={convertPosition(d.start, d.end)}
                rotation={[0, Math.PI, 0]}
                key={i}
              ></Model>
            );
          })}
        </Instances>
      </>
    );
}
export default Conveyors;
