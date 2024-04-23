import { Suspense } from 'react';
import Boxes from '../components/Boxes';
import Floor from '../components/Floor';
import Map from '../components/Map';
import axios from 'axios';

function LoadMapData() {
  const suspender = axios.get('http://localhost:3001/map').then(({ data }) => {
    console.log(data);
    return data;
  });
  return suspender;
}
// update mesh in this function
function Mesh() {
  return (
    <>
      <Suspense>
        <Map resource={LoadMapData} />
      </Suspense>

      <Floor />
    </>
  );
}

export default Mesh;
