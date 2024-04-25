import { Instance, Merged, useGLTF } from '@react-three/drei';
import { convertPosition } from '../../../shared/lib/index.ts';
import { Point2D } from '../../../shared/types/index.ts';
type DestinationProps = {
  start: Point2D;
  end: Point2D;
};

function Logistic({ start, end }: DestinationProps) {
  const { nodes, materials } = useGLTF('./models/BigShelves04Group3.glb');
  console.log(nodes);
  console.log(materials);
  return (
    <>
      <Merged meshes={nodes}>
        {({
          BigShelves04_1,
          BigShelves04_2,
          BigShelves04_3,
          BigShelves04_4,
        }) => (
          <>
            <BigShelves04_1
              position={convertPosition(start, end)}
            ></BigShelves04_1>
            <BigShelves04_2
              position={convertPosition(start, end)}
            ></BigShelves04_2>
            <BigShelves04_3
              position={convertPosition(start, end)}
            ></BigShelves04_3>
            <BigShelves04_4
              position={convertPosition(start, end)}
            ></BigShelves04_4>
          </>
        )}
      </Merged>
    </>
  );
}

export default Logistic;
