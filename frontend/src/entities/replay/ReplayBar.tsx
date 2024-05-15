import { PlayButton } from './PlayButton';
import { ReplaySlider } from './ReplaySlider';
import classes from './ReplayBar.module.css';
import { TimeStatus } from './TimeStatus';

export function ReplayBar() {
  return (
    <div className={classes.container}>
      <TimeStatus />
      <ReplaySlider />
      <PlayButton />
    </div>
  );
}
