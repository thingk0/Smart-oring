import axios from 'axios';

// robot movement mocking-api
const instanceAPI = axios.create();

// instanceAPI.interceptors.request(() => {});
// instanceAPI.interceptors.response(() => {});

export const robotMockingAPI = (id: number) =>
  axios.patch(`http://localhost:3001/robot/${id}`, {
    position: [0, 0],
  });
