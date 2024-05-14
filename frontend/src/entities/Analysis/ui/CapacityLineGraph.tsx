import ReactApexCharts from 'react-apexcharts';
import styles from './Analysis.module.css';
import { Typography } from '@mui/material';

const option = {};

type CapacityLineGraphProps = {
  yesterday: Array<number>;
  today: Array<number>;
};

function CapacityLineGraph({ yesterday, today }: CapacityLineGraphProps) {
  const series: any[] = [
    {
      name: 'Yesterday',
      data: yesterday,
    },
    {
      name: 'Today',
      data: today,
    },
  ];

  return (
    <div className={styles.component_background}>
      <Typography variant="h5" component="h2">
        미션 처리량
      </Typography>
      <ReactApexCharts type="line" series={series} options={option} />
    </div>
  );
}

export default CapacityLineGraph;
