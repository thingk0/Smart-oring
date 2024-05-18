import { Container } from '@mui/material';
import { ReplayBar } from '@entity/index.ts';
import { useQuery } from '@tanstack/react-query';
import { getReplay, getReplayData, useReplayStore } from '@shared/index.ts';
import { useEffect, useState } from 'react';
import Renderer from '@base/Renderer';
import { Mark } from '@mui/base/useSlider';
import CustomLoader from '@entity/Loading/ui';
import CloseIcon from '@mui/icons-material/Close';
import { useViewStore } from '@shared/store/useViewStore';

export function ReplayPage() {
  const { missionId } = useViewStore();
  const { data, isLoading } = useQuery({
    queryKey: ['replay'],
    queryFn: () => getReplay(missionId),
  });

  useEffect(() => {
    console.log(data);
  }, [data]);

  const {
    actions: { setTotalTime },
  } = useReplayStore();

  const [marks, setMarks] = useState<Mark[]>([]);

  useEffect(() => {
    if (data && data.length > 0) {
      setTotalTime(data?.length - 1);
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
    }
  }, [data]);

  const {
    actions: { setCurrentView },
  } = useViewStore();

  const onCloseHandler = () => {
    setCurrentView('Analysis');
  };

  if (isLoading) return <CustomLoader />;

  return (
    <>
      <CloseIcon
        onClick={onCloseHandler}
        style={{
          position: 'absolute',
          top: 0,
          right: 0,
          cursor: 'pointer',
        }}
        fontSize="large"
      />
      {data.length > 0 ? <ReplayBar marks={marks} /> : <></>}
    </>
  );
}
