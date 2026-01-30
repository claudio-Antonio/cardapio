import React, { createContext, useContext, useState, useEffect } from 'react';
import type { ReactNode } from 'react';
import type {UserResponseDTO } from '../types/user';
import { authService } from '../services/auth.service';
import toast from 'react-hot-toast';

interface AuthContextType {
  user: UserResponseDTO | null;
  login: (username: string, password: string) => Promise<void>;
  register: (username: string, password: string, role: 'ADMIN' | 'USER') => Promise<void>;
  logout: () => void;
  isLoading: boolean;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};

interface AuthProviderProps {
  children: ReactNode;
}

export const AuthProvider: React.FC<AuthProviderProps> = ({ children }) => {
  const [user, setUser] = useState<UserResponseDTO | null>(null);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const storedUsername = localStorage.getItem('username');
    const storedUser = localStorage.getItem('user');

    if (storedUsername && storedUser) {
      try {
        setUser(JSON.parse(storedUser));
      } catch (error) {
        localStorage.removeItem('username');
        localStorage.removeItem('password');
        localStorage.removeItem('user');
      }
    }
    setIsLoading(false);
  }, []);

  const login = async (username: string, password: string) => {
    try {
      const response = await authService.login({ username, password });
      
      localStorage.setItem('username', username);
      localStorage.setItem('password', password);
      localStorage.setItem('user', JSON.stringify(response.user));
      
      setUser(response.user);
      
      toast.success('Login realizado com sucesso!');
    } catch (error) {
      toast.error('Erro ao fazer login');
      throw error;
    }
  };

  const register = async (username: string, password: string, role: 'ADMIN' | 'USER') => {
    try {
      const response = await authService.register({ username, password, role });
      
      localStorage.setItem('username', username);
      localStorage.setItem('password', password);
      localStorage.setItem('user', JSON.stringify(response.user));
      
      setUser(response.user);
      
      toast.success('Registro realizado com sucesso!');
    } catch (error) {
      toast.error('Erro ao registrar');
      throw error;
    }
  };

  const logout = () => {
    localStorage.removeItem('username');
    localStorage.removeItem('password');
    localStorage.removeItem('user');
    setUser(null);
  };

  return (
    <AuthContext.Provider value={{ user, login, register, logout, isLoading }}>
      {children}
    </AuthContext.Provider>
  );
};