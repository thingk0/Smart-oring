import Charger from './Charger';
import Destination from './Destination';
import Logistic from './Logistic';
import { Point2D } from '../../../shared/types';
import Robot from './Robot';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
interface MapProps {
  resource: {
    read(): MapData;
  };
}

type MapData = {
  charger: PositionData[];
  destination: PositionData[];
  logistic: PositionData[];
};

type PositionData = {
  start: Point2D;
  end: Point2D;
  direction: number;
};

const queryClient = new QueryClient();

function Map({ resource }: MapProps) {
  const data: MapData = resource.read();
  // console.log(data);

  return (
    <>
      {data.charger.map((c, index: number) => {
        return <Charger start={c.start} end={c.end} key={index} />;
      })}
      {data.destination.map((d, index) => {
        return <Destination start={d.start} end={d.end} key={index} />;
      })}
      {data.logistic.map((l, index) => {
        return <Logistic start={l.start} end={l.end} key={index} />;
      })}
      <QueryClientProvider client={queryClient}>
        <Robot />
      </QueryClientProvider>
    </>
  );
}

export default Map;
