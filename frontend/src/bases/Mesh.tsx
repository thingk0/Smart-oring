import { Suspense } from 'react';
import axios from 'axios';
import { Center } from '@react-three/drei';

import { MapData } from '@shared/types';
import Map from '@entity/3D/ui/Map';

function LoadMapData(): {
  read(): MapData;
} {
  let map: MapData | null = null;

  const suspender = axios
    .get(import.meta.env.VITE_BACKEND_SERVER + '/map')
    .then(res => (map = res.data.resultData));

  return {
    read() {
      if (map === null) {
        throw suspender;
      }
      return map;
    },
  };
}

// update mesh in this function
function Mesh() {
  return (
    <>
      <Suspense fallback={null}>
        <Center disableY>
          <Map resource={LoadMapData()} />
        </Center>
      </Suspense>
    </>
  );
}

export default Mesh;
