import ReactApexChart from 'react-apexcharts';
import styles from './Analysis.module.css';

import { Typography } from '@mui/material';

const option: any = {
  labels: ['가동률'],
  // dataLabels: {
  //   enabled: false,
  // },
  plotOptions: {
    radialBar: {
      startAngle: -150,
      endAngle: 150,
      dataLabels: {
        show: true,
        name: {
          show: false,
        },
        value: {
          fontSize: '16px',
          offsetY: 0,
        },
      },
    },
  },
};

type OperatingRateProps = {
  data: number;
};

function OperatingRate({ data }: OperatingRateProps) {
  return (
    <div className={`${styles.component_background} ${styles.operating}`}>
      <Typography variant="h2" component="h2">
        가동률
      </Typography>
      <ReactApexChart
        type="radialBar"
        series={[data]}
        series={[90]}
        options={option}
        // width={300}
      />
    </div>
  );
}
export default OperatingRate;
