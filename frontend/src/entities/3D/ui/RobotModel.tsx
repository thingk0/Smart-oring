import { ThreeEvent, useFrame } from '@react-three/fiber';
import useGraphicsQualityStore from '@shared/store/useGraphicsQualityStore';
import { usePathStore } from '@shared/store/usePathStore';
import { AmrStatus, TRobot } from '@shared/types';
import { useEffect, useState } from 'react';
import * as THREE from 'three';
import { AGVToolTip } from 'widgets/agv/ui/index';
// props
type RobotModelProps = {
  instances: TRobot;
  name: string;
  status: AmrStatus;
};

// main function
function RobotModel({ instances, name, status, ...props }: RobotModelProps) {
  // console.log(instances);
  const {
    actions: { setIsShow, setRoute },
  } = usePathStore();
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
  const onPointerOver = () => {
    setIsShow(true);
    setRoute(status.routeVisitedForMission, status.routeRemainingForMission);
    setHover(true);
  };
  const onPointerOut = () => {
    setHover(false);
    setIsShow(false);
  };
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
        <AGVToolTip status={status} hovered={hovered} />
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
