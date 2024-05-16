import { Container } from '@mui/material';
import { ReplayBar } from '@entity/index.ts';
import { useQuery } from '@tanstack/react-query';
import { getReplayData, useReplayStore } from '@shared/index.ts';
import { useEffect, useState } from 'react';
import Renderer from '@base/Renderer';
import { Mark } from '@mui/base/useSlider';

export function ReplayPage() {
  const { data } = useQuery({ queryKey: ['replay'], queryFn: getReplayData });
  const {
    actions: { setTotalTime },
  } = useReplayStore();
  const [marks, setMarks] = useState<Mark[]>([]);
  useEffect(() => {
    setTotalTime(data?.length - 1);
    if (!data) return;
    const bottleneckTimeList = [];
    for (let i = 0; i < data.length - 1; i++) {
      for (let j = 0; j < data[i].amrHistoryDtoList.length - 1; j++) {
        const hasBottleneck =
          data[i].amrHistoryDtoList[j]['amrStatus'] === 'BOTTLENECK';
        if (hasBottleneck) {
          bottleneckTimeList.push({ value: i });
          continue;
        }
      }
    }
    console.log(bottleneckTimeList);
    setMarks(bottleneckTimeList);
  }, [data]);

  if (!data) return <>no data</>;
  return (
    <>
      <Renderer />
      <ReplayBar marks={marks} />
    </>
  );
}
