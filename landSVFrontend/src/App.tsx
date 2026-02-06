import './App.css'
import React from "react";
import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom'
import { LoginPage } from './pages/LoginPage.tsx';
import { Protected } from './components/Protected.tsx';
import { ChatSocketProvider } from './context/chatSocketContext.tsx';
import { Toaster } from 'sonner';
import { Dashboard } from './pages/Dashboard.tsx';
import { RegisterPage } from './pages/RegisterPage.tsx';

const App: React.FC = () => {
  return (
    <div>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Navigate to={'/login'} replace />} />
          <Route path='/login' element={<LoginPage />} />
          <Route path="/register-page" element={<RegisterPage />} />
          <Route path='/dashboard/*' element={
            <Protected>
              <ChatSocketProvider>
                <Dashboard />
              </ChatSocketProvider>
            </Protected>
          } />
        </Routes>
      </BrowserRouter>
      <Toaster />
    </div>
  );
};

export default App
