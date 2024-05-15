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
      <header className={styles.nav_wrap}>
        <nav className={styles.flex}>
          <Typography variant="h1" component="h1">
            <a href="#">Analysis</a>
          </Typography>

          <Tabs
            value={type}
            onChange={onChangeHandler}
            aria-label="Analysis Tab Nagivation"
          >
            <Tab label="realtime dashboard" />
            <Tab label="discover mission" />
            <Tab label="bottleneck coordinate" />
          </Tabs>
        </nav>
        <Button variant="contained">Back to Monitoring</Button>
      </header>

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
