import { DashboardData } from '../types';
import CapacityLineGraph from './CapacityLineGraph';
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
  console.log(data[0]);

  // 실시간 병목, 에러 = 1초마다 생성되거나 없어짐
  // 따라서 tanstack-query 써야할 것 같은데
  // 부모가 리렌더링되니까 자식도 리렌더링 될거 같은데?

  return (
    <>
      <h1>Dashboard</h1>
      <div>
        <div>
          <CapacityLineGraph
            yesterday={data[0].yesterdayOutputGraph}
            today={data[0].todayOutputGraph}
          />
          <div>
            <OperaingRate data={data[0].totalUsagePercent} />
            <UsageRate data={data[0].amrUsagePercent} />
            <ErrorRate data={data[0].amrErrorPercent} />
          </div>
        </div>
        <div>
          <CurrentTime />
          <TodayOutput data={data[0].todayTotalOutput} />
          <RealTimeError data={data[0].realtimeError} />
          <RealTimeBottleneck data={data[0].realtimeBottleneck} />
        </div>
      </div>
    </>
  );
}

export default Dashboard;
