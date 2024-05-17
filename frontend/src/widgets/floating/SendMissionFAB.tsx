import Fab from '@mui/material/Fab';
import DoneIcon from '@mui/icons-material/Done';
import { useControlStore } from '@shared/store/useControlStore';
import { postMission } from '@shared/api';
export function SendMissionFAB() {
  const { isControlMode, nodeList } = useControlStore();
  if (isControlMode)
    return (
      <Fab
        color="primary"
        aria-label="add"
        sx={{ position: 'absolute', bottom: 16, right: 86 }}
        onClick={() => {
          postMission(nodeList);
        }}
      >
        <DoneIcon />
      </Fab>
    );
}
