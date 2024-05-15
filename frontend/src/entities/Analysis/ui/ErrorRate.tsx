import styles from './Analysis.module.css';
import { Typography } from '@mui/material';

type ErrorRateProps = {
  data: [{ amrId: number; ErrorPercent: number }];
};

function ErrorRate({ data }: ErrorRateProps) {
  return (
    <div className={styles.component_background}>
      <Typography variant="h2" component="h2">
        미션 중단률 Top 3
      </Typography>
      <ol>
        {data.map((amr: any) => {
          return (
            <Typography variant="body1" component="li" key={amr.amrId}>
              AMR {amr.ErrorPercent}
            </Typography>
          );
        })}
      </ol>
    </div>
  );
}

export default ErrorRate;
