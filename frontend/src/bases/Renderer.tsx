import { Canvas, Dpr } from '@react-three/fiber';

import Camera from './Camera';
import Scene from './Scene';
import Helpers from './Helper';
import PostProcessing from './Postprocessing';
import Shader from './Shader';
import CustomLoader from '@entity/Loading/ui';
import useGraphicsQualityStore from '@shared/store/useGraphicsQualityStore';

function Renderer() {
  const { fov, shadowDetail, renderingScale } = useGraphicsQualityStore();
  function calcDpr(): Dpr {
    if (renderingScale === 'auto') {
      return [1, 2];
    } else {
      return [renderingScale, renderingScale];
    }
  }
  return (
    <>
      <Canvas
        shadows={shadowDetail !== 'off' ? true : false}
        camera={{ fov: fov }}
        dpr={calcDpr()}
      >
        <Helpers />
        <PostProcessing />
        <Shader />
        <Scene />
        <Camera />
      </Canvas>
      <CustomLoader />
    </>
  );
}

export default Renderer;
