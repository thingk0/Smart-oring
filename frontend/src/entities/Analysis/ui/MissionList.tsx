import axios from 'axios';
import useMissionStore from '../store/useMissionStore';

function MissionList() {
  const list = useMissionStore(state => state.missionList);
  const setHistory = useMissionStore(state => state.setMissionHistory);

  const onClickHandler = () => {
    axios.get(import.meta.env.VITE_MISSION_HISTORY_URL).then(res => {
      console.log(res.data.data);
      setHistory(res.data.data);
    });
  };

  return (
    <aside>
      <h3>리스트</h3>
      <ul>
        {list?.map((mission: any) => (
          <Mission
            key={mission.mission_id}
            mission={mission}
            onClick={() => onClickHandler()}
          />
        ))}
      </ul>
    </aside>
  );
}

type MissionProps = {
  mission: any;
  onClick: React.MouseEventHandler;
};

function Mission({ mission, onClick }: MissionProps) {
  return <li onClick={onClick}>{mission.mission_id}</li>;
}

export default MissionList;
