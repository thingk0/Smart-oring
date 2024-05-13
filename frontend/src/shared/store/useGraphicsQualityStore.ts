import {
  EffectQuality,
  GraphicsQuality,
  LightQuality,
  RenderingScale,
  ShadowDetail,
} from '@shared/types';
import { create } from 'zustand'; // 변경된 부분

import { createJSONStorage, persist } from 'zustand/middleware';
interface GraphicsQualityOptions {
  graphicsQuality: GraphicsQuality;
  renderingScale: RenderingScale;
  shadowDetail: ShadowDetail;
  ambientOcclusion: boolean;
  lightQuality: LightQuality;
  fov: number;
  effectQuality: EffectQuality;
}
interface UseGraphicsQualityStore extends GraphicsQualityOptions {
  actions: {
    setOption: (value: GraphicsQualityOptions) => void;
    setGraphicsQuality: (value: GraphicsQuality) => void;
    setShadowDetail: (value: ShadowDetail) => void;
    setFov: (value: number) => void;
    setAmbientOcclusion: (value: boolean) => void;
    setLightQuality: (value: LightQuality) => void;
    setRenderingScale: (value: RenderingScale) => void;
    setEffectQuality: (value: EffectQuality) => void;
  };
}
export const lowOption: GraphicsQualityOptions = {
  graphicsQuality: 'low',
  renderingScale: 1,
  shadowDetail: 'off',
  fov: 75,
  ambientOcclusion: false,
  lightQuality: 'low',
  effectQuality: 'low',
};
export const mediumOption: GraphicsQualityOptions = {
  graphicsQuality: 'medium',
  renderingScale: 'auto',
  shadowDetail: 'off',
  fov: 75,
  ambientOcclusion: false,
  lightQuality: 'medium',
  effectQuality: 'low',
};
export const highOption: GraphicsQualityOptions = {
  graphicsQuality: 'high',
  renderingScale: 'auto',
  shadowDetail: 'high',
  fov: 75,
  ambientOcclusion: true,
  lightQuality: 'high',
  effectQuality: 'high',
};
const useGraphicsQualityStore = create<UseGraphicsQualityStore>()(
  persist(
    (set, get) => ({
      ...mediumOption,
      actions: {
        setOption: (value: GraphicsQualityOptions) => set({ ...value }),
        setGraphicsQuality: (value: GraphicsQuality) => {
          if (value === 'low') {
            set({ ...lowOption });
          } else if (value === 'medium') {
            set({ ...mediumOption });
          } else if (value === 'high') {
            set({ ...highOption });
          }
        },
        setShadowDetail: (value: ShadowDetail) => set({ shadowDetail: value }),
        setFov: (value: number) => set({ fov: value }),
        setRenderingScale: (value: RenderingScale) =>
          set({ renderingScale: value }),
        setAmbientOcclusion: (value: boolean) =>
          set({ ambientOcclusion: value }),
        setLightQuality: (value: LightQuality) => set({ lightQuality: value }),
        setEffectQuality: (value: EffectQuality) =>
          set({ effectQuality: value }),
      },
    }),
    {
      name: 'graphics-quality',
      storage: createJSONStorage(() => localStorage),
      partialize: state =>
        Object.fromEntries(
          Object.entries(state).filter(([key]) => !['actions'].includes(key))
        ),
    }
  )
);

export default useGraphicsQualityStore;
