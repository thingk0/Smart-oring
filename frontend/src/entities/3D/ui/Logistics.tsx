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
        castShadow
        material={materials.BigShelvesMetall03}
        geometry={nodes.BigShelves04_1.geometry}
      >
        {data.logistic.map((l, index) => {
          return (
            <Logistic
              start={l.start}
              end={l.end}
              direction={l.direction}
              key={index}
            />
          );
        })}
      </Instances>

      <Instances
        castShadow
        material={materials.BigShelvesMetall01}
        geometry={nodes.BigShelves04_2.geometry}
      >
        {data.logistic.map((l, index) => {
          return (
            <Logistic
              start={l.start}
              end={l.end}
              direction={l.direction}
              key={index}
            />
          );
        })}
      </Instances>

      <Instances
        castShadow
        material={materials.BigShelvesWood}
        geometry={nodes.BigShelves04_3.geometry}
      >
        {data.logistic.map((l, index) => {
          return (
            <Logistic
              start={l.start}
              end={l.end}
              direction={l.direction}
              key={index}
            />
          );
        })}
      </Instances>
      <Instances
        castShadow
        material={materials.BigShelvesMetall02}
        geometry={nodes.BigShelves04_4.geometry}
      >
        {data.logistic.map((l, index) => {
          return (
            <Logistic
              start={l.start}
              end={l.end}
              direction={l.direction}
              key={index}
            />
          );
        })}
      </Instances>
      {/* 상자 */}
      <Instances
        castShadow
        material={materials.BigShelvesWoodBox}
        geometry={nodes.BigShelvesWoodBox.geometry}
      >
        {data.logistic.map((l, index) => {
          return (
            <Logistic
              start={l.start}
              end={l.end}
              direction={l.direction}
              key={index}
            />
          );
        })}
      </Instances>
      <Instances
        castShadow
        material={materials.Cardboard_box05}
        geometry={nodes.Cardboard_box01.geometry}
      >
        {data.logistic.map((l, index) => {
          return (
            <Logistic
              start={l.start}
              end={l.end}
              direction={l.direction}
              key={index}
            />
          );
        })}
      </Instances>
      <Instances
        castShadow
        material={materials.Cardboard_box06}
        geometry={nodes.Cardboard_box05.geometry}
      >
        {data.logistic.map((l, index) => {
          return (
            <Logistic
              start={l.start}
              end={l.end}
              direction={l.direction}
              key={index}
            />
          );
        })}
      </Instances>
    </>
  );
}
export default Logistics;
