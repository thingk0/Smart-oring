import Light from './Light';
import Mesh from './Mesh';
import { EffectComposer, N8AO, Bloom } from '@react-three/postprocessing';

function Scene() {
  return (
    <>
      <Light />
      <Mesh />
      <EffectComposer enableNormalPass={false}>
        <N8AO aoRadius={0.5} intensity={1} />
        <Bloom luminanceThreshold={1} intensity={0.5} levels={9} mipmapBlur />
      </EffectComposer>
    </>
  );
}

export default Scene;
