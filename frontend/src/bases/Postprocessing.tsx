import { Bloom, EffectComposer, N8AO } from '@react-three/postprocessing';

function PostProcessing() {
  return (
    <>
      <EffectComposer enableNormalPass={false}>
        <N8AO aoRadius={0.5} intensity={1} />
        <Bloom luminanceThreshold={1} intensity={0.5} levels={9} mipmapBlur />
      </EffectComposer>
    </>
  );
}
export default PostProcessing;
