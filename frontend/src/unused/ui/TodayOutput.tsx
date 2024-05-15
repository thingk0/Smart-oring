import styles from './Analysis.module.css';
import { Typography } from '@mui/material';

type TodayOutputProps = {
  data: number;
};

const getDateTime = () => {
  const date = new Date();
  return date.toLocaleDateString();
};

function TodayOutput({ data }: TodayOutputProps) {
  return (
    <div className={styles.component_background}>
      <Typography variant="h2" component="h2">
        {getDateTime()} 생산량
      </Typography>
      <Typography variant="body1" component="p">
        {data}
      </Typography>
    </div>
  );
}

export default TodayOutput;
