type RealTimeErrorProps = {
  data: [
    {
      amrId: number;
      missionId: number;
    },
  ];
};

function RealTimeError({ data }: RealTimeErrorProps) {
  return (
    <div>
      <h3>실시간 에러</h3>
      {data.map(({ amrId, missionId }) => {
        return (
          <p key={amrId}>
            {amrId}번 기기 - {missionId} 미션 수행 시 에러
          </p>
        );
      })}
    </div>
  );
}

export default RealTimeError;
