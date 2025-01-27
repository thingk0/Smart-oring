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
    <div className={`${styles.component_background} ${styles.scroll}`}>
      <Typography variant="h2" component="h2">
        실시간 에러
      </Typography>
      <ul>
        {data.length > 0 ? (
          data.map(({ amrId, missionId }) => {
            return (
              <Typography variant="body1" component="li" key={amrId}>
                MISSION {missionId} - AMR {amrId}
              </Typography>
            );
          })
        ) : (
          <span>에러가 없습니다.</span>
        )}
      </ul>
    </div>
  );
}

export default RealTimeError;
