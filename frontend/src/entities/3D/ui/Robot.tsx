import { useEffect } from 'react';
import { useQuery } from '@tanstack/react-query';
import { useGLTF } from '@react-three/drei';
import { getRobotPosition } from '../../../shared/api';
import { Point2D, robotData } from '../../../shared/types';
import { Vector3 } from 'three';

const convertPosition = (position: Point2D): Vector3 => {
  return new Vector3(position[0], 0, position[1]);
};

function Robot() {
  let query = useQuery({
    queryKey: ['robotPosition'],
    queryFn: getRobotPosition,
    refetchInterval: 1000,
  });

  const forklift = useGLTF('./models/Forklift01.glb');

  useEffect(() => {
    console.log('query changed', query.data);
  }, [query]);

  return (
    <>
      {query.data?.map((p: robotData) => {
        return (
          <object3D key={p.id} position={convertPosition(p.position)}>
            <primitive object={forklift.scene} />
          </object3D>
        );
      })}
    </>
  );
}

export default Robot;
