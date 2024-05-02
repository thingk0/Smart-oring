import { useHelper } from '@react-three/drei';
import { useRef } from 'react';
import { DirectionalLightHelper } from 'three';

// set light at this function
const lightColor = 'white';

function Light() {
  const lightRef = useRef(null);
  useHelper(lightRef, DirectionalLightHelper, 3, 'red');
  return (
    <>
      <ambientLight color={lightColor} intensity={1} />
      <directionalLight
        ref={lightRef}
        position={[0, 10, 0]}
        intensity={0.8}
        target-position={[0, 0, 0]}
        castShadow
        shadow-camera-top={100}
        shadow-camera-bottom={-100}
        shadow-camera-left={-100}
        shadow-camera-right={100}
        shadow-mapSize={[512 * 10, 512 * 10]}
      />
    </>
  );
}

export default Light;
