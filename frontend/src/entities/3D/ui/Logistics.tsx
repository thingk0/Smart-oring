import { Instances, useGLTF } from '@react-three/drei';
import { Point2D } from '../../../shared/types';
import Logistic from './Logistic';
type MapData = {
  charger: PositionData[];
  destination: PositionData[];
  logistic: PositionData[];
};
type PositionData = {
  start: Point2D;
  end: Point2D;
  direction: number;
};
function Logistics({ data }: { data: MapData }) {
  const { nodes, materials } = useGLTF('./models/BigShelves04Group3.glb');
  console.log(nodes);
  console.log(materials);
  return (
    <>
      <Instances
        material={materials.BigShelvesMetall02}
        geometry={nodes.BigShelves04_1.geometry}
      >
        {data.logistic.map((l, index) => {
          return <Logistic start={l.start} end={l.end} key={index} />;
        })}
      </Instances>

      <Instances
        material={materials.BigShelvesMetall01}
        geometry={nodes.BigShelves04_2.geometry}
      >
        {data.logistic.map((l, index) => {
          return <Logistic start={l.start} end={l.end} key={index} />;
        })}
      </Instances>

      <Instances
        material={materials.BigShelvesWood}
        geometry={nodes.BigShelves04_3.geometry}
      >
        {data.logistic.map((l, index) => {
          return <Logistic start={l.start} end={l.end} key={index} />;
        })}
      </Instances>
    </>
  );
}
export default Logistics;
