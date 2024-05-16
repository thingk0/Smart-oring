import BasicSpeedDial from 'widgets/floating/BasicSpeedDial';
import Analysis from '@entity/Analysis/ui';
import Renderer from './bases/Renderer';
import { createPortal } from 'react-dom';

function App() {
  return (
    <>
      <Renderer />
      <BasicSpeedDial />
      {/* <BasicSpeedDial /> */}
      {/* {createPortal(<Analysis />, document.body)} */}
    </>
  );
}

export default App;
