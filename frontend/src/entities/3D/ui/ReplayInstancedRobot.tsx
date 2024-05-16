import { Merged, useGLTF } from '@react-three/drei';
import { useMemo, useState, useRef, useEffect } from 'react';
import { gsap } from 'gsap';
import { getRotationIndex } from '@shared/lib';
import { TRobot, robotData } from '@shared/types';
import RobotModel from './RobotModel';
import { Group, Object3DEventMap } from 'three';
import { useReplayStore } from '@shared/store';

interface ReplayInstancedRobotProps {
  replayData: {
    time: number[];
    amrHistoryDtoList: robotData[];
  }[];
}

// main component
function ReplayInstancedRobot({ replayData }: ReplayInstancedRobotProps) {
  const { currentTime } = useReplayStore();
  const [beforePositions, setBeforePositions] = useState<robotData[]>([]);
  const AGVs = useRef<Group<Object3DEventMap>>(null!);
  const { speed } = useReplayStore();

  useEffect(() => {
    const data = replayData[currentTime].amrHistoryDtoList;
    // calculate direction
    beforePositions?.forEach((before: robotData, index: number) => {
      if (before && data[index]) {
        const [y, x, radian] = getRotationIndex(before, data[index]);
        // 판별 로직 추가
        let offsetX = 1;
        let offsetZ = 0.5;
        // const normalizedAngle = (radian + 2 * Math.PI) % (2 * Math.PI);
        // if (
        //   Math.abs(normalizedAngle - Math.PI / 2) < 0.01 ||
        //   Math.abs(normalizedAngle - (3 * Math.PI) / 2) < 0.01
        // ) {
        //   [offsetX, offsetZ] = [offsetZ, offsetX];
        // }
        const nx = data[index].ycoordinate + x + offsetX;
        const nz = data[index].xcoordinate + y + offsetZ;
        // move AGVs position
        gsap.to(AGVs.current?.children[index].position, {
          duration: 1 / speed,
          ease: 'none',
          x: nx,
          z: nz,
          onComplete: () => {
            // rotate AGVs
            // AGVs.current.children[index].rotation.y = radian;
            console.log(data[0].ycoordinate + ', ' + data[0].xcoordinate);
          },
        });
        // gsap.to(AGVs.current?.children[index].rotation, {
        //   duration: 1 / speed,
        //   ease: 'none',
        //   y: radian,
        // });
      } else {
        const date = new Date();
        console.log('로봇 null 들어옴 ', date);
      }
    });

    // update state
    setBeforePositions(data);
  }, [currentTime]);

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
      {replayData[currentTime].amrHistoryDtoList &&
        replayData[currentTime].amrHistoryDtoList?.map(
          (status: any, index: number) => {
            return (
              <Merged meshes={instances} key={index}>
                {(instances: TRobot) => (
                  <RobotModel
                    instances={instances}
                    name={'robot' + index}
                    status={status}
                  />
                )}
              </Merged>
            );
          }
        )}
    </group>
  );
}

export default ReplayInstancedRobot;

useGLTF.preload('./models/AGV.glb');
