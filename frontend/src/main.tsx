import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App.tsx';
import './index.css';
import { RouterProvider, createBrowserRouter } from 'react-router-dom';
import { SettingPage } from './pages/index.ts';
import { ThemeProvider, createTheme } from '@mui/material/styles';

const router = createBrowserRouter([
  {
    path: '/',
    element: <App />,
  },
  {
    path: 'setting',
    element: <SettingPage />,
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
    <ThemeProvider theme={theme}>
      <RouterProvider router={router} />
    </ThemeProvider>
  </React.StrictMode>
);
