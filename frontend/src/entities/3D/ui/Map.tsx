import Destinations from './Destinations';
import Storages from './Storages';
import { useQuery } from '@tanstack/react-query';
import InstancedRobot from './InstancedRobot';
import Path from './Path';
import Chargers from './Chargers';
import { getMap } from '@shared/api';
import ReplayInstancedRobot from './ReplayInstancedRobot';
import Conveyors from './Conveyors';
import WareHouseFrame from '@entity/WareHouseFrame/WareHouseFrame';
import { useViewStore } from '@shared/store/useViewStore';
import ReplayPath from './ReplayPath';

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
        {currentView === 'Monitoring' && <Path />}
        {currentView === 'Replay' && <ReplayPath />}
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
