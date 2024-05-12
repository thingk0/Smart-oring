import Analysis from '@entity/Analysis/ui';
import Renderer from './bases/Renderer';
import { createPortal } from 'react-dom';

function App() {
  return (
    <>
      {/* <Renderer /> */}
      {createPortal(<Analysis />, document.body)}
    </>
  );
}

export default App;
