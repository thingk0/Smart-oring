import { Button, Typography } from '@mui/material';
import ReplayIcon from '@mui/icons-material/Replay';
import ReactApexChart from 'react-apexcharts';
import { MissionHistoryType } from '@entity/Analysis/store/useMissionStore';
import styles from '../Analysis.module.css';

interface MissionHistoryProps {
  history: MissionHistoryType;
}

function MissionHistory({ history }: MissionHistoryProps) {
  // console.log(history);

  const series = [
    history.mission_execution_time_analysis?.processing_time,
    history.mission_execution_time_analysis?.bottleneck_time,
    history.mission_execution_time_analysis?.error_time,
  ];
  const options = {
    labels: [
      `Processing ${history.mission_execution_time_analysis?.processing_time}s`,
      `Stopped ${history.mission_execution_time_analysis?.bottleneck_time}s`,
      `Error ${history.mission_execution_time_analysis?.error_time}s`,
    ],
  };

  const timelineSeries = [{ data: history?.amr_status_timeline }];
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
    Object.keys(history).length > 0 && (
      <div
        className={`${styles.component_background} ${styles.mission_grid_column}`}
      >
        <div className={`${styles.flex_center} ${styles.margin_bottom}`}>
          <div>
            <Typography component="h3" variant="h3">
              {history.mission_execution_time_analysis?.amr_code} | Mission{' '}
              {history.mission_execution_time_analysis?.mission_id}
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
