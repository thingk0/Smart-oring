import { Instance } from '@react-three/drei';
import { calculateScale, convertPosition } from '../../../shared/lib/index.ts';
import { Point2D } from '../../../shared/types/index.ts';
type DestinationProps = {
  start: Point2D;
  end: Point2D;
  direction: number;
};
function Destination({ start, end }: DestinationProps) {
  return (
    <>
      <Instance
        castShadow
        position={convertPosition(start, end, 0.2)}
        rotation={[0, Math.PI / 2, 0]}
      />
    </>
  );
}

export default Destination;
