import { create } from 'zustand';

type MissionState = {
  missionList: Array<MissionObject> | null;
  setMissionList: (by: Array<MissionObject>) => void;
  missionHistory: MissionHistoryType | null;
  setMissionHistory: (by: MissionHistoryType) => void;
};

export type MissionObject = {
  content: [
    {
      mission_id: number;
      amr_id: number;
      amr_code: string;
      delay_time: number;
      mission_started_at: string;
      mission_finished_at: string;
    },
  ];
};

export type MissionHistoryType = {
  mission_execution_time_analysis: {
    amr_code: string;
    mission_id: number;
    total_execution_time: number;
    processing_time: number;
    bottleneck_time: number;
    charging_time: number;
    error_time: number;
    discharging_time: number;
  };
  amr_status_timeline: [
    {
      amr_status: string;
      start_to_end: Array<number>;
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
