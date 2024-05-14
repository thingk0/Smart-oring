import ReactApexChart from 'react-apexcharts';
import styles from './Analysis.module.css';

import { Typography } from '@mui/material';

const option: any = {
  labels: ['가동률'],
  dataLabels: {
    enabled: false,
  },
  plotOptions: {
    radialBar: {
      startAngle: -150,
      endAngle: 150,
    },
  },
};

type OperatingRateProps = {
  data: number;
};

function OperatingRate({ data }: OperatingRateProps) {
  return (
    <div className={styles.component_background}>
      <Typography variant="h5" component="h2">
        가동률
      </Typography>
      <ReactApexChart type="radialBar" series={[data]} options={option} />
    </div>
  );
}
export default OperatingRate;
