import { useEffect } from 'react';
import ReactApexChart from 'react-apexcharts';
import { Typography } from '@mui/material';
import axios from 'axios';

import styles from './Analysis.module.css';

const option = {
  colors: ['#673ab7'],
  dataLabels: { enabled: true },
};

function HeatmapGraph() {
  return (
    <div className={`${styles.component_background} ${styles.margin_top}`}>
      <Typography variant="h3" component="h2">
        병목 좌표
      </Typography>
      {/* <ReactApexChart
  type="heatmap"
  series={data}
  options={option}
  height={500}
/> */}
    </div>
  );
}

export default HeatmapGraph;
