import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App.tsx';
import './index.css';
import { RouterProvider, createBrowserRouter } from 'react-router-dom';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
const queryClient = new QueryClient();
import { ReplayPage, SettingPage } from './pages';
const router = createBrowserRouter([
  {
    path: '/',
    element: <App />,
  },
  {
    path: 'setting',
    element: <SettingPage />,
  },
  {
    path: 'replay',
    element: <ReplayPage />,
  },
]);
ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <QueryClientProvider client={queryClient}>
      <RouterProvider router={router} />
    </QueryClientProvider>
  </React.StrictMode>
);
