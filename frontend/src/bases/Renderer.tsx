import { Canvas } from '@react-three/fiber';

import Camera from './Camera';
import Scene from './Scene';
import Helpers from './Helper';
import PostProcessing from './Postprocessing';
import Shader from './Shader';
import CustomLoader from '@entity/Loading/ui';

function Renderer() {
  return (
    <>
      <Canvas shadows>
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
