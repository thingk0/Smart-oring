import { Suspense } from 'react';
import axios from 'axios';
import { Center } from '@react-three/drei';

import { MapData } from '@shared/types';
import Map from '@entity/3D/ui/Map';

// update mesh in this function
function Mesh() {
  return (
    <>
      <Suspense fallback={null}>
        <Center disableY>
          <Map />
        </Center>
      </Suspense>
    </>
  );
}

export default Mesh;
