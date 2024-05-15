import { Container } from '@mui/material';
import { ReplayBar } from '@entity/index.ts';
import { useQuery } from '@tanstack/react-query';
import { getReplayData, useReplayStore } from '@shared/index.ts';
import { useEffect } from 'react';

export function ReplayPage() {
  const { data } = useQuery({ queryKey: ['replay'], queryFn: getReplayData });
  const {
    actions: { setTotalTime },
  } = useReplayStore();
  useEffect(() => {
    setTotalTime(data?.length);
  }, [data]);

  if (!data) return <>no data</>;
  return (
    <>
      <main>
        <Container fixed>
          <ReplayBar />
        </Container>
      </main>
    </>
  );
}
