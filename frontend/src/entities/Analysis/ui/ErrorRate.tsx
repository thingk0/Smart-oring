type ErrorRateProps = {
  data: [{ amrId: number; ErrorPercent: number }];
};

function ErrorRate({ data }: ErrorRateProps) {
  return (
    <div>
      <p>AMR 오류율 Worst 3</p>
      <ol>
        {data.map((amr: any) => {
          return <li key={amr.amrId}>{amr.ErrorPercent}</li>;
        })}
      </ol>
    </div>
  );
}

export default ErrorRate;
