import { TRobot } from './InstancedRobot';

// props
type RobotModelProps = {
  instances: TRobot;
};

// main function
function RobotModel({ instances, ...props }: RobotModelProps) {
  // console.log(instances);

  return (
    <group {...props}>
      <group>
        <instances.geo_aluminium_3 />
        <instances.geo_black_7 />
        <instances.geo_black_matte_1 />
        <instances.geo_black_smoke_glass_8 />
        <instances.geo_light_cyan_1 />
        <instances.geo_orange_1 />
        <instances.geo_rubber_6 />
      </group>
    </group>
  );
}

export default RobotModel;
