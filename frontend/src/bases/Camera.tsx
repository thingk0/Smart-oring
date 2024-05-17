import { OrthographicCamera } from '@react-three/drei';
import { useFrame } from '@react-three/fiber';
import { Vector3 } from 'three';
import { useViewStore } from '@shared/store/useViewStore';
// set camera at this function
function Camera() {
  const { isFPVStatus, cameraIndex, cameraList } = useViewStore();
  useFrame(state => {
    if (!isFPVStatus) {
      console.log(state.camera.position);
      const location = new Vector3(...cameraList[cameraIndex].position);
      // const angle = new Vector3(...cameraList[cameraIndex].rotation);
      state.camera.position.copy(location);
      const target = new Vector3(...cameraList[cameraIndex].lookAt);
      state.camera.lookAt(target);
    }
  });
  return (
    <camera>
      <OrthographicCamera />
    </camera>
  );
}

export default Camera;
