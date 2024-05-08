import { convertPosition } from '../../../shared/lib';
import { MapData } from '../../../shared/types';
import { Instances, Model } from './Storage';

function Storages({ data }: { data: MapData }) {
  return (
    <>
      <Instances>
        {data.storage.map((l, i) => {
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
export default Storages;
