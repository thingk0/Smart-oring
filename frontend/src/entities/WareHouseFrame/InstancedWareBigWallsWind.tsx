import { Instances, Model } from './WareBigWallsWind';

export default function InstancedWareBigWallsWind() {
  return (
    <>
      <Instances>
        <Model position={[0, 0, -18.5]}></Model>
        <Model position={[0, 0, 18.5]} rotation={[0, Math.PI, 0]}></Model>
      </Instances>
    </>
  );
}
