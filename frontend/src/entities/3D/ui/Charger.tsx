/*
Auto-generated by: https://github.com/pmndrs/gltfjsx
Command: npx gltfjsx@6.2.16 ./public/models/charger.glb -t -T -I --shadows 
Files: ./public/models/charger.glb [9.54KB] > C:\Users\SSAFY\Documents\GitLab\S10P31S109\frontend\charger-transformed.glb [1.7KB] (82%)
*/

import * as THREE from 'three';
import React, { useMemo, useContext, createContext } from 'react';
import { useGLTF, Merged } from '@react-three/drei';
import { GLTF } from 'three-stdlib';

type GLTFResult = GLTF & {
  nodes: {
    Cube: THREE.Mesh;
  };
  materials: {
    Material: THREE.MeshStandardMaterial;
  };
};

type ContextType = Record<
  string,
  React.ForwardRefExoticComponent<JSX.IntrinsicElements['mesh']>
>;

const context = createContext({} as ContextType);
export function Instances({
  children,
  ...props
}: JSX.IntrinsicElements['group']) {
  const { nodes } = useGLTF('/models/charger-transformed.glb') as GLTFResult;
  const instances = useMemo(
    () => ({
      Cube: nodes.Cube,
    }),
    [nodes]
  );
  return (
    <Merged meshes={instances} {...props}>
      {(instances: ContextType) => (
        <context.Provider value={instances} children={children} />
      )}
    </Merged>
  );
}

export function Model(props: JSX.IntrinsicElements['group']) {
  const instances = useContext(context);
  return (
    <group {...props} dispose={null}>
      <instances.Cube scale={0.5} />
    </group>
  );
}

useGLTF.preload('/models/charger-transformed.glb');