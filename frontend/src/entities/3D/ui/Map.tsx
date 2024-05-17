import Destinations from './Destinations';
import Storages from './Storages';
import { useQueries } from '@tanstack/react-query';
import InstancedRobot from './InstancedRobot';
import Path from './Path';
import Wall from './Wall';
import Floor from './Floor';
import Chargers from './Chargers';
import { getMap, getReplayData } from '@shared/api';
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
  const results = useQueries({
    queries: [
      {
        queryKey: ['map'],
        // queryFn: getRobotPosition, : mocking api
        queryFn: getMap,
      },
      {
        queryKey: ['replay'],
        queryFn: getReplayData,
      },
    ],
  });
  const { pathname } = useLocation();

  const { currentView } = useViewStore();

  if (results[0].data) {
    return (
      <>
        {currentView === 'Monitoring' && <InstancedRobot />}
        {pathname === '/replay' && results[1].data && (
          <ReplayInstancedRobot replayData={results[1].data} />
        )}
        {/* <AGVInstance /> */}
        <Path />
        {/* <Wall /> */}
        {/* <Floor /> */}
        <WareHouseFrame />
        <Chargers data={results[0].data} />
        <Destinations data={results[0].data} />
        <Storages data={results[0].data} />
        <Conveyors data={results[0].data} />
      </>
    );
  }
}

export default Map;
