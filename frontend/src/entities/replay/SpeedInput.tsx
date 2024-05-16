import * as React from 'react';
import { styled } from '@mui/system';
import RemoveIcon from '@mui/icons-material/Remove';
import AddIcon from '@mui/icons-material/Add';
import { useReplayStore } from '@shared/store';

export default function SpeedInput() {
  const {
    speed,
    actions: { setSpeed },
  } = useReplayStore();
  const step = 0.5;
  const min = 0.5;
  const max = 3;

  const handleIncrement = () => {
    if (speed + step <= max) {
      setSpeed(speed + step);
    }
  };

  const handleDecrement = () => {
    if (speed - step >= min) {
      setSpeed(speed - step);
    }
  };

  return (
    <StyledInputRoot>
      <StyledButton onClick={handleDecrement}>
        <RemoveIcon fontSize="small" />
      </StyledButton>
      <StyledPre>{speed.toFixed(1)}</StyledPre>
      <StyledButton onClick={handleIncrement}>
        <AddIcon fontSize="small" />
      </StyledButton>
    </StyledInputRoot>
  );
}

const blue = {
  100: '#daecff',
  200: '#b6daff',
  300: '#66b2ff',
  400: '#3399ff',
  500: '#007fff',
  600: '#0072e5',
  700: '#0059B2',
  800: '#004c99',
};

const grey = {
  50: '#F3F6F9',
  100: '#E5EAF2',
  200: '#DAE2ED',
  300: '#C7D0DD',
  400: '#B0B8C4',
  500: '#9DA8B7',
  600: '#6B7A90',
  700: '#434D5B',
  800: '#303740',
  900: '#1C2025',
};

const StyledInputRoot = styled('div')(
  ({ theme }) => `
  font-weight: 400;
  color: ${theme.palette.mode === 'dark' ? grey[300] : grey[500]};
  display: flex;
  flex-flow: row nowrap;
  justify-content: center;
  align-items: center;
`
);

const StyledPre = styled('pre')(
  () => `
  font-family: inherit;
  font-weight: 600;
  line-height: 1.375;
  margin: 0 8px;
  padding: 10px 12px;
  min-width: 0;
  width: 4rem;
  text-align: center;
  background: none;
  border: none;
  box-shadow: none;
  color: white;
`
);

const StyledButton = styled('button')(
  ({ theme }) => `
  font-size: 0.875rem;
  box-sizing: border-box;
  line-height: 1.5;
  border: 1px solid;
  border-radius: 999px;
  border-color: ${theme.palette.mode === 'dark' ? grey[800] : grey[200]};
  background: ${theme.palette.mode === 'dark' ? grey[900] : grey[50]};
  color: ${theme.palette.mode === 'dark' ? grey[200] : grey[900]};
  width: 32px;
  height: 32px;
  display: flex;
  flex-flow: row nowrap;
  justify-content: center;
  align-items: center;
  transition-property: all;
  transition-timing-function: cubic-bezier(0.4, 0, 0.2, 1);
  transition-duration: 120ms;

  &:hover {
    cursor: pointer;
    background: ${theme.palette.mode === 'dark' ? blue[700] : blue[500]};
    border-color: ${theme.palette.mode === 'dark' ? blue[500] : blue[400]};
    color: ${grey[50]};
  }

  &:focus-visible {
    outline: 0;
  }
`
);
