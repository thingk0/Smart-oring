import BasicSpeedDial from 'widgets/floating/BasicSpeedDial';
import Renderer from './bases/Renderer';
import { createPortal } from 'react-dom';
import CameraView from '@widgets/floating/CameraView';
import { useViewStore } from '@shared/store/useViewStore';

function App() {
  const { isFPVStatus } = useViewStore();
  return (
    <>
      <Renderer />
      <BasicSpeedDial />
      {isFPVStatus && createPortal(<CameraView />, document.body)}
      {/* {createPortal(<Analysis />, document.body)} */}
    </>
  );
}

export default App;
