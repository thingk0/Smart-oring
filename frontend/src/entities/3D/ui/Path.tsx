import { Line } from '@react-three/drei';
import { convert2DTo3D } from '@shared/lib';
import { usePathStore } from '@shared/store/usePathStore';
import { useEffect } from 'react';

function Path() {
  const { isShow, nextRoutes, visitedRoutes } = usePathStore();
  useEffect(() => {
    console.log(visitedRoutes);
  }, []);

  useEffect(() => {}, []);
  return (
    <>
      {isShow && nextRoutes?.length > 0 && (
        <Line points={convert2DTo3D(nextRoutes)} lineWidth={10} color="grey" />
      )}
      {isShow && visitedRoutes?.length > 0 && (
        <Line
          points={convert2DTo3D(visitedRoutes)}
          lineWidth={10}
          color="skyblue"
        />
      )}
    </>
  );
}
export default Path;
