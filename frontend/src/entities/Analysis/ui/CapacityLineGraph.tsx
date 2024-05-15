import ReactApexCharts from 'react-apexcharts';
import styles from './Analysis.module.css';
import { Typography } from '@mui/material';

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

  const options = {};

  return (
    <div className={`${styles.component_background} ${styles.line}`}>
      <Typography component="h2" variant="h2">
        미션 처리량
      </Typography>
      <ReactApexCharts
        type="line"
        series={series}
        options={options}
        height={220}
      />
    </div>
  );
}

export default CapacityLineGraph;
