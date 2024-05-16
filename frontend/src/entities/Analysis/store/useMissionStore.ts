import { create } from 'zustand';

type MissionState = {
  missionList: Array<MissionObject> | null;
  setMissionList: (by: Array<MissionObject>) => void;
  missionHistory: MissionHistoryType | null;
  setMissionHistory: (by: MissionHistoryType) => void;
};

export type MissionObject = {
  mission_id: number;
  amr_id: number;
  amr_code: string;
  delay_time: number;
  mission_started_at: string;
  mission_finished_at: string;
};

export type MissionHistoryType = {
  missionExecutionTimeAnalysis: {
    amrCode: string;
    missionId: number;
    totalExecutionTime: number;
    processingTime: number;
    bottleneckTime: number;
    chargingTime: number;
    errorTime: number;
    dischargingTime: number;
  };
  amrStatusTimeline: [
    {
      amrStatus: string;
      startToEnd: Array<number>;
    },
  ];
};

const useMissionStore = create<MissionState>()(set => ({
  missionList: null,
  setMissionList: by => set(_ => ({ missionList: by })),
  missionHistory: null,
  setMissionHistory: by => set(_ => ({ missionHistory: by })),
}));

export default useMissionStore;
