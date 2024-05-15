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
    <ThemeProvider theme={theme}>
      <RouterProvider router={router} />
    </ThemeProvider>
  </React.StrictMode>
);
