import { useGLTF } from '@react-three/drei';
import { Group, Mesh, MeshStandardMaterial, Object3D, Vector3 } from 'three';

// type Geometry = {
//   AuxScene: Group;
//   Cube013: Mesh;
//   Cube013_1: Mesh;
//   crate: Object3D;
//   modelgltf: Object3D;
// };

// type Material = {
//   'BrownDark.057': MeshStandardMaterial;
//   'Metal.089': MeshStandardMaterial;
// };

// type GLTF = {
//   nodes: Geometry;
//   materials: Material;
// };

type BoxProps = {
  position: Vector3;
};

export function BoxModel({ position }: BoxProps): React.ReactElement {
  const { nodes, materials } = useGLTF('./models/box.glb');

  return (
    <group dispose={null}>
      <group rotation={[Math.PI / 2, 0, 0]} position={position}>
        <mesh
          geometry={nodes.Cube013.geometry}
          material={materials['BrownDark.057']}
        />
        <mesh
          geometry={nodes.Cube013_1.geometry}
          material={materials['Metal.089']}
        />
      </group>
    </group>
  );
}

useGLTF.preload('./models/box.glb');
