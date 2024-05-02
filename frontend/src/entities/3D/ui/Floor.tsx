import * as THREE from 'three';
function Floor() {
  const width = 100;
  const height = 50;
  return (
    <>
      <mesh
        position={[width / 2, -0.01, height / 2]}
        rotation-x={-Math.PI / 2}
        receiveShadow
      >
        <planeGeometry args={[width, height]} />
        <meshStandardMaterial color={0xffffff} side={THREE.DoubleSide} />
      </mesh>
    </>
  );
}
export default Floor;
