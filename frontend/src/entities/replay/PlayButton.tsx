import PlayArrowIcon from '@mui/icons-material/PlayArrow';
import PauseIcon from '@mui/icons-material/Pause';
import { useEffect, useState } from 'react';
import { useReplayStore } from '@shared/index';

export function PlayButton() {
  const {
    isPlaying,
    actions: { setIsPlaying },
  } = useReplayStore();

  return (
    <button onClick={() => setIsPlaying(!isPlaying)}>
      {isPlaying ? <PauseIcon /> : <PlayArrowIcon />}
    </button>
  );
}
