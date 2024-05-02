import { useQuery } from '@tanstack/react-query';
import { useGLTF } from '@react-three/drei';
import { useMemo } from 'react';
import { getRobotPosition } from '../../../shared/api';
import RobotModel from './RobotModel';

// main component
function InstancedRobot() {
  const { data, isPending } = useQuery({
    queryKey: ['robotPosition'],
    queryFn: getRobotPosition,
    // refetchInterval: 1000,
  });

  const { scene } = useGLTF('./models/AGV.glb');
  const model = useMemo(() => {
    return scene;
  }, [scene]);

  return (
    <>
      <instancedMesh count={data?.length}>
        {!isPending &&
          data?.map((robot: any, index: number) => {
            return (
              <RobotModel key={index} position={robot.position} scene={model} />
            );
          })}
      </instancedMesh>
    </>
  );
}

export default InstancedRobot;
