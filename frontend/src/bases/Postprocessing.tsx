import { Bloom, EffectComposer, N8AO } from '@react-three/postprocessing';
import useGraphicsQualityStore from '@shared/store/useGraphicsQualityStore';

function PostProcessing() {
  const { ambientOcclusion, effectQuality } = useGraphicsQualityStore();
  return (
    <>
      <EffectComposer enableNormalPass={false}>
        {ambientOcclusion && <N8AO aoRadius={0.5} intensity={1} />}
        {effectQuality === 'high' && (
          <Bloom luminanceThreshold={1} intensity={0.5} levels={9} mipmapBlur />
        )}
      </EffectComposer>
    </>
  );
}
export default PostProcessing;
