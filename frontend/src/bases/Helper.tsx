import { OrbitControls } from '@react-three/drei';
import { useViewStore } from '@shared/store/useViewStore';
import { Perf } from 'r3f-perf';

function Helpers() {
  const { currentView, isFPVStatus } = useViewStore();
  return (
    <>
      {/* <Perf position="top-left" /> */}
      <OrbitControls enableRotate={isFPVStatus ? false : true} />
      {/* <gridHelper args={[100, 100]} /> */}
      {/* <axesHelper args={[100]} /> */}
    </>
  );
}

export default Helpers;
