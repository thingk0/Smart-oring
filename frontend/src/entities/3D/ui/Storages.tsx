import { Outlines } from '@react-three/drei';
import { convertPosition } from '../../../shared/lib';
import { MapData } from '../../../shared/types';
import { Instances, Model } from './Storage';

function Storages({ data }: { data: MapData }) {
  const outlines = true;
  return (
    <>
      <Instances>
        {data.storage.map((l, i) => {
          return (
            <Model position={convertPosition(l.start, l.end)} key={i}></Model>
          );
        })}
      </Instances>
    </>
  );
}
export default Storages;
