import { Suspense, useState } from 'react';

import Dashboard from './Dashboard';
import Heatmap from './Heatmap';
import Mission from './Mission';

import { LoadData } from '@shared/api';
import styles from './Analysis.module.css';
import { Box, Tab, Tabs, Typography } from '@mui/material';

function Analysis() {
  const [type, setType] = useState(0);

  const onChangeHandler = (event: React.SyntheticEvent, newValue: number) => {
    setType(newValue);
  };

  return (
    <div className={styles.wrap}>
      <div>
        <Typography>top navigation</Typography>

        <Tabs
          value={type}
          onChange={onChangeHandler}
          aria-label="Analysis Tab Nagivation"
        >
          <Tab label="dashboard" />
          <Tab label="mission" />
          <Tab label="heatmap" />
        </Tabs>
      </div>

      <Suspense fallback={<h1>Loading...</h1>}>
        {type === 0 && (
          <Dashboard resource={LoadData(import.meta.env.VITE_DASHBOARD_URL)} />
        )}
        {type === 1 && <Mission />}
        {type === 2 && (
          <Heatmap resource={LoadData(import.meta.env.VITE_HEATMAP_URL)} />
        )}
      </Suspense>
    </div>
  );
}

export default Analysis;
