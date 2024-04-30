function Floor() {
  const width = 100;
  const height = 50;
  return (
    <>
      <mesh
        scale={[width, height, 1]}
        position={[width / 2, 0, height / 2]}
        rotation={[-Math.PI / 2, 0, 0]}
      >
        <planeGeometry />
        <meshStandardMaterial />
      </mesh>
    </>
  );
}
export default Floor;
