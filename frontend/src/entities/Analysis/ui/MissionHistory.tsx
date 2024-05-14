import useMissionStore from '../store/useMissionStore';

function MissionHistory() {
  const history = useMissionStore(state => state.missionHistory);

  return (
    <>
      <h3>미션 히스토리</h3>
      <p>{history?.total_execution_time}</p>
    </>
  );
}

export default MissionHistory;
