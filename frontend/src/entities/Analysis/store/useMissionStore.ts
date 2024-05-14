import { create } from 'zustand';

type MissionState = {
  missionList: Array<MissionObject> | null;
  setMissionList: (by: Array<MissionObject>) => void;
  missionHistory: MissionHistory | null;
  setMissionHistory: (by: MissionHistory) => void;
};

export type MissionObject = {
  mission_id: number;
  amr_id: number;
  amr_code: string;
  delay_time: number;
  mission_started_at: string;
  mission_finished_at: string;
};

type MissionHistory = {
  total_execution_time: number;
  processing_time: number;
  bottleneck_time: number;
  charging_time: number;
  error_time: number;
  discharging_time: number;
  mission_infos: Array<string>;
};

const useMissionStore = create<MissionState>()(set => ({
  missionList: null,
  setMissionList: by => set(_ => ({ missionList: by })),
  missionHistory: null,
  setMissionHistory: by => set(_ => ({ missionHistory: by })),
}));

export default useMissionStore;
