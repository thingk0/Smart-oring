import ReactApexCharts from 'react-apexcharts';
import styles from './Analysis.module.css';
import { Typography } from '@mui/material';

type CapacityLineGraphProps = {
  yesterday: Array<number>;
  today: Array<number>;
  total: number;
};

const options = {
  xaxis: {
    categories: [
      '00:00',
      '00:30',
      '01:00',
      '01:30',
      '02:00',
      '02:30',
      '03:00',
      '03:30',
      '04:00',
      '04:30',
      '05:00',
      '05:30',
      '06:00',
      '06:30',
      '07:00',
      '07:30',
      '08:00',
      '08:30',
      '09:00',
      '09:30',
      '10:00',
      '10:30',
      '11:00',
      '11:30',
      '12:00',
      '12:30',
      '13:00',
      '13:30',
      '14:00',
      '14:30',
      '15:00',
      '15:30',
      '16:00',
      '16:30',
      '17:00',
      '17:30',
      '18:00',
      '18:30',
      '19:00',
      '19:30',
      '20:00',
      '20:30',
      '21:00',
      '21:30',
      '22:00',
      '22:30',
      '23:00',
      '23:30',
    ],
    tickAmount: 24,
    stepSize: 2,
  },

  yaxis: {
    stepSize: 100,
  },
};

function CapacityLineGraph({
  yesterday,
  today,
  total,
}: CapacityLineGraphProps) {
  // const series: any[] = [
  //   {
  //     name: 'Yesterday',
  //     data: yesterday,
  //   },
  //   {
  //     name: 'Today',
  //     data: today,
  //   },
  // ];

  const series: any[] = [
    {
      name: 'Yesterday',
      data: [
        1, 2, 2, 3, 3, 4, 4, 4, 4, 10, 12, 13, 19, 19, 27, 28, 28, 29, 30, 32,
        36, 42, 42, 44, 46, 52, 52, 53, 55, 55, 57, 62, 62, 67, 72, 73, 74, 75,
        75, 75, 75, 76, 78, 80, 80, 86, 95, 97,
      ],
    },
    {
      name: 'Today',
      data: [
        0, 1, 2, 4, 4, 10, 20, 20, 25, 25, 27, 27, 27, 27, 29, 32, 42, 42, 43,
        47, 47, 49, 49, 50, 51, 54, 56, 57, 66, 66, 67, 67, 69, 72, 75, 77, 78,
        78, 79, 81, 81, 85, 90, 91, 93, 94, 98, 100,
      ],
    },
  ];

  return (
    <div className={`${styles.component_background} ${styles.line}`}>
      <Typography component="h2" variant="h2">
        생산량 비교
      </Typography>
      <Typography component="span" variant="body1">
        {/* 오늘 생산량 : {total} | 어제 생산량 :{' '}
        {yesterday.reduce((pre, cur) => pre + cur)} */}
        오늘 생산량 : 100 | 어제 생산량 : 97
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
