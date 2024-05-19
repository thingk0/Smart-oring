import FormGroup from '@mui/material/FormGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import Checkbox from '@mui/material/Checkbox';
import { useReplayStore } from '@shared/store';

export default function ReplayCheckbox() {
  const {
    isOverlayOn,
    actions: { setIsOverlayOn },
  } = useReplayStore();
  return (
    <FormGroup>
      <FormControlLabel
        control={
          <Checkbox
            checked={isOverlayOn}
            onChange={e => setIsOverlayOn(e.target.checked)}
          />
        }
        label="오버레이"
        sx={{
          '& .MuiFormControlLabel-label': { color: 'white' },
          '& .MuiCheckbox-root': { color: 'white' },
        }}
      />
    </FormGroup>
  );
}
