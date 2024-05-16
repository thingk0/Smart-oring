import MissionList from './MissionList';
import MissionHistory from './MissionHistory';
import styles from './Analysis.module.css';
import MissionFilter from './compound/MissionFilter';

function Mission() {
  return (
    <div>
      <MissionFilter />
      <div className={styles.mission_grid}>
        <MissionList />
        <MissionHistory />
      </div>
    </div>
  );
}
export default Mission;
