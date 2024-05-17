import { Button, Typography } from '@mui/material';
import ReplayIcon from '@mui/icons-material/Replay';
import ReactApexChart from 'react-apexcharts';
import { MissionHistoryType } from '@entity/Analysis/store/useMissionStore';
import styles from '../Analysis.module.css';
import { MissionData, getDate, getTime } from './MissionList';
import { useViewStore } from '@shared/store/useViewStore';
import { useReplayStore } from '@shared/store';

interface MissionHistoryProps {
  history: MissionHistoryType;
  title: MissionData;
}

function MissionHistory({ history, title }: MissionHistoryProps) {
  // console.log(history);

  const {
    actions: { setCurrentView, setMissionId },
  } = useViewStore();

  const {
    actions: {
      setIsPlaying,
      setTotalTime,
      setCurrentTime,
      increaseCurrentTime,
      setSpeed,
    },
  } = useReplayStore();

  const onChangeReplayHandler = () => setCurrentView('Replay');

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
              AMR {title.amr_id} | Mission {title.mission_id}
            </Typography>
            <Typography component="p" variant="body2">
              미션 시작 :{' '}
              {getDate(title.mission_started_at) +
                ' ' +
                getTime(title.mission_started_at)}
            </Typography>
            <Typography component="p" variant="body2">
              미션 종료 :{' '}
              {getDate(title.mission_finished_at) +
                ' ' +
                getTime(title.mission_finished_at)}
            </Typography>
          </div>

          <Button
            variant="contained"
            endIcon={<ReplayIcon />}
            onClick={() => {
              setMissionId(title.mission_id);
              setIsPlaying(false);
              setTotalTime(0);
              setCurrentTime(0);
              setSpeed(1);
              increaseCurrentTime();
              onChangeReplayHandler();
            }}
          >
            Replay
          </Button>
        </div>
        <div className={styles.flex_notcenter}>
          {history.mission_execution_time_analysis &&
          Object.keys(history.mission_execution_time_analysis).length > 0 ? (
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
          ) : (
            <span>History 데이터가 없습니다.</span>
          )}
          {history.amr_status_timeline.length > 0 ? (
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
          ) : (
            <span>Timeline 데이터가 없습니다.</span>
          )}
        </div>
      </div>
    )
  );
}

export default MissionHistory;
