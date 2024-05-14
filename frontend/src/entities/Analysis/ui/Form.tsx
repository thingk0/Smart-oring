import { useEffect } from 'react';
import axios from 'axios';
import useMissionStore from '../store/useMissionStore';

const AMRs = ['AMR01', 'AMR02', 'AMR03', 'AMR04', 'AMR05'];

function Form() {
  const setMissionList = useMissionStore(state => state.setMissionList);

  useEffect(() => {
    axios.get(import.meta.env.VITE_MISSION_URL).then(res => {
      setMissionList(res.data.content);
      console.log(res.data.content);
    });
  }, []);

  return (
    <div>
      <p>필터</p>
      <form action="#" method="get" onSubmit={() => {}}>
        {/* AMR 종류 선택, 다중선택임 */}
        <label htmlFor="AMR_type">AMR 종류</label>
        <select name="" id="AMR_type">
          {AMRs.map((info: any, index: number) => {
            return <option key={index}>{info}</option>;
          })}
        </select>
        {/* 분석 시간 선정 */}
        {/* 오늘부터 6개월 전까지 데이터 보관*/}
        <label htmlFor="analysis_start_range">분석시간 범위</label>
        <input type="date" name="startTime" id="analysis_start_range" />
        <input type="date" name="endTime" id="analysis_end_range" />
        {/* 병목 시간 선정 */}
        <label htmlFor="bottleneck_time">병목시간</label>
        <input type="number" id="bottleneck_time" />
        <button type="submit">검색</button>
      </form>
    </div>
  );
}

export default Form;
