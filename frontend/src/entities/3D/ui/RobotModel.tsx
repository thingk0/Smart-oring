import { useEffect, useRef, useState } from 'react';
import gsap from 'gsap';
import { Point2D } from '../../../shared/types';

// utils
const arr = [0, Math.PI / 2, 0, Math.PI * 1.5, Math.PI];

const getRotationIndex = (before: Point2D, current: Point2D) => {
  if (before && current) {
    const y = before[0] - current[0];
    const x = before[1] - current[1];
    const rotationIndex = 2 * y + x + 2;

    return [y, x, rotationIndex];
  }

  return [];
};

// props
type RobotModelProps = {
  position: Point2D;
  scene: any;
};

// main function
function RobotModel({ position, scene }: RobotModelProps) {
  console.log(position);
  //   console.log(scene);

  const robot = useRef(null);
  const [beforePosition, setBeforePosition] = useState<Point2D>();

  useEffect(() => {
    // calculate direction
    const [y, x, rotationIndex] = getRotationIndex(beforePosition, position);
    const direction = arr[rotationIndex];

    // move forklifts position
    gsap.to(robot?.current.position, {
      duration: 1,
      ease: 'none',
      x: position[1] + x,
      z: position[0] + y,
      onComplete: () => {
        // rotate forklifts
        robot.current.rotation.y = direction;
      },
    });

    // update state
    setBeforePosition(position);
  }, [position]);

  return (
    <group ref={robot}>
      <primitive object={scene} />
    </group>
  );
}

export default RobotModel;
