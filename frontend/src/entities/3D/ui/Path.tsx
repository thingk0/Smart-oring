import { Line } from '@react-three/drei';
import { convert2DTo3D } from '@shared/lib';
import { usePathStore } from '@shared/store/usePathStore';
import { useEffect } from 'react';

function Path() {
  const { isShow, route } = usePathStore();
  useEffect(() => {
    console.log(route);
  }, []);

  useEffect(() => {}, []);
  return (
    <>
      {isShow && (
        <Line points={convert2DTo3D(route)} lineWidth={10} color="black" />
      )}
    </>
  );
}
export default Path;
