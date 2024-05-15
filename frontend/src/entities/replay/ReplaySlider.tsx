import Slider from '@mui/material/Slider';
import { useReplayStore } from '@shared/store';
import { useEffect } from 'react';

const marks = [
  {
    value: 0,
    label: '0°C',
  },
  {
    value: 20,
    label: '20°C',
  },
  {
    value: 37,
    label: '37°C',
  },
  {
    value: 100,
    label: '100°C',
  },
];

function valuetext(value: number) {
  return `${value}°C`;
}

export function ReplaySlider() {
  const {
    totalTime,
    currentTime,
    isPlaying,
    actions: { increaseCurrentTime, setCurrentTime },
  } = useReplayStore();
  console.log(totalTime);
  useEffect(() => {
    let interval: NodeJS.Timeout;
    if (isPlaying) {
      interval = setInterval(() => {
        increaseCurrentTime();
      }, 1000);
    }
    return () => clearInterval(interval);
  }, [isPlaying]);
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
      value={currentTime}
    />
  );
}
