type TodayOutputProps = {
  data: number;
};

function TodayOutput({ data }: TodayOutputProps) {
  return (
    <div>
      <h3>오늘 생산량</h3>
      <p>{data}</p>
    </div>
  );
}

export default TodayOutput;
