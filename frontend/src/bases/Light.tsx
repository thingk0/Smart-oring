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
      <directionalLight ref={lightRef} position={[50, 30, 25]} intensity={5} />
    </>
  );
}

export default Light;
