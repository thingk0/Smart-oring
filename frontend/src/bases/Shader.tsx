import { SoftShadows } from '@react-three/drei';
import useGraphicsQualityStore from '@shared/store/useGraphicsQualityStore';

function Shader() {
  const { shadowDetail } = useGraphicsQualityStore();
  const config = {
    size: 25,
    focus: 0,
    samples: 10,
  };
  return <>{shadowDetail === 'high' && <SoftShadows {...config} />}</>;
}
export default Shader;
