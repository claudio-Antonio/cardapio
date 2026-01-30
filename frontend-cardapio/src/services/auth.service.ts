import api from './api';
import type { AuthenticationDTO, RegisterDTO, UserResponseDTO } from '../types/user';

export const authService = {
  async login(credentials: AuthenticationDTO): Promise<{ user: UserResponseDTO }> {
    try {
      const response = await api.post('/auth/login', credentials);
      return { user: response.data }; 
    } catch (error) {
      throw error;
    }
  },

  async register(userData: RegisterDTO): Promise<{ user: UserResponseDTO }> {
    try {
      const response = await api.post('/auth/register', userData);
      return { user: response.data }; 
    } catch (error) {
      throw error;
    }
  },

  async getProfile(): Promise<UserResponseDTO> {
    try {
      const response = await api.get('/auth/profile');
      return response.data;
    } catch (error) {
      throw error;
    }
  },
};