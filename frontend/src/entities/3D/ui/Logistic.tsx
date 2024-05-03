import { Instance, Merged, useGLTF } from '@react-three/drei';
import { convertPosition } from '../../../shared/lib/index.ts';
import { Point2D } from '../../../shared/types/index.ts';
type DestinationProps = {
  start: Point2D;
  end: Point2D;
  direction: number;
};

function Logistic({ start, end, direction }: DestinationProps) {
  return (
    <>
      <Instance
        position={convertPosition(start, end)}
        rotation={[0, direction === 1 ? Math.PI / 2 : 0, 0]}
      />
    </>
  );
}

export default Logistic;
