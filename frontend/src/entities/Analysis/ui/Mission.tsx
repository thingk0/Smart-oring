import Form from './Form';
import MissionList from './MissionList';
import MissionHistory from './MissionHistory';
import styles from './Analysis.module.css';

function Mission() {
  return (
    <div>
      <Form />
      <div className={styles.mission_grid}>
        <MissionList />
        <MissionHistory />
      </div>
    </div>
  );
}
export default Mission;
