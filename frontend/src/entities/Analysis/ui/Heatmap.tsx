import ReactApexChart from 'react-apexcharts';
import styles from './Analysis.module.css';
import { Typography } from '@mui/material';

type HeatmapProps = {
  resource: {
    read(): any;
  };
};

function Heatmap({ resource }: HeatmapProps) {
  const data = resource.read();
  //   console.log(data);

  const option = {
    colors: ['#000000'],
    dataLabels: { enabled: true },
  };

  return (
    <div className={styles.component_background}>
      <Typography variant="h2" component="h2">
        병목 좌표
      </Typography>
      <ReactApexChart
        type="heatmap"
        series={data}
        options={option}
        height={500}
      />
    </div>
  );
}

export default Heatmap;
