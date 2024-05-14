import styles from './Analysis.module.css';
import { Typography } from '@mui/material';

type RealTimeBottleneck = {
  data: [
    {
      amrId: number;
      xcoordinate: number;
      ycoordinate: number;
    },
  ];
};

function RealTimeBottleneck({ data }: RealTimeBottleneck) {
  return (
    <div className={styles.component_background}>
      <Typography variant="h5" component="h2">
        실시간 병목
      </Typography>
      {data.map(({ amrId, xcoordinate, ycoordinate }) => {
        return (
          <p key={amrId}>
            {amrId}번 기기 - ({xcoordinate}, {ycoordinate}) 좌표 병목
          </p>
        );
      })}
    </div>
  );
}

export default RealTimeBottleneck;
