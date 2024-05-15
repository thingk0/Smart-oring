import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App.tsx';
import './index.css';
import { RouterProvider, createBrowserRouter } from 'react-router-dom';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';

import { ReplayPage, SettingPage } from './pages';
import { SettingPage } from './pages/index.ts';
import { ThemeProvider, createTheme } from '@mui/material/styles';

const queryClient = new QueryClient();

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

const theme = createTheme({
  typography: {
    fontFamily: ['Pretendard Variable', 'sans-serif'].join(','),
    h1: {
      fontFamily: ['Gasoek One', 'Pretendard Variable', 'sans-serif'].join(','),
      fontSize: 32,
      color: '#1F1E30',
    },
    h2: {
      marginBottom: '20px',
      fontSize: 20,
      fontWeight: 'bold',
    },
    h3: {},
    body1: {},
    button: {},
    // fontSize: 24,
    // fontWeight: 'bold',
  },
});

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <QueryClientProvider client={queryClient}>
      <ThemeProvider theme={theme}>
        <RouterProvider router={router} />
      </ThemeProvider>
    </QueryClientProvider>
  </React.StrictMode>
);
