/*
Auto-generated by: https://github.com/pmndrs/gltfjsx
Command: npx gltfjsx@6.2.16 ./public/models/Cardboard_box.glb -t -T 
Files: ./public/models/Cardboard_box.glb [1.39MB] > C:\Users\SSAFY\Documents\GitLab\S10P31S109\frontend\Cardboard_box-transformed.glb [51.82KB] (96%)
*/

import * as THREE from 'three';
import { useGLTF } from '@react-three/drei';
import { GLTF } from 'three-stdlib';

type GLTFResult = GLTF & {
  nodes: {
    Cardboard_box_1: THREE.Mesh;
  };
  materials: {
    Cardboard_box01: THREE.MeshStandardMaterial;
  };
};

export function CardboardBox(props: JSX.IntrinsicElements['group']) {
  const { nodes, materials } = useGLTF(
    '/models/Cardboard_box-transformed.glb'
  ) as GLTFResult;
  return (
    <group {...props} dispose={null}>
      <mesh
        geometry={nodes.Cardboard_box_1.geometry}
        material={materials.Cardboard_box01}
      />
    </group>
  );
}

useGLTF.preload('/models/Cardboard_box-transformed.glb');