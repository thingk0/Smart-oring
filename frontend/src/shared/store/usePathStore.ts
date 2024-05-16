import { Point2D } from '@shared/types';
import { create } from 'zustand'; // 변경된 부분

interface UsePathStore {
  isShow: boolean;
  visitedRoutes: Point2D[];
  nextRoutes: Point2D[];
  actions: PathStoreActions;
}
interface PathStoreActions {
  setIsShow: (value: boolean) => void;
  setRoute: (visited: Point2D[], next: Point2D[]) => void;
}
const initialValue = {
  isShow: false,
  visitedRoutes: [],
  nextRoutes: [],
};
export const usePathStore = create<UsePathStore>()((set, get) => ({
  ...initialValue,
  actions: {
    setIsShow: (value: boolean) => set({ isShow: value }),
    setRoute: (visited: Point2D[], next: Point2D[]) =>
      set({ visitedRoutes: visited, nextRoutes: next }),
  },
}));
