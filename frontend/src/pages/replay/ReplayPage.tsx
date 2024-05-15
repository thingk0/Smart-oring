import { Container } from '@mui/material';
import { ReplayBar } from '@entity/index.ts';
import { useQuery } from '@tanstack/react-query';
import { getReplayData, useReplayStore } from '@shared/index.ts';
import { useEffect } from 'react';
import Renderer from '@base/Renderer';

export function ReplayPage() {
  const { data } = useQuery({ queryKey: ['replay'], queryFn: getReplayData });
  const {
    actions: { setTotalTime },
  } = useReplayStore();
  useEffect(() => {
    setTotalTime(data?.length - 1);
  }, [data]);

  if (!data) return <>no data</>;
  return (
    <>
      <Renderer />
      <ReplayBar />
    </>
  );
}
