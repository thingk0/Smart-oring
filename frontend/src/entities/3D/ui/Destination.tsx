import { Instance } from '@react-three/drei';
import { calculateScale, convertPosition } from '../../../shared/lib/index.ts';
import { Point2D } from '../../../shared/types/index.ts';
type DestinationProps = {
  start: Point2D;
  end: Point2D;
};
function Destination({ start, end }: DestinationProps) {
  return (
    <>
      <boxGeometry />
      <meshStandardMaterial color="purple" />
      <Instance
        position={convertPosition(start, end)}
        scale={calculateScale(start, end)}
      />
    </>
  );
}

export default Destination;
