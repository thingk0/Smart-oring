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

// { data }: RealTimeBottleneck
function RealTimeBottleneck() {
  const data = [
    {
      amrId: 8,
      xcoordinate: 43,
      ycoordinate: 16,
    },
    {
      amrId: 24,
      xcoordinate: 65,
      ycoordinate: 13,
    },
    {
      amrId: 1,
      xcoordinate: 32,
      ycoordinate: 27,
    },
  ];

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
