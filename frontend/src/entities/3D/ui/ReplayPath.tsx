import { Line } from '@react-three/drei';
import { getReplay } from '@shared/api';
import { convert2DTo3D } from '@shared/lib';
import { useReplayStore } from '@shared/store';
import { usePathStore } from '@shared/store/usePathStore';
import { useViewStore } from '@shared/store/useViewStore';
import { useQuery } from '@tanstack/react-query';
import { useEffect } from 'react';

function ReplayPath() {
  const { isShow, index } = usePathStore();
  const { currentTime } = useReplayStore();
  const { missionId } = useViewStore();
  const { data } = useQuery({
    queryKey: ['replay'],
    queryFn: () => getReplay(missionId),
    staleTime: Infinity,
  });
  if (data)
    return (
      <>
        {isShow &&
          data[currentTime]?.amrHistoryDtoList[index]?.routeRemainingForMission
            ?.length > 0 && (
            <Line
              points={convert2DTo3D(
                data[currentTime].amrHistoryDtoList[index]
                  .routeRemainingForMission
              )}
              lineWidth={10}
              color="#FEE59A"
            />
          )}
        {isShow &&
          data[currentTime]?.amrHistoryDtoList[index]?.routeVisitedForMission
            ?.length > 0 && (
            <Line
              points={convert2DTo3D(
                data[currentTime].amrHistoryDtoList[index]
                  .routeVisitedForMission
              )}
              lineWidth={10}
              color="skyblue"
            />
          )}
      </>
    );
}
export default ReplayPath;
