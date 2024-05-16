import { Suspense } from 'react';
import { Center } from '@react-three/drei';

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
