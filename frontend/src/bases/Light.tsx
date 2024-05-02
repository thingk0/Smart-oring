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
        position={[50, 15, 25]}
        intensity={1}
        target-position={[70, 0, 25]}
        castShadow
        shadow-camera-top={1000}
        shadow-camera-bottom={-6}
        shadow-camera-left={-1000}
        shadow-camera-right={1000}
      />
    </>
  );
}

export default Light;
