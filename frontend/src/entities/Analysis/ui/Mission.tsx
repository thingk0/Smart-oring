import { Suspense } from 'react';
import Form from './Form';
import MissionList from './MissionList';
import MissionHistory from './MissionHistory';

function Mission() {
  return (
    <div>
      <Form />
      <MissionList />
      <MissionHistory />
    </div>
  );
}
export default Mission;
