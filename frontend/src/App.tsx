import BasicSpeedDial from 'widgets/floating/BasicSpeedDial';
import Renderer from './bases/Renderer';
import { createPortal } from 'react-dom';
import CameraView from '@widgets/floating/CameraView';
import { useViewStore } from '@shared/store/useViewStore';
import Analysis from '@entity/Analysis/ui';

function App() {
  const { isFPVStatus, isViewAnalysis } = useViewStore();
  return (
    <>
      <Renderer />
      <BasicSpeedDial />
      {isFPVStatus && createPortal(<CameraView />, document.body)}
      {isViewAnalysis && createPortal(<Analysis />, document.body)}
    </>
  );
}

export default App;
