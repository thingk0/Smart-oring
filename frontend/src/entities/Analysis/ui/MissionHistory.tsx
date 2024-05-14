import { Button, Typography } from '@mui/material';
import useMissionStore from '../store/useMissionStore';
import styles from './Analysis.module.css';
import ReplayIcon from '@mui/icons-material/Replay';
import ReactApexChart from 'react-apexcharts';

function MissionHistory() {
  const history = useMissionStore(state => state.missionHistory);
  // console.log(history);

  // console.log(history?.processing_time);
  // console.log(history?.bottleneck_time);
  // console.log(history?.error_time);

  const series = [
    history?.processing_time,
    history?.bottleneck_time,
    history?.error_time,
  ];
  const options = {
    chart: {
      type: 'donut',
    },
    responsive: [
      {
        breakpoint: 480,
        options: {
          chart: {
            width: 200,
          },
          legend: {
            position: 'bottom',
          },
        },
      },
    ],
  };

  const timelineSeries = [
    {
      data: [
        {
          x: 'Code',
          y: [
            new Date('2019-03-02').getTime(),
            new Date('2019-03-04').getTime(),
          ],
        },
        {
          x: 'Test',
          y: [
            new Date('2019-03-04').getTime(),
            new Date('2019-03-08').getTime(),
          ],
        },
        {
          x: 'Validation',
          y: [
            new Date('2019-03-08').getTime(),
            new Date('2019-03-12').getTime(),
          ],
        },
        {
          x: 'Deployment',
          y: [
            new Date('2019-03-12').getTime(),
            new Date('2019-03-18').getTime(),
          ],
        },
      ],
    },
  ];
  const timelineOptions = {
    chart: {
      height: 350,
      type: 'rangeBar',
    },
    plotOptions: {
      bar: {
        horizontal: true,
      },
    },
    xaxis: {
      type: 'datetime',
    },
  };

  return (
    history && (
      <div
        className={`${styles.component_background} ${styles.mission_grid_column}`}
      >
        <div className={`${styles.flex_center} ${styles.margin_bottom}`}>
          <div>
            <Typography component="h2" variant="h5">
              대충 AMR 번호랑 미션 번호
            </Typography>
            <Typography component="p" variant="body2">
              대충 시작 시간과 종료 시간
            </Typography>
          </div>

          <Button variant="contained" endIcon={<ReplayIcon />}>
            Replay
          </Button>
        </div>
        <div className={styles.flex}>
          <div>
            <Typography component="p" variant="h5">
              main graph
            </Typography>
            <ReactApexChart type="donut" series={series} options={options} />
          </div>
          <div>
            <Typography component="p" variant="h5">
              line graph
            </Typography>
            <ReactApexChart
              type="rangeBar"
              series={timelineSeries}
              options={timelineOptions}
            />
          </div>
        </div>
      </div>
    )
  );
}

export default MissionHistory;
