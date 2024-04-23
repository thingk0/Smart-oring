import { Suspense } from 'react';
import Boxes from '../components/Boxes';
import Floor from '../components/Floor';
import Map from '../components/Map';
import axios from 'axios';

function LoadMapData() {
  let map: null | Number[][] = null;
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

      <Floor />
    </>
  );
}

export default Mesh;
