import { useProgress } from '@react-three/drei';

import Gear from './Gear';
import styles from './Loading.module.css';

function CustomLoader() {
  const { errors, progress } = useProgress();

  if (errors.length > 0) return <h1>Resource Load Error</h1>;
  return <>{progress < 100 ? <Loading /> : ''}</>;
}

function Loading() {
  return (
    <div className={styles.wrap}>
      <div className={styles.container}>
        <div className="gear">
          <Gear
            className={styles.gear}
            width={100}
            height={100}
            fill="#f44336"
          />
          <Gear className={styles.gear} width={50} height={50} fill="#4caf50" />
          <Gear className={styles.gear} width={25} height={25} fill="#3241ff" />
        </div>
        <span className={styles.text}>Loading...</span>
      </div>
    </div>
  );
}

export default CustomLoader;
