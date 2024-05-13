<<<<<<< HEAD
import BasicSpeedDial from 'widgets/floating/BasicSpeedDial';
=======
import Analysis from '@entity/Analysis/ui';
>>>>>>> d539b8de916d0dfd0a466ef2ae64a8aaa8455f4a
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
      {createPortal(<Analysis />, document.body)}
>>>>>>> d539b8de916d0dfd0a466ef2ae64a8aaa8455f4a
    </>
  );
}

export default App;
