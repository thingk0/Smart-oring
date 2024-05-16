import BasicSpeedDial from 'widgets/floating/BasicSpeedDial';
import Analysis from '@entity/Analysis/ui';
import Renderer from './bases/Renderer';
import { createPortal } from 'react-dom';

function App() {
  return (
    <>
<<<<<<< HEAD
      <Renderer />
      <BasicSpeedDial />
=======
      {/* <Renderer /> */}
      {/* <BasicSpeedDial /> */}
>>>>>>> 6970afc22f2c4847605de52cddb3c2573bc0f74b
      {createPortal(<Analysis />, document.body)}
    </>
  );
}

export default App;
