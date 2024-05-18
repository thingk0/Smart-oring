import { create } from 'zustand'; // 변경된 부분

import { createJSONStorage, persist } from 'zustand/middleware';
interface UseReplayStore {
  isPlaying: boolean;
  currentTime: number;
  totalTime: number;
  actions: ReplayStoreActions;
  speed: number;
  amrId: number;
}
interface ReplayStoreActions {
  setIsPlaying: (value: boolean) => void;
  setCurrentTime: (value: number) => void;
  setTotalTime: (value: number) => void;
  increaseCurrentTime: () => void;
  setSpeed: (value: number) => void;
  setAmrId: (value: number) => void;
}
const initialValue = {
  isPlaying: false,
  currentTime: 0,
  totalTime: 110,
  speed: 1.0,
  amrId: 1,
};
export const useReplayStore = create<UseReplayStore>()(
  persist(
    (set, get) => ({
      ...initialValue,
      actions: {
        setIsPlaying: (value: boolean) => set({ isPlaying: value }),
        setTotalTime: (value: number) => set({ totalTime: value }),
        setCurrentTime: (value: number) => set({ currentTime: value }),
        setAmrId: (value: number) => set({ amrId: value }),
        increaseCurrentTime: () =>
          set(state => {
            let newCurrentTime = state.currentTime;
            let newIsPlaying = state.isPlaying;
            if (state.currentTime < state.totalTime) {
              newCurrentTime++;
            } else {
              newIsPlaying = !newIsPlaying;
            }
            return {
              currentTime: newCurrentTime,
              isPlaying: newIsPlaying,
            };
          }),
        setSpeed: (value: number) => set({ speed: value }),
      },
    }),
    {
      name: 'replay',
      storage: createJSONStorage(() => localStorage),
      partialize: state =>
        Object.fromEntries(
          Object.entries(state).filter(([key]) => !['actions'].includes(key))
        ),
    }
  )
);
