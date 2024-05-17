import BasicSpeedDial from 'widgets/floating/BasicSpeedDial';
import Renderer from './bases/Renderer';
import { createPortal } from 'react-dom';
import CameraView from '@widgets/floating/CameraView';
import { useViewStore } from '@shared/store/useViewStore';
import Analysis from '@entity/Analysis/ui';
import { ReplayPage, SettingPage } from 'pages';

function App() {
  const { isFPVStatus, currentView } = useViewStore();
  return (
    <>
      <Renderer />
      {isFPVStatus && createPortal(<CameraView />, document.body)}
      {currentView === 'Monitoring' && <BasicSpeedDial />}
      {currentView === 'Analysis' && createPortal(<Analysis />, document.body)}
      {currentView === 'Setting' &&
        createPortal(<SettingPage />, document.body)}
      {currentView === 'Replay' && createPortal(<ReplayPage />, document.body)}
    </>
  );
}

export default App;
