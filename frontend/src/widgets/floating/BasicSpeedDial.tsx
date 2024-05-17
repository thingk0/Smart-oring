import SpeedDial from '@mui/material/SpeedDial';
import SpeedDialIcon from '@mui/material/SpeedDialIcon';
import SpeedDialAction from '@mui/material/SpeedDialAction';
import SaveIcon from '@mui/icons-material/Save';
import ShareIcon from '@mui/icons-material/Share';
import AnalyticsIcon from '@mui/icons-material/Analytics';
import SettingsIcon from '@mui/icons-material/Settings';
import { useNavigate } from 'react-router-dom';
import SwitchVideoIcon from '@mui/icons-material/SwitchVideo';
import { useViewStore } from '@shared/store/useViewStore';
import TouchAppIcon from '@mui/icons-material/TouchApp';
import { useControlStore } from '@shared/store/useControlStore';
export default function BasicSpeedDial() {
  const {
    actions: { switchCamera, setCurrentView },
  } = useViewStore();
  const {
    actions: { setIsControlMode },
  } = useControlStore();
  const actions = [
    {
      icon: <SettingsIcon />,
      name: '설정',
      onClick: () => {
        setCurrentView('Setting');
      },
    },
    {
      icon: <SwitchVideoIcon />,
      name: '카메라 전환',
      onClick: () => {
        switchCamera();
      },
    },
    {
      icon: <AnalyticsIcon />,
      name: '분석',
      onClick: () => setCurrentView('Analysis'),
    },
    {
      icon: <TouchAppIcon />,
      name: '로봇 제어',
      onClick: () => {
        setIsControlMode(true);
      },
    },
  ];

  return (
    <SpeedDial
      ariaLabel="SpeedDial basic example"
      sx={{ position: 'absolute', bottom: 16, right: 16 }}
      icon={<SpeedDialIcon />}
    >
      {actions.map(action => (
        <SpeedDialAction
          key={action.name}
          icon={action.icon}
          tooltipTitle={action.name}
          onClick={action.onClick}
        />
      ))}
    </SpeedDial>
  );
}
