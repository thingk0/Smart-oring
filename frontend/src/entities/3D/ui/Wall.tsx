import { convertPosition } from '../../../shared/lib';

function Wall() {
  const width = 100;
  const height = 5;
  const width2 = 50;
  return (
    <>
      {/* 상 */}
      <mesh scale={[width, height, 1]} position={[width / 2, height / 2, 0]}>
        <boxGeometry />
        <meshStandardMaterial />
      </mesh>
      {/* 하 */}
      <mesh scale={[width, height, 1]} position={[width / 2, height / 2, 50]}>
        <boxGeometry />
        <meshStandardMaterial />
      </mesh>
      {/* 좌 */}
      <mesh
        scale={[1, height, width2]}
        position={[0.5, height / 2, width2 / 2]}
      >
        <boxGeometry />
        <meshStandardMaterial />
      </mesh>
      {/* 우 */}
      <mesh
        scale={[1, height, width2]}
        position={[99.5, height / 2, width2 / 2]}
      >
        <boxGeometry />
        <meshStandardMaterial />
      </mesh>
    </>
  );
}
export default Wall;
