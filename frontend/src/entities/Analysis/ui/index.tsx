import { Suspense, useState } from 'react';

import Dashboard from './Dashboard';
import Heatmap from './Heatmap';
import Mission from './Mission';

import { LoadData } from '@shared/api';
import styles from './Analysis.module.css';
import { Button, CircularProgress, Tab, Tabs, Typography } from '@mui/material';

function Analysis() {
  const [type, setType] = useState(0);

  const onChangeHandler = (_: React.SyntheticEvent, newValue: number) => {
    setType(newValue);
  };

  return (
    <div className={styles.wrap}>
      <nav className={styles.nav_wrap}>
        <div className={styles.flex}>
          <Typography variant="h4" component="h1">
            Analysis
          </Typography>

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
        <Button variant="contained">Back to Monitoring</Button>
      </nav>

      <div className={styles.body_margin}>
        <Suspense fallback={<CircularProgress />}>
          {type === 0 && (
            <Dashboard
              resource={LoadData(import.meta.env.VITE_DASHBOARD_URL)}
            />
          )}
          {type === 1 && <Mission />}
          {type === 2 && (
            <Heatmap resource={LoadData(import.meta.env.VITE_HEATMAP_URL)} />
          )}
        </Suspense>
      </div>
    </div>
  );
}

export default Analysis;
