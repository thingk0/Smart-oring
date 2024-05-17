import { OrbitControls } from '@react-three/drei';
import { Perf } from 'r3f-perf';

function Helpers() {
  return (
    <>
      <Perf position="top-left" />
      <OrbitControls enableRotate={false} />
      <gridHelper args={[100, 100]} />
      {/* <axesHelper args={[100]} /> */}
    </>
  );
}

export default Helpers;
