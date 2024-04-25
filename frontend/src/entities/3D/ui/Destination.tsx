import { calculateScale, convertPosition } from '../../../shared/lib/index.ts';
import { Point2D } from '../../../shared/types/index.ts';
type DestinationProps = {
  start: Point2D;
  end: Point2D;
};
function Destination({ start, end }: DestinationProps) {
  return (
    <>
      <mesh
        position={convertPosition(start, end)}
        scale={calculateScale(start, end)}
      >
        <boxGeometry />
        <meshStandardMaterial color="purple" />
      </mesh>
    </>
  );
}

export default Destination;
