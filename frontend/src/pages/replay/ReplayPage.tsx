import { Container } from '@mui/material';
import { ReplayBar } from '@entity/index.ts';

export function ReplayPage() {
  return (
    <>
      <main>
        <Container fixed>
          <ReplayBar />
        </Container>
      </main>
    </>
  );
}
