import styles from './Analysis.module.css';
import { Typography } from '@mui/material';

type ErrorRateProps = {
  data: [{ amrId: number; ErrorPercent: number }];
};

function ErrorRate({ data }: ErrorRateProps) {
  return (
    <div className={styles.component_background}>
      <Typography variant="h5" component="h2">
        AMR 오류율 Worst 3
      </Typography>
      <ol>
        {data.map((amr: any) => {
          return <li key={amr.amrId}>{amr.ErrorPercent}</li>;
        })}
      </ol>
    </div>
  );
}

export default ErrorRate;
