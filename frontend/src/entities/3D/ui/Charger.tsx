import { Point2D } from '../../../shared/types';

const getPosition = (start: number[], end: number[]) => {
  return {
    width: end[1] - start[1],
    height: end[0] - start[0],
    startX: start[1] + (end[1] - start[1]) / 2,
    startY: start[0] + (end[0] - start[0]) / 2,
  };
};

type ChargerProps = {
  start: Point2D;
  end: Point2D;
};

function Charger({ start, end }: ChargerProps) {
  const { width, height, startX, startY } = getPosition(start, end);

  return (
    <>
      <mesh
        position={[startX, 0, startY]}
        scale={[width, height, 1]}
        rotation={[-Math.PI / 2, 0, 0]}
      >
        <planeGeometry />
        <meshStandardMaterial color="green" />
      </mesh>
    </>
  );
}

export default Charger;
