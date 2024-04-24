import Charger from './Charger';
import Destination from './Destination';

type MapData = {
  charger: PositionData[];
  destination: PositionData[];
  logistic: PositionData[];
};

type PositionData = {
  start: [number, number];
  end: [number, number];
  direction: number;
};

function Map({ resource }) {
  const data: MapData = resource.read();
  console.log(data);

  return (
    <>
      {data.charger.map((c, index: number) => {
        return <Charger start={c.start} end={c.end} key={index} />;
      })}
      {data.destination.map((d, index) => {
        return <Destination start={d.start} end={d.end} key={index} />;
      })}
    </>
  );
}

export default Map;
