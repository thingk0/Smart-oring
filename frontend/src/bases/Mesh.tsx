import { Suspense } from 'react';
import Boxes from '../components/Boxes';
import Floor from '../components/Floor';
import Map from '../entities/3D/ui/Map';
import axios from 'axios';
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
function LoadMapData(): {
  read(): MapData;
} {
  let map: MapData | null = null;
  const suspender = axios.get('http://localhost:3001/map').then(({ data }) => {
    map = data;
  });

  return {
    read() {
      if (map === null) {
        throw suspender;
      }
      return map;
    },
  };
}

// function LoadMap() {
//   return {
//     map: LoadMapData(),
//   };
// }

// update mesh in this function
function Mesh() {
  return (
    <>
      <Suspense
        fallback={
          <mesh>
            <boxGeometry />
            <meshStandardMaterial color={'red'} />
          </mesh>
        }
      >
        <Map resource={LoadMapData()} />
      </Suspense>
    </>
  );
}

export default Mesh;
