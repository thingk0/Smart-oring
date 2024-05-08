import { Canvas } from '@react-three/fiber';
import { Loader } from '@react-three/drei';
import Camera from './Camera';
import Scene from './Scene';
import Helpers from './Helper';
import PostProcessing from './Postprocessing';
import Shader from './Shader';

function Renderer() {
  // axesHelper
  // The X axis is red, the Y axis is green and the Z axis is blue.

  return (
    <>
      <Canvas shadows>
        <Helpers />
        <PostProcessing />
        <Shader />
        <Scene />
        <Camera />
      </Canvas>
      <Loader />
    </>
  );
}

export default Renderer;
