import { useQuery } from '@tanstack/react-query';
import { Merged, useGLTF } from '@react-three/drei';
import { useMemo, useState, useRef, useEffect } from 'react';
import { gsap } from 'gsap';

import { getRobotPosition } from '@shared/api';
import { getRotationIndex } from '@shared/lib';
import { TRobot, robotData } from '@shared/types';
import RobotModel from './RobotModel';

// main component
function InstancedRobot() {
  const { data, isPending } = useQuery({
    queryKey: ['robotPosition'],
    queryFn: getRobotPosition,
    refetchInterval: 1000,
  });

  const [beforePositions, setBeforePositions] = useState([]);
  const AGVs = useRef(null);

  useEffect(() => {
    // calculate direction
    beforePositions?.forEach((before: robotData, index: number) => {
      const [y, x, radian] = getRotationIndex(before, data[index]);

      // move AGVs position
      gsap.to(AGVs.current?.children[index].position, {
        duration: 1,
        ease: 'none',
        x: data[index].position[1] + x,
        z: data[index].position[0] + y,
        onComplete: () => {
          // rotate AGVs
          AGVs.current.children[index].rotation.y = radian;
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

  return (
    <group ref={AGVs}>
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
