import { useViewStore } from '@shared/store/useViewStore';
import InstancedWareBigWallsWind from './InstancedWareBigWallsWind';
import InstancedWareFrontWallsWindDoor from './InstancedWareFrontWallsWindDoor';
import { WareBigFloor } from './WareBigFloor';
import { WareBigRoof } from './WareBigRoof';

export default function WareHouseFrame() {
  const width = 77;
  const height = 37;
  const { cameraIndex, cameraList } = useViewStore();
  return (
    <group position={[width / 2, 0, height / 2]}>
      <WareBigFloor />
      {!cameraList[cameraIndex].isTop && <WareBigRoof />}
      <InstancedWareFrontWallsWindDoor />
      <InstancedWareBigWallsWind />
    </group>
  );
}
