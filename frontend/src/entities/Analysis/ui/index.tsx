import { Suspense, useState } from 'react';

import Dashboard from './Dashboard';
import Heatmap from './Heatmap';
import Mission from './Mission';

import { LoadData } from '@shared/api';
import styles from './Analysis.module.css';

function Analysis() {
  const [type, setType] = useState('dashboard');

  const onClickHandler = (val: string) => {
    setType(val);
  };

  return (
    <div className={styles.wrap}>
      <div>
        <p>top navigation</p>
        <ul>
          <li onClick={() => onClickHandler('dashboard')}>Dashboard</li>
          <li onClick={() => onClickHandler('mission')}>Mission</li>
          <li onClick={() => onClickHandler('heatmap')}>Heatmap</li>
        </ul>
      </div>

      <Suspense fallback={<h1>Loading...</h1>}>
        {type === 'dashboard' && (
          <Dashboard resource={LoadData(import.meta.env.VITE_DASHBOARD_URL)} />
        )}
        {type === 'mission' && <Mission />}
        {type === 'heatmap' && (
          <Heatmap resource={LoadData(import.meta.env.VITE_HEATMAP_URL)} />
        )}
      </Suspense>
    </div>
  );
}

export default Analysis;
