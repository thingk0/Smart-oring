/*
Auto-generated by: https://github.com/pmndrs/gltfjsx
Command: npx gltfjsx@6.2.16 ./public/models/conveyor.glb -I -T -t 
Files: ./public/models/conveyor.glb [298.94KB] > /Users/yizhi/vscode/SSAFY/S10P31S109/frontend/conveyor-transformed.glb [49.58KB] (83%)
*/

import * as THREE from 'three';
import React, {
  useMemo,
  useContext,
  createContext,
  useEffect,
  useState,
  useCallback,
} from 'react';
import { useGLTF, Merged, Outlines } from '@react-three/drei';
import { GLTF } from 'three-stdlib';
import { useControlStore } from '@shared/store/useControlStore';
import { useViewStore } from '@shared/store/useViewStore';

type GLTFResult = GLTF & {
  nodes: {
    AlertOrange005: THREE.Mesh;
    PaletArrow005: THREE.Mesh;
    Pc006: THREE.Mesh;
  };
  materials: {
    M_ColorPalette: THREE.MeshStandardMaterial;
    ['PaletArrow.009']: THREE.MeshStandardMaterial;
    M_Glass: THREE.MeshStandardMaterial;
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
  const { nodes } = useGLTF('/models/conveyor-transformed.glb') as GLTFResult;
  const instances = useMemo(
    () => ({
      AlertOrange: nodes.AlertOrange005,
      PaletArrow: nodes.PaletArrow005,
      Pc: nodes.Pc006,
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
        console.log(e.eventObject.position);
        console.log(e.point);
        if (currentView)
          addNodeList([
            Math.floor(e.eventObject.position.z),
            e.eventObject.position.x - 4,
          ]);
      }}
      onPointerOver={onPointerOver}
      onPointerOut={onPointerOut}
    >
      <instances.AlertOrange position={[-0.391, 2.632, -0.646]}>
        {hovered && currentView === 'Control' && (
          <Outlines thickness={thickness} angle={0} color={color} />
        )}
      </instances.AlertOrange>
      <instances.PaletArrow position={[-0.453, 0.816, 0.101]}>
        {hovered && currentView === 'Control' && (
          <Outlines thickness={thickness} angle={0} color={color} />
        )}
      </instances.PaletArrow>
      <instances.Pc position={[-0.187, 1, 0.101]}>
        {hovered && currentView === 'Control' && (
          <Outlines thickness={thickness} angle={0} color={color} />
        )}
      </instances.Pc>
    </group>
  );
}

useGLTF.preload('/models/conveyor-transformed.glb');