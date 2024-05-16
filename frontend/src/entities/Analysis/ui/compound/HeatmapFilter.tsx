import dayjs from 'dayjs';
import styles from '../Analysis.module.css';
import Form from './Form';

interface HeatmapFilterProps {
  setState: React.Dispatch<React.SetStateAction<object>>;
}

function HeatmapFilter({ setState }: HeatmapFilterProps) {
  const date = dayjs(new Date());

  return (
    <div className={styles.filter}>
      <Form URL={import.meta.env.VITE_HEATMAP_URL}>
        <Form.Title variant="h3" component="h2">
          검색 조건
        </Form.Title>
        <Form.Label id="analysis_start_range">분석시간 범위</Form.Label>
        <Form.DatepickerProvider>
          <Form.Datepicker
            defaultValue={date}
            maxDate={date}
            queryParam="startTime"
          />
          <Form.Datepicker
            defaultValue={date}
            maxDate={date}
            queryParam="endTime"
          />
        </Form.DatepickerProvider>
        <Form.Label id="bottleneck_time">병목 시간</Form.Label>
        <Form.TextField
          type="number"
          defaultValue="1"
          variant="standard"
          queryParam="bottleneckSeconds"
        />
        <Form.Button
          variant="contained"
          url={import.meta.env.VITE_MISSION_URL}
          setState={setState}
        >
          검색
        </Form.Button>
      </Form>
    </div>
  );
}

export default HeatmapFilter;
