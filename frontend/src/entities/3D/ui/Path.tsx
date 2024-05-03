import { Line } from '@react-three/drei';
import * as THREE from 'three';

function Path() {
  return (
    <>
      <Line
        points={[
          [0, 0, 0],
          [10, 0, 0],
          [20, 0, 0],
        ]}
        lineWidth={10}
        vertexColors={[
          new THREE.Color('#ff0000'),
          new THREE.Color('#00ff00'),
          new THREE.Color('#0000ff'),
        ]}
      />
    </>
  );
}
export default Path;
