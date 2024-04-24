import { calculateScale, convertPosition } from './shared/lib/utils';
type DestinationProps = {
  start: [number, number];
  end: [number, number];
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
