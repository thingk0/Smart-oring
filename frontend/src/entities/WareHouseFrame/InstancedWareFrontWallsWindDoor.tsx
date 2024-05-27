import { Instances, Model } from './WareFrontWallsWindDoor';

export default function InstancedWareFrontWallsWindDoor() {
  return (
    <>
      <Instances>
        <Model position={[-38.5, 0, 0]}></Model>
        <Model position={[38.5, 0, 0]} rotation={[0, Math.PI, 0]}></Model>
      </Instances>
    </>
  );
}
