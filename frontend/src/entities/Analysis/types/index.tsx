export type DashboardData = {
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
