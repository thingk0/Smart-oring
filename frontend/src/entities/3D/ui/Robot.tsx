import { useEffect, useRef, useState } from 'react';
import { useQuery } from '@tanstack/react-query';
import { useGLTF } from '@react-three/drei';
import { Vector3 } from 'three';
import gsap from 'gsap';

import { BackendRobotPosition, getRobotPosition } from '@shared/api';
import { Point2D, robotData } from '@shared/types';

// utils
const convertPosition = (position: Point2D): Vector3 => {
  return new Vector3(position[1], 0, position[0]);
};

const arr = [0, Math.PI / 2, 0, Math.PI * 1.5, Math.PI];

const getRotationIndex = (before: robotData, current: robotData) => {
  const y = before.ycoordinate - current.ycoordinate;
  const x = before.xcoordinate - current.xcoordinate;
  const rotationIndex = 2 * y + x + 2;

  return [y, x, rotationIndex];
};

function Robot() {
  const { data, isPending } = useQuery({
    queryKey: ['robotPosition'],
    queryFn: BackendRobotPosition,
    refetchInterval: 1000,
  });

  const model = useGLTF('./models/AGV.glb');

  const [beforePositions, setBeforePositions] = useState([]);
  const AGVs = useRef([]);

  useEffect(() => {
    // calculate direction
    beforePositions?.forEach((before: robotData, index: number) => {
      const [y, x, rotationIndex] = getRotationIndex(before, data[index]);
      const direction = arr[rotationIndex];

      // move AGVs position
      gsap.to(AGVs.current[index].position, {
        duration: 1,
        ease: 'none',
        x: data[index].xcoordinate + x,
        z: data[index].ycoordinate + y,
        onComplete: () => {
          // rotate AGVs
          AGVs.current[index].rotation.y = direction;
        },
      });
    });

    // update state
    setBeforePositions(data);
  }, [data]);

  return (
    <group>
      {!isPending &&
        data.map((F: robotData, index: number) => {
          // AGVs.current.push();
          return (
            <object3D
              key={F.amrId}
              ref={element => (AGVs.current[index] = element)}
            >
              <pointLight color="#00afff" intensity={10} />
              <primitive object={model.scene.clone()} />
            </object3D>
          );
        })}
    </group>
  );
}

export default Robot;
