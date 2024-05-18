import Destinations from './Destinations';
import Storages from './Storages';
import { useQueries, useQuery } from '@tanstack/react-query';
import InstancedRobot from './InstancedRobot';
import Path from './Path';
import Wall from './Wall';
import Floor from './Floor';
import Chargers from './Chargers';
import { getMap, getReplay, getReplayData } from '@shared/api';
import ReplayInstancedRobot from './ReplayInstancedRobot';
import { useLocation } from 'react-router-dom';
import Conveyors from './Conveyors';
import { WareBigFloor } from '../../WareHouseFrame/WareBigFloor';
import { WareBigRoof } from '../../WareHouseFrame/WareBigRoof';
import InstancedWareBigWallsWind from '../../WareHouseFrame/InstancedWareBigWallsWind';
import InstancedWareFrontWallsWindDoor from '../../WareHouseFrame/InstancedWareFrontWallsWindDoor';
import WareHouseFrame from '@entity/WareHouseFrame/WareHouseFrame';
import { useViewStore } from '@shared/store/useViewStore';

function Map() {
  const { data } = useQuery({
    queryKey: ['map'],
    queryFn: getMap,
  });

  const { currentView } = useViewStore();

  if (data) {
    return (
      <>
        {currentView === 'Monitoring' && <InstancedRobot />}
        {currentView === 'Replay' && <ReplayInstancedRobot />}
        {/* <AGVInstance /> */}
        <Path />
        {/* <Wall /> */}
        {/* <Floor /> */}
        <WareHouseFrame />
        <Chargers data={data} />
        <Destinations data={data} />
        <Storages data={data} />
        <Conveyors data={data} />
      </>
    );
  }
}

export default Map;
