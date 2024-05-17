import { create } from 'zustand'; // 변경된 부분
import { createJSONStorage, persist } from 'zustand/middleware';

interface UseViewStore {
  cameraIndex: number;
  isFPVStatus: boolean;
  currentView: string;
  missionId: number;
  cameraList: {
    isTop: boolean;
    position: number[];
    lookAt: number[];
  }[];
  actions: ViewStoreActions;
}

interface ViewStoreActions {
  switchCamera: () => void;
  setIsFPVStatus: (value: boolean) => void;
  setCurrentView: (value: string) => void;
  setMissionId: (value: number) => void;
}

const initialValue = {
  missionId: -1,
  isFPVStatus: false,
  currentView: 'Monitoring',
  cameraIndex: 0,
  cameraList: [
    { isTop: false, position: [73, 7, 34], lookAt: [0, 0, 0] },
    { isTop: false, position: [1, 7, 34], lookAt: [50, 0, 0] },
    { isTop: false, position: [2, 6, 2], lookAt: [77, 0, 50] },
    {
      isTop: true,
      position: [40, 30, 20],
      lookAt: [40, 0, 20],
    },
  ],
};

export const useViewStore = create<UseViewStore>()(
  persist(
    (set, get) => ({
      ...initialValue,
      actions: {
        setMissionId: (value: number) => set({ missionId: value }),
        switchCamera: () =>
          set(state => {
            console.log((state.cameraIndex + 1) % state.cameraList.length);
            return {
              cameraIndex: (state.cameraIndex + 1) % state.cameraList.length,
            };
          }),
        setIsFPVStatus: (value: boolean) =>
          set({
            isFPVStatus: value,
          }),
        setCurrentView: (value: string) => {
          return set({ currentView: value });
        },
      },
    }),
    {
      name: 'camera-view',
      storage: createJSONStorage(() => localStorage),
      partialize: state =>
        Object.fromEntries(
          Object.entries(state).filter(
            ([key]) => !['actions', 'cameraList', 'isFPVStatus'].includes(key)
          )
        ),
    }
  )
);
