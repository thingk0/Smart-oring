import { Suspense } from 'react';

import Dashboard from './Dashboard';
import Heatmap from './Heatmap';
import { LoadData } from '@shared/api';
import styles from './Analysis.module.css';

function Analysis() {
  return (
    <div className={styles.wrap}>
      <Suspense fallback={<h1>Loading...</h1>}>
        <Heatmap resource={LoadData(import.meta.env.VITE_HEATMAP_URL)} />
        {/* <Dashboard resource={LoadData(import.meta.env.VITE_DASHBOARD_URL)} /> */}
      </Suspense>
    </div>
  );
}

export default Analysis;
