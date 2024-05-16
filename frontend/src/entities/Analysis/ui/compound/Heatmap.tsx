import { useEffect, useState } from 'react';
import ReactApexChart from 'react-apexcharts';
import { Typography } from '@mui/material';

import styles from '../Analysis.module.css';
import HeatmapFilter from './HeatmapFilter';

function Heatmap() {
  const [coordinate, setCoordinate] = useState();

  useEffect(() => {
    console.log(coordinate);
  }, [coordinate]);

  const option = {
    colors: ['#673ab7'],
    dataLabels: { enabled: true },
  };

  // useEffect(() => {
  //   axios.get(import.meta.env.VITE_HEATMAP_URL).then(res => console.log(res));
  // }, []);

  return (
    <>
      <HeatmapFilter setState={setCoordinate} />
      <div className={`${styles.component_background} ${styles.margin_top}`}>
        <Typography variant="h3" component="h2">
          병목 좌표
        </Typography>
        <ReactApexChart
          type="heatmap"
          series={coordinate}
          options={option}
          height={500}
        />
      </div>
    </>
  );
}

export default Heatmap;
