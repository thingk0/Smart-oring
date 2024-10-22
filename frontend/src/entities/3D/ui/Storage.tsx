/*
Auto-generated by: https://github.com/pmndrs/gltfjsx
Command: npx gltfjsx@6.2.16 ./public/models/BigStorage.glb -t -T -I 
Files: ./public/models/BigStorage.glb [2.2MB] > C:\Users\m_a_y\OneDrive\문서\SSAFY\S10P31S109\frontend\BigStorage-transformed.glb [384KB] (83%)
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
import { useControlStore } from '@shared/store/useControlStore';
import { useViewStore } from '@shared/store/useViewStore';

type GLTFResult = GLTF & {
  nodes: {
    BigShelves01001: THREE.Mesh;
    BigShelves01001_1: THREE.Mesh;
    BigShelves01001_2: THREE.Mesh;
    BigShelves01001_3: THREE.Mesh;
    BigShelvesWoodBox: THREE.Mesh;
    Cardboard_box07_1: THREE.Mesh;
    Cardboard_box07_2: THREE.Mesh;
    Cardboard_box08: THREE.Mesh;
  };
  materials: {
    ['BigShelvesMetall03.001']: THREE.MeshStandardMaterial;
    ['BigShelvesMetall01.001']: THREE.MeshStandardMaterial;
    ['BigShelvesWood.001']: THREE.MeshStandardMaterial;
    ['BigShelvesMetall02.001']: THREE.MeshStandardMaterial;
    ['BigShelvesWoodBox.001']: THREE.MeshStandardMaterial;
    Cardboard_box06: THREE.MeshStandardMaterial;
    Cardboard_box07: THREE.MeshStandardMaterial;
    Cardboard_box05: THREE.MeshStandardMaterial;
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
  const { nodes } = useGLTF('/models/BigStorage-transformed.glb') as GLTFResult;
  const instances = useMemo(
    () => ({
      BigShelves: nodes.BigShelves01001,
      BigShelves1: nodes.BigShelves01001_1,
      BigShelves2: nodes.BigShelves01001_2,
      BigShelves3: nodes.BigShelves01001_3,
      BigShelvesWoodBox: nodes.BigShelvesWoodBox,
      Cardboardbox: nodes.Cardboard_box07_1,
      Cardboardbox1: nodes.Cardboard_box07_2,
      Cardboardbox2: nodes.Cardboard_box08,
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
    () =>
      void (document.body.style.cursor =
        hovered && currentView === 'Control' ? 'pointer' : 'auto'),
    [hovered]
  );
  const onPointerOver = useCallback(() => setHover(true), []);
  const onPointerOut = useCallback(() => setHover(false), []);
  const {
    actions: { addNodeList },
  } = useControlStore();
  const { currentView } = useViewStore();

  return (
    <group
      {...props}
      dispose={null}
      onClick={e => {
        e.stopPropagation();
        if (currentView === 'Control')
          addNodeList([
            Math.round(e.eventObject.position.z),
            Math.round(e.eventObject.position.x),
          ]);
      }}
      onPointerOver={onPointerOver}
      onPointerOut={onPointerOut}
    >
      <instances.BigShelvesWoodBox>
        {hovered && currentView === 'Control' && (
          <Outlines thickness={thickness} angle={0} color={color} />
        )}
      </instances.BigShelvesWoodBox>
      <instances.Cardboardbox2>
        {hovered && currentView === 'Control' && (
          <Outlines thickness={thickness} angle={0} color={color} />
        )}
      </instances.Cardboardbox2>
      <instances.BigShelves>
        {hovered && currentView === 'Control' && (
          <Outlines thickness={thickness} angle={0} color={color} />
        )}
      </instances.BigShelves>
      <instances.BigShelves1>
        {hovered && currentView === 'Control' && (
          <Outlines thickness={thickness} angle={0} color={color} />
        )}
      </instances.BigShelves1>
      <instances.BigShelves2>
        {hovered && currentView === 'Control' && (
          <Outlines thickness={thickness} angle={0} color={color} />
        )}
      </instances.BigShelves2>
      <instances.BigShelves3>
        {hovered && currentView === 'Control' && (
          <Outlines thickness={thickness} angle={0} color={color} />
        )}
      </instances.BigShelves3>
      <instances.Cardboardbox>
        {hovered && currentView === 'Control' && (
          <Outlines thickness={thickness} angle={0} color={color} />
        )}
      </instances.Cardboardbox>
      <instances.Cardboardbox1>
        {hovered && currentView === 'Control' && (
          <Outlines thickness={thickness} angle={0} color={color} />
        )}
      </instances.Cardboardbox1>
    </group>
  );
}

useGLTF.preload('/models/BigStorage-transformed.glb');
