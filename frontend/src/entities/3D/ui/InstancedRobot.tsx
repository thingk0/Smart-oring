import { useQuery } from '@tanstack/react-query';
import { Merged, useGLTF } from '@react-three/drei';
import { useMemo, useState, useRef, useEffect } from 'react';
import { gsap } from 'gsap';

import { BackendRobotPosition } from '@shared/api';
import { getRotationIndex } from '@shared/lib';
import { TRobot, robotData } from '@shared/types';
import RobotModel from './RobotModel';
import { Group, Object3DEventMap } from 'three';

// main component
function InstancedRobot() {
  const { data, isPending } = useQuery({
    queryKey: ['robotPosition'],
    // queryFn: getRobotPosition, : mocking api
    queryFn: BackendRobotPosition,
    refetchInterval: 800,
  });

  const [beforePositions, setBeforePositions] = useState([]);
  const AGVs = useRef<Group<Object3DEventMap>>(null!);

  useEffect(() => {
    if (data && data[0] !== null) {
      // console.log(data[0].ycoordinate + ', ' + data[0].xcoordinate);

      // calculate direction
      beforePositions?.forEach((before: robotData, index: number) => {
        if (before && data[index]) {
          const [y, x, radian] = getRotationIndex(before, data[index]);

          // move AGVs position
          gsap.to(AGVs.current?.children[index].position, {
            duration: 1,
            ease: 'none',
            x: data[index].ycoordinate + x,
            z: data[index].xcoordinate + y,
            onComplete: () => {
              // rotate AGVs
              AGVs.current.children[index].rotation.y = radian;
              console.log(data[0].ycoordinate + ', ' + data[0].xcoordinate);
            },
          });
        } else {
          const date = new Date();
          console.log('로봇 null 들어옴 ', date);
        }
      });

      // update state
      setBeforePositions(data);
    }
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
        data?.map((status: any, index: number) => {
          return (
            <Merged meshes={instances} key={index}>
              {(instances: TRobot) => (
                <RobotModel
                  instances={instances}
                  name={'robot' + index}
                  battery={status.battery}
                />
              )}
            </Merged>
          );
        })}
    </group>
  );
}

export default InstancedRobot;

useGLTF.preload('./models/AGV.glb');
