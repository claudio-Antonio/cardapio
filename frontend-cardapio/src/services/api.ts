import axios from 'axios';
import toast from 'react-hot-toast';

const API_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080';

const api = axios.create({
  baseURL: API_URL,
  headers: {
    'Content-Type': 'application/json',
  },
  withCredentials: true,
});

// Interceptor para adicionar Basic Auth a todas as requisições
api.interceptors.request.use(
  (config) => {
    const username = localStorage.getItem('username');
    const password = localStorage.getItem('password');
    
    if (username && password && config.headers) {
      const credentials = btoa(`${username}:${password}`);
      config.headers.Authorization = `Basic ${credentials}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Interceptor para tratar erros globalmente
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      localStorage.removeItem('username');
      localStorage.removeItem('password');
      localStorage.removeItem('user');
      window.location.href = '/login';
      toast.error('Sessão expirada. Faça login novamente.');
    }
    
    if (error.response?.status === 403) {
      toast.error('Acesso negado. Permissão insuficiente.');
    }
    
    if (error.response?.data?.message) {
      toast.error(error.response.data.message);
    } else {
      toast.error('Erro ao processar requisição');
    }
    
    return Promise.reject(error);
  }
);

export default api;