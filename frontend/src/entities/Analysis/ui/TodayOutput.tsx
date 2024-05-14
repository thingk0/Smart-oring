import styles from './Analysis.module.css';
import { Typography } from '@mui/material';

type TodayOutputProps = {
  data: number;
};

function TodayOutput({ data }: TodayOutputProps) {
  return (
    <div className={styles.component_background}>
      <Typography variant="h5" component="h2">
        오늘 생산량
      </Typography>
      <p>{data}</p>
    </div>
  );
}

export default TodayOutput;
