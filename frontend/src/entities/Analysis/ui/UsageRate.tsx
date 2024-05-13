type UsageRateProps = {
  data: [{ amrId: number; UsagePercent: number }];
};

function UsageRate({ data }: UsageRateProps) {
  return (
    <div>
      <p>AMR 사용률 Top 3</p>
      <ol>
        {data.map((amr: any) => {
          return <li key={amr.amrId}>{amr.UsagePercent}</li>;
        })}
      </ol>
    </div>
  );
}

export default UsageRate;
