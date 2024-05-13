type RealTimeBottleneck = {
  data: [
    {
      amrId: number;
      xcoordinate: number;
      ycoordinate: number;
    },
  ];
};

function RealTimeBottleneck({ data }: RealTimeBottleneck) {
  return (
    <div>
      <h3>실시간 병목</h3>
      {data.map(({ amrId, xcoordinate, ycoordinate }) => {
        return (
          <p key={amrId}>
            {amrId}번 기기 - ({xcoordinate}, {ycoordinate}) 좌표 병목
          </p>
        );
      })}
    </div>
  );
}

export default RealTimeBottleneck;
