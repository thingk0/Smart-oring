import { useFrame } from '@react-three/fiber';
import useGraphicsQualityStore from '@shared/store/useGraphicsQualityStore';
import { TRobot } from '@shared/types';
import { useCallback, useEffect, useState } from 'react';
import * as THREE from 'three';
import { AGVToolTip } from 'widget/agv/ui/index';
// props
type RobotModelProps = {
  instances: TRobot;
  name: string;
  battery: number;
  amrId: number;
};

// main function
function RobotModel({ instances, name, battery, ...props }: RobotModelProps) {
  // console.log(instances);
  console.log('battery', battery);
  const [isFPV, setIsFPV] = useState(false);
  console.log(props);
  useFrame(state => {
    if (!isFPV) return;
    const target = new THREE.Vector3();
    const robot = state.scene.getObjectByName(name);
    // getWorldPosition으로 target에 robot의 위치를 저장한다. (헷갈림 주의)
    robot?.children[0].getWorldPosition(target);
    target.y = 2;
    state.camera.position.copy(target);
  });
  const [hovered, setHover] = useState(false);
  useEffect(
    () => void (document.body.style.cursor = hovered ? 'pointer' : 'auto'),
    [hovered]
  );
  const onPointerOver = useCallback(() => setHover(true), []);
  const onPointerOut = useCallback(() => setHover(false), []);
  const { lightQuality } = useGraphicsQualityStore();
  return (
    <group {...props}>
      <group
        name={name}
        onClick={() => setIsFPV(true)}
        onPointerMissed={() => setIsFPV(false)}
        onPointerOver={onPointerOver}
        onPointerOut={onPointerOut}
      >
        {lightQuality === 'high' && (
          <pointLight color="#00afff" intensity={10} />
        )}
        <AGVToolTip battery={battery} amrId={props.amrId} hovered={hovered} />
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
