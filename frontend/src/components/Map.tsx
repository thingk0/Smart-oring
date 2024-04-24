import Charger from './Charger';

type MapData = {
  charger: PositionData[];
  destination: PositionData[];
  logistic: PositionData[];
};

type PositionData = {
  start: number[];
  end: number[];
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
    </>
  );
}

export default Map;
