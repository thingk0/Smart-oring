import styles from './Analysis.module.css';
import { Typography } from '@mui/material';

function CurrentTime() {
  const date = new Date();

  return (
    <div className={styles.component_background}>
      <Typography variant="h5" component="h2">
        현재 시간
      </Typography>

      <p>{`${date.getFullYear()}-${date.getMonth() + 1}-${date.getDate()}`}</p>
      <p>{`${date.getHours()}:${date.getMinutes()}`}</p>
    </div>
  );
}

export default CurrentTime;
