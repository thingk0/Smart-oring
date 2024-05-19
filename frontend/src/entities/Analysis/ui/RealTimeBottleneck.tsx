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
    <div className={`${styles.component_background} ${styles.scroll}`}>
      <Typography variant="h2" component="h2">
        실시간 병목
      </Typography>
      <ul>
        {data.length > 0 ? (
          data.map(({ amrId, xcoordinate, ycoordinate }) => {
            return (
              <Typography variant="body1" component="li" key={amrId}>
                ({xcoordinate}, {ycoordinate}) Coordinate - AMR {amrId}
              </Typography>
            );
          })
        ) : (
          <span>병목이 없습니다.</span>
        )}
      </ul>
    </div>
  );
}

export default RealTimeBottleneck;
