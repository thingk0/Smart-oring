import Fab from '@mui/material/Fab';
import DoneIcon from '@mui/icons-material/Done';
import { useControlStore } from '@shared/store/useControlStore';
import { postMission } from '@shared/api';
export function SendMissionFAB() {
  const {
    isControlMode,
    nodeList,
    actions: { setIsControlMode },
  } = useControlStore();
  if (isControlMode)
    return (
      <Fab
        color="primary"
        aria-label="add"
        sx={{ position: 'absolute', bottom: 16, right: 86 }}
        onClick={() => {
          async function sendMission() {
            console.log(nodeList);
            const result = await postMission(nodeList);
            console.log(result);
            setIsControlMode(false);
          }
          sendMission();
        }}
      >
        <DoneIcon />
      </Fab>
    );
}
