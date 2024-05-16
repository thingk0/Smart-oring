import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App.tsx';
import './index.css';
import { RouterProvider, createBrowserRouter } from 'react-router-dom';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';

import { ReplayPage, SettingPage } from './pages';
import { SettingPage } from './pages/index.ts';
import { ThemeProvider, createTheme } from '@mui/material/styles';

<<<<<<< HEAD
const queryClient = new QueryClient();

=======
>>>>>>> 6970afc22f2c4847605de52cddb3c2573bc0f74b
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
    h3: {
      fontSize: 24,
      fontWeight: 'bold',
    },
    h2: {
      marginBottom: '20px',
      fontSize: 20,
      fontWeight: 'bold',
    },
    h4: {
      fontSize: 20,
      fontWeight: 600,
    },
  },
});

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
<<<<<<< HEAD
    <QueryClientProvider client={queryClient}>
      <ThemeProvider theme={theme}>
        <RouterProvider router={router} />
      </ThemeProvider>
    </QueryClientProvider>
=======
    <ThemeProvider theme={theme}>
      <RouterProvider router={router} />
    </ThemeProvider>
>>>>>>> 6970afc22f2c4847605de52cddb3c2573bc0f74b
  </React.StrictMode>
);
