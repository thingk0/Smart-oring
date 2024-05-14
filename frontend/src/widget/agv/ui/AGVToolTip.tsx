import { Html } from '@react-three/drei';
import classes from './AGVToolTip.module.css';
import { AmrStatus } from '@shared/types';
interface AGVToolTipProps {
  hovered: boolean;
  status: AmrStatus;
}
export function AGVToolTip({ hovered, status }: AGVToolTipProps) {
  const {
    battery,
    amrId,
    missionId,
    amrStatus,
    stopPeriod,
    amrHistoryCreatedAt,
    ycoordinate,
    xcoordinate,
  } = status || {};
  return (
    <>
      <Html position={[2, 2, 0]}>
        <div className={`${classes.content} ${hovered && classes.hovered}`}>
          배터리 : {battery}%<br />
          기기번호 : {amrId}
          <br />
          미션번호 : {missionId}
          <br />
          상태 : {amrStatus}
          <br />
          정체 시간(초) : {stopPeriod}
          <br />
          데이터 생성 시간 : {amrHistoryCreatedAt}
          <br />
          좌표 : [{ycoordinate} , {xcoordinate}]
        </div>
      </Html>
    </>
  );
}
