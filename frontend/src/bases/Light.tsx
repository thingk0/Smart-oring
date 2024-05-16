import { light } from '@mui/material/styles/createPalette';
import { Environment, useHelper } from '@react-three/drei';
import useGraphicsQualityStore from '@shared/store/useGraphicsQualityStore';
import { useRef } from 'react';
import { DirectionalLightHelper, DirectionalLight } from 'three';

// set light at this function
const lightColor = 'white';

function Light() {
  const { shadowDetail, lightQuality } = useGraphicsQualityStore();
  const lightRef = useRef<DirectionalLight>(null!);
  // useHelper(lightRef, DirectionalLightHelper, 3, 'red');
  return (
    <>
      {lightQuality === 'high' && (
        <Environment preset="warehouse" environmentIntensity={0.3} />
      )}
      {lightQuality === 'low' && <ambientLight intensity={1.8} />}
      {lightQuality === 'medium' && <ambientLight intensity={1} />}
      {lightQuality !== 'low' && (
        <directionalLight
          ref={lightRef}
          position={[0, 10, 0]}
          color={lightColor}
          intensity={0.8}
          target-position={[0, 0, 0]}
          castShadow
          shadow-camera-top={100}
          shadow-camera-bottom={-100}
          shadow-camera-left={-100}
          shadow-camera-right={100}
          shadow-mapSize={[512 * 10, 512 * 10]}
        />
      )}
    </>
  );
}

export default Light;
