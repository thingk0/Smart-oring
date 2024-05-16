import { secondsToHMS } from '@shared/lib';
import { useReplayStore } from '@shared/store';
import classes from './TimeStatus.module.css';

export function TimeStatus() {
  const { currentTime, totalTime } = useReplayStore();
  return (
    <div className={classes.container}>
      <time>{secondsToHMS(currentTime)}</time>
      <span style={{ color: '#7abdff' }}>/</span>
      <time>{secondsToHMS(totalTime)}</time>
    </div>
  );
}
