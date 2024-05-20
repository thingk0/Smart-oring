import { light } from '@mui/material/styles/createPalette';
import { Environment, useHelper } from '@react-three/drei';
import useGraphicsQualityStore from '@shared/store/useGraphicsQualityStore';
import { useRef } from 'react';
import { DirectionalLightHelper, DirectionalLight } from 'three';
import * as THREE from 'three';
// set light at this function
const lightColor = '#dbc3a8';

function Light() {
  const { shadowDetail, lightQuality } = useGraphicsQualityStore();
  // const lightRef = useRef<DirectionalLight>(null!);
  // useHelper(lightRef, DirectionalLightHelper, 3, 'red');
  return (
    <>
      {lightQuality === 'high' && (
        <Environment
          environmentIntensity={0.2}
          files={
            'https://dl.polyhaven.org/file/ph-assets/HDRIs/hdr/1k/rural_asphalt_road_1k.hdr'
          }
          // background
          ground={{ height: 55, radius: 200, scale: 200 }}
        />
      )}
      {lightQuality === 'low' && <ambientLight intensity={1.8} />}
      {lightQuality === 'medium' && <ambientLight intensity={1} />}
      {lightQuality !== 'low' && (
        <directionalLight
          // ref={lightRef}
          position={[60, 15, -10]}
          color={lightColor}
          intensity={0.3}
          target-position={[50, 0, 0]}
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
