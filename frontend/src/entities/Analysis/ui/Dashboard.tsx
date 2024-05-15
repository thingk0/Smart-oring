import styles from './Analysis.module.css';
import { DashboardData } from '../types';
import CapacityLineGraph from './CapacityLineGraph';
import CurrentTime from './CurrentTime';
import ErrorRate from './ErrorRate';
import OperatingRate from './OperatingRate';
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

  // 실시간 병목, 에러 = 1초마다 생성되거나 없어짐
  // 따라서 tanstack-query 써야할 것 같은데
  // 부모가 리렌더링되니까 자식도 리렌더링 될거 같은데?

  // 현재 시간을 빼고 4x3 형태로 수정하기

  // Typography 들어간 부분 싹 다 수정하기

  return (
    <>
      {/* grid로 변경 */}
      <div className={styles.grid}>
        <CapacityLineGraph
          yesterday={data[0].yesterdayOutputGraph}
          today={data[0].todayOutputGraph}
        />
        {/* <CurrentTime /> */}
        <RealTimeError data={data[0].realtimeError} />
        <RealTimeBottleneck data={data[0].realtimeBottleneck} />
        <TodayOutput data={data[0].todayTotalOutput} />
        <OperatingRate data={data[0].totalUsagePercent} />
        <UsageRate data={data[0].amrUsagePercent} />
        <ErrorRate data={data[0].amrErrorPercent} />
      </div>
    </>
  );
}

export default Dashboard;
