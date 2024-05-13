import { DashboardData } from '../types';
import CurrentTime from './CurrentTime';
import ErrorRate from './ErrorRate';
import OperaingRate from './OperatingRate';
import RealTimeBottleneck from './RealTimeBottleneck';
import RealTimeError from './RealTimeError';
import TodayOutput from './TodayOutput';
import UsageRate from './UsageRate';

type DashboardProps = {
  resource: {
    read(): any;
  };
};

function Dashboard({ resource }: DashboardProps) {
  const data: DashboardData[] = resource.read();
  // console.log(data);

  return (
    <>
      <h1>Dashboard</h1>
      <div>
        {/* <div>
          <div>미션 처리량</div>
          <div>
            <OperaingRate data={data[0].totalUsagePercent} />
            <UsageRate data={data[0].amrUsagePercent} />
            <ErrorRate data={data[0].amrErrorPercent} />
          </div>
        </div> */}
        <div>
          <CurrentTime />
          <TodayOutput data={data[0].todayTotalMissionCount} />
          <RealTimeError data={data[0].realtimeError} />
          <RealTimeBottleneck data={data[0].realtimeBottleneck} />
        </div>
      </div>
    </>
  );
}

export default Dashboard;
