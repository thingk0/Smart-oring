import { create } from 'zustand'; // 변경된 부분

interface UsePathStore {
  isShow: boolean;
  route: [number, number][];
  actions: PathStoreActions;
}
interface PathStoreActions {
  setIsShow: (value: boolean) => void;
  setRoute: (value: [number, number][]) => void;
}
const initialValue = {
  isShow: false,
  route: [],
};
export const usePathStore = create<UsePathStore>()((set, get) => ({
  ...initialValue,
  actions: {
    setIsShow: (value: boolean) => set({ isShow: value }),
    setRoute: (value: [number, number][]) => set({ route: value }),
  },
}));
