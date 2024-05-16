import InstancedWareBigWallsWind from './InstancedWareBigWallsWind';
import InstancedWareFrontWallsWindDoor from './InstancedWareFrontWallsWindDoor';
import { WareBigFloor } from './WareBigFloor';
import { WareBigRoof } from './WareBigRoof';

export default function WareHouseFrame() {
  const width = 77;
  const height = 37;
  return (
    <group position={[width / 2, 0, height / 2]}>
      <WareBigFloor />
      {/* <WareBigRoof /> */}
      <InstancedWareFrontWallsWindDoor />
      <InstancedWareBigWallsWind />
    </group>
  );
}
