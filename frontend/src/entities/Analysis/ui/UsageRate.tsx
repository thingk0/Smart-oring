import styles from './Analysis.module.css';
import { Typography } from '@mui/material';

type UsageRateProps = {
  data: [{ amrId: number; UsagePercent: number }];
};

function UsageRate({ data }: UsageRateProps) {
  return (
    <div className={styles.component_background}>
      <Typography variant="h2" component="h2">
        미션 수행률 Top 3
      </Typography>
      <ol>
        {data.map((amr: any) => {
          return (
            <Typography variant="body1" component="li" key={amr.amrId}>
              AMR {amr.UsagePercent}
            </Typography>
          );
        })}
      </ol>
    </div>
  );
}

export default UsageRate;
