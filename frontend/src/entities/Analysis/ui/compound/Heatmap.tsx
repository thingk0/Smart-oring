import { useEffect, useState } from 'react';
import ReactApexChart from 'react-apexcharts';
import { Typography } from '@mui/material';

import styles from '../Analysis.module.css';
import HeatmapFilter from './HeatmapFilter';

const compressCoordinate = (coordinate: Array<any>) => {
  const result = [];
  let tmp = [];

  for (let y = 3; y < coordinate.length; y += 3) {
    tmp = [];
    for (let x = 3; x < coordinate[y].data.length; x += 3) {
      tmp.push({
        x: x / 3 - 1,
        y: compress3x3(coordinate, y, x),
      });
    }
    result.push({ name: y / 3 - 1, data: tmp });
  }

  return result;
};

const compress3x3 = (coordinate: Array<any>, y: number, x: number) => {
  let sum = 0;

  for (let i = x; i > x - 3; i--) {
    for (let j = y; j > y - 3; j--) {
      sum += coordinate[j].data[i].y;
    }
  }

  return sum;
};

function Heatmap() {
  const [coordinate, setCoordinate] = useState([]);
  const [compressed, setCompressed] = useState([]);

  // if (coordinate.length) console.log(compress3x3(coordinate, 3, 3));

  useEffect(() => {
    if (coordinate.length) {
      setCompressed(compressCoordinate(coordinate));
    }
  }, [coordinate]);

  const option = {
    colors: ['#ff0000'],
    // dataLabels: { enabled: true },
  };

  return (
    <>
      <HeatmapFilter setState={setCoordinate} />
      {compressed.length > 0 && (
        <div className={`${styles.component_background} ${styles.margin_top}`}>
          <Typography variant="h3" component="h2">
            병목 좌표
          </Typography>
          <ReactApexChart
            type="heatmap"
            series={compressed}
            options={option}
            height={500}
          />
        </div>
      )}
    </>
  );
}

export default Heatmap;
