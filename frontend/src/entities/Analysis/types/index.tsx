export type DashboardData = {
  todayOutputGraph: Array<number>;
  yesterdayOutputGraph: Array<number>;
  todayTotalOutput: number;
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
