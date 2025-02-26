/*
Auto-generated by: https://github.com/pmndrs/gltfjsx
Command: npx gltfjsx@6.2.16 ./public/models/storage.glb -t -T -I --shadows 
Files: ./public/models/storage.glb [2.01MB] > C:\Users\SSAFY\Documents\GitLab\S10P31S109\frontend\storage-transformed.glb [355.73KB] (82%)
*/

import * as THREE from 'three';
import React, {
  useMemo,
  useContext,
  createContext,
  useCallback,
  useState,
  useEffect,
} from 'react';
import { useGLTF, Merged, Outlines } from '@react-three/drei';
import { GLTF } from 'three-stdlib';

type GLTFResult = GLTF & {
  nodes: {
    BigShelves04001: THREE.Mesh;
    BigShelves04001_1: THREE.Mesh;
    BigShelves04001_2: THREE.Mesh;
    BigShelves04001_3: THREE.Mesh;
    BigShelvesWoodBox: THREE.Mesh;
    Cardboard_box01: THREE.Mesh;
    Cardboard_box05: THREE.Mesh;
  };
  materials: {
    ['BigShelvesMetall03.001']: THREE.MeshStandardMaterial;
    ['BigShelvesMetall01.001']: THREE.MeshStandardMaterial;
    ['BigShelvesWood.001']: THREE.MeshStandardMaterial;
    ['BigShelvesMetall02.001']: THREE.MeshStandardMaterial;
    ['BigShelvesWoodBox.001']: THREE.MeshStandardMaterial;
    ['Cardboard_box06.002']: THREE.MeshStandardMaterial;
    ['Cardboard_box05.001']: THREE.MeshStandardMaterial;
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
  const { nodes } = useGLTF('/models/storage-transformed.glb') as GLTFResult;
  const instances = useMemo(
    () => ({
      BigShelves: nodes.BigShelves04001,
      BigShelves1: nodes.BigShelves04001_1,
      BigShelves2: nodes.BigShelves04001_2,
      BigShelves3: nodes.BigShelves04001_3,
      BigShelvesWoodBox: nodes.BigShelvesWoodBox,
      Cardboardbox: nodes.Cardboard_box01,
      Cardboardbox1: nodes.Cardboard_box05,
    }),
    [nodes]
  );
  return (
    <Merged meshes={instances} {...props} castShadow receiveShadow>
      {(instances: ContextType) => (
        <context.Provider value={instances} children={children} />
      )}
    </Merged>
  );
}

export function Model(props: JSX.IntrinsicElements['group']) {
  const instances = useContext(context);
  const thickness = 0.06;
  const color = 'white';
  const [hovered, setHover] = useState(false);
  useEffect(
    () => void (document.body.style.cursor = hovered ? 'pointer' : 'auto'),
    [hovered]
  );
  const onPointerOver = useCallback(() => setHover(true), []);
  const onPointerOut = useCallback(() => setHover(false), []);
  return (
    <group
      {...props}
      dispose={null}
      onClick={e => {
        console.log(e);
      }}
      onPointerOver={onPointerOver}
      onPointerOut={onPointerOut}
    >
      <group scale={[1.167, 1, 1]}>
        <instances.BigShelves>
          {hovered && (
            <Outlines thickness={thickness} angle={0} color={color} />
          )}
        </instances.BigShelves>
        <instances.BigShelves1>
          {hovered && (
            <Outlines thickness={thickness} angle={0} color={color} />
          )}
        </instances.BigShelves1>
        <instances.BigShelves2>
          {hovered && (
            <Outlines thickness={thickness} angle={0} color={color} />
          )}
        </instances.BigShelves2>
        <instances.BigShelves3>
          {hovered && (
            <Outlines thickness={thickness} angle={0} color={color} />
          )}
        </instances.BigShelves3>
      </group>
      <instances.BigShelvesWoodBox>
        {hovered && <Outlines thickness={thickness} angle={0} color={color} />}
      </instances.BigShelvesWoodBox>
      <instances.Cardboardbox>
        {hovered && <Outlines thickness={thickness} angle={0} color={color} />}
      </instances.Cardboardbox>
      <instances.Cardboardbox1>
        {hovered && <Outlines thickness={thickness} angle={0} color={color} />}
      </instances.Cardboardbox1>
    </group>
  );
}

useGLTF.preload('/models/storage-transformed.glb');
