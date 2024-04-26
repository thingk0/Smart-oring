import { useQuery } from '@tanstack/react-query';
import { getRobotPosition } from '../../../shared/api';

function Robot() {
  const query = useQuery({
    queryKey: ['robotPosition'],
    queryFn: getRobotPosition,
    staleTime: 1000,
  });

  return (
    <>
      {query.data?.map(p => {
        return (
          <mesh>
            <planeGeometry />
            <meshBasicMaterial color="pink" />
          </mesh>
        );
      })}
    </>
  );
}

export default Robot;
