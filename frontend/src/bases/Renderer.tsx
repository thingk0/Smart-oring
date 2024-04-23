import { Canvas } from '@react-three/fiber';
import Camera from './Camera';
import Scene from './Scene';
import Helpers from './Helper';

function Renderer() {
  // axesHelper
  // The X axis is red, the Y axis is green and the Z axis is blue.

  return (
    <Canvas>
      <Helpers />
      <Scene />
      <Camera />
    </Canvas>
  );
}

export default Renderer;
