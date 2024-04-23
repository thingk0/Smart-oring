import { Suspense, useEffect } from 'react';

function Map({ resource }: { resource: unknown }) {
  const tmp = resource();
  return <>{tmp}</>;
}

export default Map;
