import { PlayButton } from './PlayButton';
import { ReplaySlider } from './ReplaySlider';
import classes from './ReplayBar.module.css';
import { TimeStatus } from './TimeStatus';
import { useEffect } from 'react';
import { Mark } from '@mui/base/useSlider';
import SpeedInput from './SpeedInput';
import Stack from '@mui/material/Stack';

export function ReplayBar({ marks }: { marks: Mark[] }) {
  useEffect(() => {
    console.log(marks);
  }, [marks]);
  return (
    <div className={classes.container}>
      <TimeStatus />
      <ReplaySlider marks={marks} />
      <Stack
        direction="row"
        justifyContent="space-between"
        alignItems="center"
        spacing={1}
      >
        <PlayButton />
        <SpeedInput />
      </Stack>
    </div>
  );
}
