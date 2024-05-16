import { Button, Typography } from '@mui/material';
import useMissionStore from '../store/useMissionStore';
import styles from './Analysis.module.css';
import ReplayIcon from '@mui/icons-material/Replay';
import ReactApexChart from 'react-apexcharts';

function MissionHistory() {
  const history = useMissionStore(state => state.missionHistory);
  // console.log(history);

  const series = [
    history?.missionExecutionTimeAnalysis.processingTime,
    history?.missionExecutionTimeAnalysis.bottleneckTime,
    history?.missionExecutionTimeAnalysis.errorTime,
  ];
  const options = {
    labels: [
      `Processing ${history?.missionExecutionTimeAnalysis.processingTime}s`,
      `Stopped ${history?.missionExecutionTimeAnalysis.bottleneckTime}s`,
      `Error ${history?.missionExecutionTimeAnalysis.errorTime}s`,
    ],
  };

  const timelineSeries = [{ data: history?.amrStatusTimeline }];
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
            <Typography component="h3" variant="h3">
              {history.missionExecutionTimeAnalysis.amrCode} | Mission{' '}
              {history.missionExecutionTimeAnalysis.missionId}
            </Typography>
            <Typography component="p" variant="body2">
              대충 시작 시간과 종료 시간
            </Typography>
          </div>

          <Button variant="contained" endIcon={<ReplayIcon />}>
            Replay
          </Button>
        </div>
        <div className={styles.flex_notcenter}>
          <div>
            <Typography component="p" variant="h2">
              AMR Usage Rate
            </Typography>
            <ReactApexChart
              type="donut"
              series={series}
              options={options}
              width={350}
            />
          </div>
          <div>
            <Typography component="p" variant="h2">
              Mission Process Timeline
            </Typography>
            <ReactApexChart
              type="rangeBar"
              series={timelineSeries}
              options={timelineOptions}
              width={450}
              height={200}
            />
          </div>
        </div>
      </div>
    )
  );
}

export default MissionHistory;
