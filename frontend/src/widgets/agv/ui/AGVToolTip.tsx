import { Html } from '@react-three/drei';
import classes from './AGVToolTip.module.css';
import { AmrStatus } from '@shared/types';
import SignalWifiStatusbarConnectedNoInternet4RoundedIcon from '@mui/icons-material/SignalWifiStatusbarConnectedNoInternet4Rounded';
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
    currentStopDuration,
    amrHistoryCreatedAt,
    ycoordinate,
    xcoordinate,
  } = status || {};
  return (
    <>
      <Html position={[2, 2, 0]}>
        <div className={`${classes.content} ${hovered && classes.hovered}`}>
          {status ? (
            <>
              배터리 : {battery}%<br />
              기기번호 : {amrId}
              <br />
              미션번호 : {missionId}
              <br />
              상태 : {amrStatus}
              <br />
              정차한지 {currentStopDuration}초 째
              <br />
              데이터 생성 시간 : {amrHistoryCreatedAt}
              <br />
              좌표 : [{ycoordinate} , {xcoordinate}]
            </>
          ) : (
            <SignalWifiStatusbarConnectedNoInternet4RoundedIcon fontSize="large" />
          )}
        </div>
      </Html>
    </>
  );
}
