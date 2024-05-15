import { create } from 'zustand'; // 변경된 부분

import { createJSONStorage, persist } from 'zustand/middleware';
interface UseReplayStore {
  isPlaying: boolean;
  currentTime: number;
  totalTime: number;
  actions: ReplayStoreActions;
}
interface ReplayStoreActions {
  setIsPlaying: (value: boolean) => void;
  setCurrentTime: (value: number) => void;
  setTotalTime: (value: number) => void;
  increaseCurrentTime: () => void;
}
const initialValue = {
  isPlaying: false,
  currentTime: 0,
  totalTime: 110,
};
export const useReplayStore = create<UseReplayStore>()(
  persist(
    (set, get) => ({
      ...initialValue,
      actions: {
        setIsPlaying: (value: boolean) => set({ isPlaying: value }),
        setTotalTime: (value: number) => set({ totalTime: value }),
        setCurrentTime: (value: number) => set({ currentTime: value }),
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
