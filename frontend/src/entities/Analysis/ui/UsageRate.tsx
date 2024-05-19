import styles from './Analysis.module.css';
import { Typography } from '@mui/material';

type UsageRateProps = {
  data: Array<UsageRate>;
};

type UsageRate = {
  amrId: number;
  percentage: number;
};

function UsageRate({ data }: UsageRateProps) {
  return (
    <div className={styles.component_background}>
      <Typography variant="h2" component="h2">
        미션 수행률 Worst 3
      </Typography>
      <ol>
        {data.length > 0 ? (
          data.map(({ amrId, percentage }: UsageRate) => {
            return (
              <Typography variant="body1" component="li" key={amrId}>
                AMR {amrId} : {percentage.toFixed(2)}%
              </Typography>
            );
          })
        ) : (
          <span>데이터가 없습니다.</span>
        )}
      </ol>
    </div>
  );
}

export default UsageRate;
