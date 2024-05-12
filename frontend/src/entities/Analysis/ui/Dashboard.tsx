import { DashboardData } from '../types';
import ErrorRate from './ErrorRate';
import OperaingRate from './OperatingRate';
import UsageRate from './UsageRate';

type DashboardProps = {
  resource: {
    read(): any;
  };
};

function Dashboard({ resource }: DashboardProps) {
  const data: DashboardData[] = resource.read();
  console.log(data);

  return (
    <>
      <h1>Dashboard</h1>
      <div>
        {/* <div>
          <div>미션 처리량</div>
          <div>
            <OperaingRate data={data[0].totalUsagePercent} />
            <UsageRate data={data[0].amrUsagePercent} />
            <ErrorRate data={data[0].amrErrorPercent} />
          </div>
        </div> */}
        <div>
          <div>
            <h3>시간</h3>
          </div>
          <div>
            <h3>오늘 생산량</h3>
            <p>{data[0].todayTotalMissionCount}</p>
          </div>
          <div>
            <h3>실시간 에러</h3>
            {data[0].realtimeError.map(({ amrId, missionId }) => {
              return (
                <p>
                  {amrId}번 기기 - {missionId} 미션 수행 시 에러
                </p>
              );
            })}
          </div>
          <div>
            <h3>실시간 병목</h3>
            {data[0].realtimeBottleneck.map(
              ({ amrId, xcoordinate, ycoordinate }) => {
                return (
                  <p>
                    {amrId}번 기기 - ({xcoordinate}, {ycoordinate}) 좌표 병목
                  </p>
                );
              }
            )}
          </div>
        </div>
      </div>
    </>
  );
}

export default Dashboard;
