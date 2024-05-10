import { useQuery } from '@tanstack/react-query';
import { Instances, Model } from './AGV';
import { BackendRobotPosition } from '@shared/api';
import { useEffect, useRef, useState } from 'react';
import { Group, Object3DEventMap } from 'three';
import { getRotationIndex } from '@shared/lib';
import { robotData } from '@shared/types';
import { gsap } from 'gsap';

function AGVInstance() {
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
  return (
    <>
      <Instances>
        <group ref={AGVs}>
          {!isPending &&
            data?.map((status: any, index: number) => {
              return <Model name={'robot' + index} />;
            })}
        </group>
      </Instances>
    </>
  );
}
export default AGVInstance;
