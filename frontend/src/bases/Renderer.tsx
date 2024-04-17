import { Canvas } from '@react-three/fiber';
import Camera from './Camera';
import Scene from './Scene';
import { OrbitControls } from '@react-three/drei';

function Renderer() {
  // axesHelper
  // The X axis is red, the Y axis is green and the Z axis is blue.

  return (
    <Canvas>
      <OrbitControls />
      <gridHelper args={[100, 100]} />
      <axesHelper args={[5]} />

      <Scene />
      <Camera />
    </Canvas>
  );
}

export default Renderer;
