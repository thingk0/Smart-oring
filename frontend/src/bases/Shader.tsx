import { SoftShadows } from '@react-three/drei';

function Shader() {
  const config = {
    size: 25,
    focus: 0,
    samples: 10,
  };
  return (
    <>
      <SoftShadows {...config} />
    </>
  );
}
export default Shader;
