import dayjs from 'dayjs';
import styles from '../Analysis.module.css';
import Form from './Form';
import { MissionObject } from '@entity/Analysis/store/useMissionStore';
import { MenuItem, Select, SelectChangeEvent } from '@mui/material';
import { useState } from 'react';

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

interface MissionFilterProps {
  setList: React.Dispatch<React.SetStateAction<MissionObject[]>>;
}

function MissionFilter({ setList }: MissionFilterProps) {
  const date = dayjs(Date.now());

  const [type, setType] = useState('');

  return (
    <div className={styles.filter}>
      <Form URL={import.meta.env.VITE_MISSION_URL}>
        <Form.Title variant="h3" component="h2">
          검색 조건
        </Form.Title>
        <Form.Label id="amrtype">AMR Type</Form.Label>

        {/* 되는 코드 */}
        <Select
          labelId="amrtype"
          id="type"
          value={type}
          onChange={(event: SelectChangeEvent) => setType(event.target.value)}
        >
          {AMRs.map((info: string, index: number) => {
            return (
              <MenuItem key={index} value={'AMR00' + info.split('AMR')[1]}>
                {info}
              </MenuItem>
            );
          })}
        </Select>

        {/* 안되는 코드 */}
        {/* <Form.Select id="amrtype" label="AMR type" queryParam="amrType">
          {AMRs.map((info: string, index: number) => {
            return (
              <Form.Option key={index} queryParam="amrType" val={info}>
                {info}
              </Form.Option>
            );
          })}
        </Form.Select> */}
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
        <Form.Button variant="contained" setState={setList} selectValue={type}>
          검색
        </Form.Button>
      </Form>
    </div>
  );
}

export default MissionFilter;
