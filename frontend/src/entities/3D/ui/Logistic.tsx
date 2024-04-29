import { Instance, Merged, useGLTF } from '@react-three/drei';
import { convertPosition } from '../../../shared/lib/index.ts';
import { Point2D } from '../../../shared/types/index.ts';
type DestinationProps = {
  start: Point2D;
  end: Point2D;
};

function Logistic({ start, end }: DestinationProps) {
  return (
    <>
      <Instance position={convertPosition(start, end)} />
    </>
  );
}

export default Logistic;
