import styles from './Analysis.module.css';
import { Typography } from '@mui/material';

type ErrorRateProps = {
  data: Array<ErrorRate>;
};

type ErrorRate = {
  amrId: number;
  percentage: number;
};
// { data }: ErrorRateProps

function ErrorRate() {
  const data = [
    {
      amrId: 7,
      percentage: 12.4,
    },
    {
      amrId: 11,
      percentage: 9.7,
    },
    {
      amrId: 2,
      percentage: 8.8,
    },
  ];

  return (
    <div className={styles.component_background}>
      <Typography variant="h2" component="h2">
        미션 중단률 Top 3
      </Typography>
      <ol>
        {data.length > 0 ? (
          data
            .filter((amr: ErrorRate, index: number) => {
              if (index < 3) return amr;
            })
            .map(({ amrId, percentage }: ErrorRate) => {
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

export default ErrorRate;
