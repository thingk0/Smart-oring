import { Container } from '@mui/material';
import { ReplaySlider } from '@entity/index';
import { PlayButton } from '@entity/replay/PlayButton';

export function ReplayPage() {
  return (
    <>
      <main>
        <Container fixed>
          <ReplaySlider />
          <PlayButton />
        </Container>
      </main>
    </>
  );
}
