import { useEffect, useState } from 'react';
import axios from 'axios';
import useMissionStore from '../store/useMissionStore';
import {
  Button,
  InputLabel,
  MenuItem,
  Select,
  SelectChangeEvent,
  TextField,
  Typography,
} from '@mui/material';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import styles from './Analysis.module.css';

const AMRs = [
  'ALL',
  'AMR01',
  'AMR02',
  'AMR03',
  'AMR04',
  'AMR05',
  'AMR06',
  'AMR07',
  'AMR08',
  'AMR09',
  'AMR10',
  'AMR11',
  'AMR12',
  'AMR13',
  'AMR14',
  'AMR15',
  'AMR16',
  'AMR17',
  'AMR18',
  'AMR19',
  'AMR20',
  'AMR21',
  'AMR22',
  'AMR23',
  'AMR24',
];

function Form() {
  const setMissionList = useMissionStore(state => state.setMissionList);

  const [AMRType, setAMRType] = useState('ALL');
  const onSelectAMRHandler = (event: SelectChangeEvent) => {
    setAMRType(event.target.value);
  };

  const [startDate, setStartDate] = useState();
  const [endDate, setEndDate] = useState();
  const [bottleneckTime, setBottleneckTime] = useState();

  useEffect(() => {
    axios
      .get(import.meta.env.VITE_MISSION_URL)
      .then(res => setMissionList(res.data.content));
  }, []);

  return (
    <div className={styles.filter}>
      <form action="#" method="get">
        <div className={styles.flex_center}>
          {/* 다중선택임 */}
          <Typography variant="h3" component="h2">
            검색 조건
          </Typography>

          <InputLabel id="AMRtype-label">AMR Type</InputLabel>
          <Select
            labelId="AMRtype-label"
            label="AMR type"
            value={AMRType}
            onChange={onSelectAMRHandler}
          >
            {AMRs.map((info: any, index: number) => {
              return (
                <MenuItem key={index} value={info}>
                  {info}
                </MenuItem>
              );
            })}
          </Select>

          {/* 오늘부터 6개월 전까지 데이터 보관*/}
          <InputLabel id="analysis_start_range">분석시간 범위</InputLabel>
          <LocalizationProvider dateAdapter={AdapterDayjs}>
            <DatePicker />
            <DatePicker />
          </LocalizationProvider>

          {/* 병목 시간 선정 */}
          <InputLabel id="bottleneck_time">병목 시간</InputLabel>
          <TextField type="number" defaultValue={1} variant="standard" />

          <Button variant="contained">검색</Button>
        </div>
      </form>
    </div>
  );
}

export default Form;
