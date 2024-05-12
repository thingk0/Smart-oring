import { Canvas } from '@react-three/fiber';

import Camera from './Camera';
import Scene from './Scene';
import Helpers from './Helper';
import PostProcessing from './Postprocessing';
import Shader from './Shader';
import CustomLoader from '@entity/Loading/ui';
import useGraphicsQualityStore from '@shared/store/useGraphicsQualityStore';

function Renderer() {
  const { fov } = useGraphicsQualityStore();
  return (
    <>
      <Canvas shadows camera={{ fov: fov }}>
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
