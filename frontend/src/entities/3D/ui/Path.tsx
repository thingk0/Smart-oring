import { Line } from '@react-three/drei';
import { BackendRobotPosition } from '@shared/index';
import { convert2DTo3D } from '@shared/lib';
import { usePathStore } from '@shared/store/usePathStore';
import { useQuery } from '@tanstack/react-query';
import { useEffect } from 'react';

function Path() {
  const { isShow, index, nextRoutes, visitedRoutes } = usePathStore();

  const { data } = useQuery({
    queryKey: ['robotPosition'],
    // queryFn: getRobotPosition, : mocking api
    queryFn: BackendRobotPosition,
  });
  if (data)
    return (
      <>
        {isShow && data[index]?.routeRemainingForMission?.length > 0 && (
          <Line
            points={convert2DTo3D(data[index].routeRemainingForMission)}
            lineWidth={10}
            color="#FEE59A"
          />
        )}
        {isShow && data[index]?.routeVisitedForMission?.length > 0 && (
          <Line
            points={convert2DTo3D(data[index].routeVisitedForMission)}
            lineWidth={10}
            color="skyblue"
          />
        )}
      </>
    );
}
export default Path;
