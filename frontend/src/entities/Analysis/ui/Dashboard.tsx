import { DashboardData } from '../types';
import RadialBar from './RadialBar';

type DashboardProps = {
  resource: {
    read(): any;
  };
};

function Dashboard({ resource }: DashboardProps) {
  const data: DashboardData[] = resource.read();
  console.log(data);

  return (
    <>
      <h1>Dashboard</h1>
      <div>
        <RadialBar data={data[0].totalUsagePercent} />
      </div>
      {/* <div>AMR 사용률</div>
      <div>AMR 에러율</div> */}
    </>
  );
}

export default Dashboard;
