import { useQuery } from '@tanstack/react-query';
import { Merged, useGLTF } from '@react-three/drei';
import { useMemo, useState, useRef, useEffect } from 'react';
import { getRobotPosition } from '../../../shared/api';
import RobotModel from './RobotModel';
import { Object3D } from 'three';
import { robotData } from '../../../shared/types';
import { gsap } from 'gsap';

// type
export type TRobot = {
  geo_aluminium_3: Object3D;
  geo_black_7: Object3D;
  geo_black_matte_1: Object3D;
  geo_black_smoke_glass_8: Object3D;
  geo_light_cyan_1: Object3D;
  geo_orange_1: Object3D;
  geo_rubber_6: Object3D;
};

// utils

const arr = [0, Math.PI / 2, 0, Math.PI * 1.5, Math.PI];

const getRotationIndex = (before: robotData, current: robotData) => {
  const y = before.position[0] - current.position[0];
  const x = before.position[1] - current.position[1];
  const rotationIndex = 2 * y + x + 2;

  return [y, x, rotationIndex];
};

// main component
function InstancedRobot() {
  const { data, isPending } = useQuery({
    queryKey: ['robotPosition'],
    queryFn: getRobotPosition,
    refetchInterval: 1000,
  });

  const [beforePositions, setBeforePositions] = useState([]);
  const forklifts = useRef(null);

  useEffect(() => {
    // calculate direction
    beforePositions?.forEach((before: robotData, index: number) => {
      const [y, x, rotationIndex] = getRotationIndex(before, data[index]);
      const direction = arr[rotationIndex];

      // move forklifts position
      gsap.to(forklifts.current?.children[index].position, {
        duration: 1,
        ease: 'none',
        x: data[index].position[1] + x,
        z: data[index].position[0] + y,
        onComplete: () => {
          // rotate forklifts
          forklifts.current.children[index].rotation.y = direction;
        },
      });
    });

    // update state
    setBeforePositions(data);
  }, [data]);

  const { nodes } = useGLTF('./models/AGV.glb');
  const instances: TRobot = useMemo(
    () => ({
      geo_aluminium_3: nodes['geo_aluminium_3'],
      geo_black_7: nodes['geo_black_7'],
      geo_black_matte_1: nodes['geo_black_matte_1'],
      geo_black_smoke_glass_8: nodes['geo_black_smoke_glass_8'],
      geo_light_cyan_1: nodes['geo_light_cyan_1'],
      geo_orange_1: nodes['geo_orange_1'],
      geo_rubber_6: nodes['geo_rubber_6'],
    }),
    [nodes]
  );

  // console.log(forklifts.current?.children);

  return (
    <group ref={forklifts}>
      {!isPending &&
        data?.map((_: any, index: number) => {
          return (
            <Merged meshes={instances} key={index}>
              {(instances: TRobot) => <RobotModel instances={instances} />}
            </Merged>
          );
        })}
    </group>
  );
}

export default InstancedRobot;

useGLTF.preload('./models/AGV.glb');
