import { convertPosition } from '../../../shared/lib';
import { MapData } from '../../../shared/types';
import { Instances, Model } from './Charger';
function LocateChargers(end: number): JSX.Element[] {
  console.log(end);
  let chargers: JSX.Element[] = [];
  for (let i = 2; i < end - 1; i += 2) {
    chargers.push(<Model position={convertPosition([i, 1], [i, 1])}></Model>);
  }
  console.log(chargers.length);
  return chargers;
}
function Chargers({ data }: { data: MapData }) {
  return (
    <>
      <Instances>{LocateChargers(data.charger[0].end[0])}</Instances>
    </>
  );
}
export default Chargers;
