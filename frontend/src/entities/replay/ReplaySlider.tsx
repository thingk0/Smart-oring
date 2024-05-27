import Slider from '@mui/material/Slider';
import { useReplayStore } from '@shared/store';
import { useEffect } from 'react';
import { Mark } from '@mui/base/useSlider';
import { secondsToHMS } from '@shared/lib';

function valuetext(value: number) {
  return `${value}°C`;
}

export function ReplaySlider({ marks }: { marks: Mark[] }) {
  const {
    totalTime,
    currentTime,
    isPlaying,
    speed,
    actions: { increaseCurrentTime, setCurrentTime },
  } = useReplayStore();
  useEffect(() => {
    let interval: NodeJS.Timeout;
    if (isPlaying) {
      interval = setInterval(() => {
        increaseCurrentTime();
      }, 1000 / speed);
    }
    return () => clearInterval(interval);
  }, [isPlaying, speed]);
  function valueLabelFormat(value: number) {
    return secondsToHMS(value);
  }
  const handleChange = (event: Event, newValue: number | number[]) => {
    setCurrentTime(newValue as number);
  };
  return (
    <Slider
      aria-label="Custom marks"
      getAriaValueText={valuetext}
      valueLabelDisplay="auto"
      marks={marks}
      min={0}
      max={totalTime}
      onChange={handleChange}
      valueLabelFormat={valueLabelFormat}
      value={currentTime}
      sx={{
        height: 8, // 원하는 높이로 조절
        '& .MuiSlider-thumb': {
          height: 24, // thumb의 높이 조절
          width: 24, // thumb의 너비 조절
        },
        '& .MuiSlider-rail': {
          height: 8, // rail의 높이 조절
        },
        '& .MuiSlider-track': {
          height: 8, // track의 높이 조절
        },
        '& .MuiSlider-mark': {
          height: 8,
          width: 'calc(1%)',
          color: '#ff5',
          backgroundColor: '#ff5e00',
          opacity: '0.3',
        },
      }}
    />
  );
}
