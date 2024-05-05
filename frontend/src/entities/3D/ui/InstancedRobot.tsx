import { useQuery } from '@tanstack/react-query';
import { Merged, useGLTF } from '@react-three/drei';
import { useMemo } from 'react';
import { getRobotPosition } from '../../../shared/api';
import RobotModel from './RobotModel';
import { Object3D } from 'three';

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

// main component
function InstancedRobot() {
  const { data, isPending } = useQuery({
    queryKey: ['robotPosition'],
    queryFn: getRobotPosition,
    // refetchInterval: 1000,
  });

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
    <>
      {!isPending &&
        data?.map((robot: any, index: number) => {
          return (
            <Merged meshes={instances} key={index}>
              {(instances: TRobot) => <RobotModel instances={instances} />}
            </Merged>
          );
        })}
    </>
  );
}

export default InstancedRobot;

useGLTF.preload('./models/AGV.glb');
