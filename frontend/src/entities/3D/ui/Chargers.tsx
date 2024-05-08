import { convertPosition } from '../../../shared/lib';
import { MapData } from '../../../shared/types';
import { Instances, Model } from './Charger';
function LocateChargers(end: number): JSX.Element[] {
  console.log(end);
  let chargers: JSX.Element[] = [];
  for (let i = 2; i < end - 1; i += 2) {
    chargers.push(
      <Model position={convertPosition([i, 1], [i, 1])} key={i}></Model>
    );
  }
  console.log(chargers.length);
  return chargers;
}
function Chargers({ data }: { data: MapData }) {
  return (
    <>
      <Instances>
        {/* {LocateChargers(data.charger[0].end[0])} */}
        {data.charger.map((c, i) => {
          return (
            <Model position={convertPosition(c.start, c.end)} key={i}></Model>
          );
        })}
      </Instances>
    </>
  );
}
export default Chargers;
