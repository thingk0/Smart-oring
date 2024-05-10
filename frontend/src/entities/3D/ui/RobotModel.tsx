import { ThreeEvent, useFrame } from '@react-three/fiber';
import { TRobot } from '../../../shared/types';
import { useCallback, useEffect, useState } from 'react';
import * as THREE from 'three';
// props
type RobotModelProps = {
  instances: TRobot;
  name: string;
};

// main function
function RobotModel({ instances, name, ...props }: RobotModelProps) {
  // console.log(instances);

  const [isFPV, setIsFPV] = useState(false);
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
  return (
    <group {...props}>
      <group
        name={name}
        onClick={() => setIsFPV(true)}
        onPointerMissed={() => setIsFPV(false)}
        onPointerOver={onPointerOver}
        onPointerOut={onPointerOut}
      >
        <pointLight color="#00afff" intensity={10} />
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
