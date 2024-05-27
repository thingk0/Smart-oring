import { Html } from '@react-three/drei';
import classes from './ReplayOverlay.module.css';
import { useReplayStore } from '@shared/store';

export default function ReplayOverlay({ amrId }: { amrId: number }) {
  const replayStore = useReplayStore();
  return (
    <Html>
      <div
        className={`${amrId === replayStore.amrId ? classes.point : classes.content}`}
      >
        {amrId}
      </div>
    </Html>
  );
}
