import { create } from 'zustand'; // 변경된 부분

import { createJSONStorage, persist } from 'zustand/middleware';
interface UseViewStore {
  cameraIndex: number;
  isFPVStatus: boolean;
  isViewAnalysis: boolean;
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
  setIsViewAnalysis: (value: boolean) => void;
}

const initialValue = {
  isFPVStatus: false,
  isViewAnalysis: false,
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
        setIsViewAnalysis: (value: boolean) => {
          console.log(value);
          return set({ isViewAnalysis: value });
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
