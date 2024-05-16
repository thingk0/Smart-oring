import { PlayButton } from './PlayButton';
import { ReplaySlider } from './ReplaySlider';
import classes from './ReplayBar.module.css';
import { TimeStatus } from './TimeStatus';
import { useEffect } from 'react';
import { Mark } from '@mui/base/useSlider';

export function ReplayBar({ marks }: { marks: Mark[] }) {
  useEffect(() => {
    console.log(marks);
  }, [marks]);
  return (
    <div className={classes.container}>
      <TimeStatus />
      <ReplaySlider marks={marks} />
      <PlayButton />
    </div>
  );
}
