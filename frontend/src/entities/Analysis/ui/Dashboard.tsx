import styles from './Analysis.module.css';
import { DashboardData } from '../types';
import CapacityLineGraph from './CapacityLineGraph';
import ErrorRate from './ErrorRate';
import OperatingRate from './OperatingRate';
import RealTimeBottleneck from './RealTimeBottleneck';
import RealTimeError from './RealTimeError';
import UsageRate from './UsageRate';

type DashboardProps = {
  resource: {
    read(): any;
  };
};

function Dashboard({ resource }: DashboardProps) {
  const data: DashboardData = resource.read();

  // 실시간 병목, 에러 = 1초마다 생성되거나 없어짐
  // 따라서 tanstack-query 써야할 것 같은데
  // 부모가 리렌더링되니까 자식도 리렌더링 될거 같은데?

  return (
    <>
      {/* grid로 변경 */}
      <div className={styles.grid}>
        <RealTimeBottleneck data={data.realtimeBottleneck} />
        <RealTimeError data={data.realtimeError} />

        <UsageRate data={data.amrUsagePercent} />
        <ErrorRate data={data.amrErrorPercent} />

        <CapacityLineGraph
          yesterday={data.yesterdayOutputGraph}
          today={data.todayOutputGraph}
          total={data.todayTotalOutput}
        />
        <OperatingRate data={data.totalUsagePercent} />
      </div>
    </>
  );
}

export default Dashboard;
