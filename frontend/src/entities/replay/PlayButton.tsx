import PlayArrowIcon from '@mui/icons-material/PlayArrow';
import PauseIcon from '@mui/icons-material/Pause';
import { useReplayStore } from '@shared/index';
import classes from './PlayButton.module.css';

export function PlayButton() {
  const {
    isPlaying,
    actions: { setIsPlaying },
  } = useReplayStore();

  return (
    <button onClick={() => setIsPlaying(!isPlaying)} className={classes.button}>
      {isPlaying ? (
        <PauseIcon fontSize="large" style={{ color: 'white' }} />
      ) : (
        <PlayArrowIcon fontSize="large" style={{ color: 'white' }} />
      )}
    </button>
  );
}
