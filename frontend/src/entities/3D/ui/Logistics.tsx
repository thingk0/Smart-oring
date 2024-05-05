import { Instances, useGLTF } from '@react-three/drei';
import { Point2D } from '../../../shared/types';
import Logistic from './Logistic';
type MapData = {
  charger: PositionData[];
  destination: PositionData[];
  storage: PositionData[];
};
type PositionData = {
  start: Point2D;
  end: Point2D;
  direction: number;
};
function Logistics({ data }: { data: MapData }) {
  const { nodes, materials } = useGLTF('./models/BigShelves04Group3.glb');
  // console.log(nodes);
  // console.log(materials);
  return (
    <>
      <Instances
        castShadow
        receiveShadow
        material={materials.BigShelvesMetall03}
        geometry={nodes.BigShelves04_1.geometry}
      >
        {data.storage.map((l, index) => {
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
        receiveShadow
        material={materials.BigShelvesMetall01}
        geometry={nodes.BigShelves04_2.geometry}
      >
        {data.storage.map((l, index) => {
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
        receiveShadow
        material={materials.BigShelvesWood}
        geometry={nodes.BigShelves04_3.geometry}
      >
        {data.storage.map((l, index) => {
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
        receiveShadow
        material={materials.BigShelvesMetall02}
        geometry={nodes.BigShelves04_4.geometry}
      >
        {data.storage.map((l, index) => {
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
        receiveShadow
        material={materials.BigShelvesWoodBox}
        geometry={nodes.BigShelvesWoodBox.geometry}
      >
        {data.storage.map((l, index) => {
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
        receiveShadow
        material={materials.Cardboard_box05}
        geometry={nodes.Cardboard_box01.geometry}
      >
        {data.storage.map((l, index) => {
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
        receiveShadow
        material={materials.Cardboard_box06}
        geometry={nodes.Cardboard_box05.geometry}
      >
        {data.storage.map((l, index) => {
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
