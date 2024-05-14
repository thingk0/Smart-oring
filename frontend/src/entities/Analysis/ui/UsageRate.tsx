import styles from './Analysis.module.css';
import { Typography } from '@mui/material';

type UsageRateProps = {
  data: [{ amrId: number; UsagePercent: number }];
};

function UsageRate({ data }: UsageRateProps) {
  return (
    <div className={styles.component_background}>
      <Typography variant="h5" component="h2">
        AMR 사용률 Top 3
      </Typography>
      <ol>
        {data.map((amr: any) => {
          return <li key={amr.amrId}>{amr.UsagePercent}</li>;
        })}
      </ol>
    </div>
  );
}

export default UsageRate;
