import styles from './Analysis.module.css';
import { Typography } from '@mui/material';

type RealTimeErrorProps = {
  data: [
    {
      amrId: number;
      missionId: number;
    },
  ];
};

function RealTimeError({ data }: RealTimeErrorProps) {
  return (
    <div className={styles.component_background}>
      <Typography variant="h5" component="h2">
        실시간 에러
      </Typography>
      {data.map(({ amrId, missionId }) => {
        return (
          <p key={amrId}>
            {amrId}번 기기 - {missionId} 미션 수행 시 에러
          </p>
        );
      })}
    </div>
  );
}

export default RealTimeError;
