import RadialBar from './RadialBar';

type DashboardProps = {
  resource: {
    read(): any;
  };
};

type DashboardData = {
  todayMissionCount: Array<any>;
  yesterdayMissionCount: Array<any>;
  todayTotalMissionCount: number;
  totalUsagePercent: number;
  amrUsagePercent: [{ amrId: number; UsagePercent: number }];
  amrErrorPercent: [{ amrId: number; ErrorPercent: number }];
  realtimeError: [
    {
      amrId: number;
      missionId: number;
      xcoordinate: number;
      ycoordinate: number;
    },
  ];
  realtimeBottleneck: [
    {
      id: number;
      amrId: number;
      missionId: number;
      bottleneckCreatedAt: string;
      bottleneckPeriod: number;
      xcoordinate: number;
      ycoordinate: number;
    },
  ];
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
