import { Point2D } from '@shared/types';
import { create } from 'zustand'; // 변경된 부분

interface UseControlStore {
  isControlMode: boolean;
  nodeList: Point2D[];
  actions: ControlStoreActions;
}
interface ControlStoreActions {
  setIsControlMode: (value: boolean) => void;
  addNodeList: (value: Point2D) => void;
}
const initialValue = {
  isControlMode: false,
  nodeList: [],
};
export const useControlStore = create<UseControlStore>()((set, _get) => ({
  ...initialValue,
  actions: {
    setIsControlMode: (value: boolean) => set({ isControlMode: value }),
    addNodeList: (value: Point2D) =>
      set(status => {
        return { nodeList: [...status.nodeList, value] };
      }),
  },
}));
