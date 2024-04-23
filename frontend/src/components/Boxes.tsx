import { Vector3 } from 'three';
import { BoxModel } from './BoxModel';

function Boxes() {
  const BoxRender = (count: number) => {
    const renderArr = [];

    for (let i = 0; i < count; i++) {
      renderArr.push(
        <BoxModel
          key={i}
          position={new Vector3(1.1 * (i % 10), 0.5, 1.1 * Math.floor(i / 10))}
        />
      );
    }

    return renderArr;
  };

  return <group>{BoxRender(100)}</group>;
}

export default Boxes;
