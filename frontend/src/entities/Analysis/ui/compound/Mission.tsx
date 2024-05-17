import MissionList from './MissionList';
import MissionHistory from './MissionHistory';
import MissionFilter from './MissionFilter';
import styles from '../Analysis.module.css';
import { useState } from 'react';
import {
  MissionHistoryType,
  MissionObject,
} from '@entity/Analysis/store/useMissionStore';

function Mission() {
  const [list, setList] = useState<MissionObject[]>([]);
  const [history, setHistory] = useState<MissionHistoryType>({});
  const [title, setTitle] = useState<object>({});

  return (
    <div>
      <MissionFilter setList={setList} />
      <div className={styles.mission_grid}>
        <MissionList list={list} setHistory={setHistory} setTitle={setTitle} />
        <MissionHistory history={history} title={title} />
      </div>
    </div>
  );
}
export default Mission;
