import { Html } from '@react-three/drei';
import classes from './AGVToolTip.module.css';
interface AGVToolTipProps {
  battery: number;
  hovered: boolean;
}
export function AGVToolTip({ battery, hovered }: AGVToolTipProps) {
  return (
    <>
      <Html position={[0, 2, 0]}>
        <div className={`${classes.content} ${hovered && classes.hovered}`}>
          배터리 {battery}%
        </div>
      </Html>
    </>
  );
}
