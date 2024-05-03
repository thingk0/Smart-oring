import { Instances, useGLTF } from '@react-three/drei';
import { Point2D } from '../../../shared/types';
import Destination from './Destination';
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
function Destinations({ data }: { data: MapData }) {
  const { nodes, materials } = useGLTF('./models/098-belt.glb');
  console.log(nodes);
  console.log(materials);
  return (
    <>
      <Instances
        castShadow
        geometry={nodes.Plane001.geometry}
        material={materials['Material.001']}
      >
        {data.destination.map((l, index) => {
          return (
            <Destination
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
        geometry={nodes.Plane002.geometry}
        material={materials['Material.002']}
      >
        {data.destination.map((l, index) => {
          return (
            <Destination
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
        geometry={nodes.Vert.geometry}
        material={materials['Material.002']}
      >
        {data.destination.map((l, index) => {
          return (
            <Destination
              start={l.start}
              end={l.end}
              direction={l.direction}
              key={index}
            />
          );
        })}
      </Instances>
      {/* <Instances
        castShadow
        geometry={nodes.Circle.geometry}
        material={materials['Material.002']}
      >
        {data.destination.map((l, index) => {
          return (
            <Destination
              start={l.start}
              end={l.end}
              direction={l.direction}
              key={index}
            />
          );
        })}
      </Instances> */}
    </>
  );
}
export default Destinations;
