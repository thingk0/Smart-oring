import axios from 'axios';
import {
  List,
  ListItem,
  ListItemButton,
  ListItemText,
  Typography,
} from '@mui/material';
import { MissionHistoryType, MissionObject } from '../../store/useMissionStore';
import styles from '../Analysis.module.css';

interface MissionListProps {
  list: MissionObject[];
  setHistory: React.Dispatch<React.SetStateAction<MissionHistoryType>>;
  setTitle: React.Dispatch<React.SetStateAction<MissionData>>;
}

export interface MissionData {
  mission_id: number;
  amr_id: number;
  amr_code: string;
  delay_time: number;
  mission_started_at: string;
  mission_finished_at: string;
}

function MissionList({ list, setHistory, setTitle }: MissionListProps) {
  const onClickHandler = (mission: MissionData) => {
    setTitle({
      mission_id: mission.mission_id,
      amr_id: mission.amr_id,
      mission_started_at: mission.mission_started_at,
      mission_finished_at: mission.mission_finished_at,
    });

    axios
      .get(
        import.meta.env.VITE_MISSION_HISTORY_URL +
          `/${mission.mission_id}/analysis`
      )
      .then(res => {
        setHistory(res.data.resultData);
      });
  };

  return (
    <aside className={styles.component_background}>
      <Typography variant="h3" component="h2">
        미션 리스트
      </Typography>
      <List sx={{ height: '300px', overflowY: 'scroll' }}>
        {list.content && list.content.length > 0 ? (
          list.content?.map((mission: MissionObject) => (
            <ListItem key={mission.mission_id} divider={true}>
              <ListItemButton>
                <Mission mission={mission} onClickHandler={onClickHandler} />
              </ListItemButton>
            </ListItem>
          ))
        ) : (
          <>데이터가 없습니다</>
        )}
      </List>
    </aside>
  );
}

type MissionProps = {
  mission: MissionObject;
  onClickHandler: Function;
};

export const getDate = (datetime: string) => {
  const tmp = new Date(datetime);
  return tmp.toLocaleDateString();
};

export const getTime = (datetime: string) => {
  const tmp = new Date(datetime);
  return tmp.toLocaleTimeString();
};

function Mission({ mission, onClickHandler }: MissionProps) {
  return (
    <>
      <ListItemText
        onClick={() => {
          onClickHandler(mission);
        }}
      >
        <div>
          <Typography variant="h4" component="h3">
            AMR {mission.amr_id} | MISSION {mission.mission_id}
          </Typography>
          <Typography variant="body2" component="p">
            지연 시간 : {mission.delay_time}초
          </Typography>
          <Typography variant="body2" component="p">
            미션 날짜 : {getDate(mission.mission_started_at)} ~
            {' ' + getDate(mission.mission_finished_at)}
          </Typography>
          <Typography variant="body2" component="p">
            미션 시간 : {getTime(mission.mission_started_at)} ~
            {' ' + getTime(mission.mission_finished_at)}
          </Typography>
        </div>
      </ListItemText>
    </>
  );
}

export default MissionList;
