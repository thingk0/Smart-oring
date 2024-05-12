import { create } from 'zustand'; // 변경된 부분

import { persist } from 'zustand/middleware';

type graphicsQualityType = 'low' | 'medium' | 'high' | 'custom';
type shadowDetailType = 'off' | 'low' | 'high';

interface UseGraphicsQualityStore {
  graphicsQuality: graphicsQualityType;
  shadowDetail: shadowDetailType;
  fov: number;
  actions: {
    setGraphicsQuality: (value: graphicsQualityType) => void;
    setShadowDetail: (value: shadowDetailType) => void;
    setFov: (value: number) => void;
  };
}

const useGraphicsQualityStore = create<UseGraphicsQualityStore>()(
  persist(
    (set, get) => ({
      graphicsQuality: 'medium',
      shadowDetail: 'off',
      fov: 75,
      actions: {
        setGraphicsQuality: (value: graphicsQualityType) => {
          usePerformanceSettingStore.setState(() => ({ isShadowOn: false }));
          set({ graphicsQuality: value });
        },
        setShadowDetail: (value: shadowDetailType) =>
          set({ shadowDetail: value }),
        setFov: (value: number) => set({ fov: value }),
      },
    }),
    {
      name: 'graphics-quality',
    }
  )
);

export default useGraphicsQualityStore;
