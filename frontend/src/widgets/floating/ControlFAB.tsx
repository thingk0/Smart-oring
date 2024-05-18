import Fab from '@mui/material/Fab';
import DoneIcon from '@mui/icons-material/Done';
import { useControlStore } from '@shared/store/useControlStore';
import { postMission } from '@shared/api';
import { useViewStore } from '@shared/store/useViewStore';
import ClearIcon from '@mui/icons-material/Clear';
export function ControlFAB() {
  return (
    <>
      <SendMissionFAB />
      <CancelMissionFAB />
    </>
  );
}
export function SendMissionFAB() {
  const { nodeList } = useControlStore();
  const {
    actions: { setCurrentView },
  } = useViewStore();
  return (
    <Fab
      color="primary"
      aria-label="add"
      sx={{ position: 'absolute', bottom: 16, right: 16 }}
      onClick={() => {
        async function sendMission() {
          console.log(nodeList);
          const result = await postMission(nodeList);
          console.log(result);
          setCurrentView('Monitoring');
        }
        sendMission();
      }}
    >
      <DoneIcon />
    </Fab>
  );
}
export function CancelMissionFAB() {
  const {
    actions: { setCurrentView },
  } = useViewStore();
  return (
    <Fab
      color="secondary"
      aria-label="add"
      sx={{ position: 'absolute', bottom: 16, right: 86 }}
      onClick={() => {
        setCurrentView('Monitoring');
      }}
    >
      <ClearIcon />
    </Fab>
  );
}
