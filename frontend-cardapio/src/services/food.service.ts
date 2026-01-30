import api from './api';
import type { Food } from '../types/food';

export const foodService = {
  async getAll(): Promise<Food[]> {
    try {
      const response = await api.get('/foods');
      return response.data;
    } catch (error) {
      throw error;
    }
  },

  async create(food: Omit<Food, 'id'>): Promise<Food> {
    try {
      const response = await api.post('/foods', food);
      return response.data;
    } catch (error) {
      throw error;
    }
  },

  async update(id: number, food: Partial<Food>): Promise<Food> {
    try {
      const response = await api.put(`/foods/${id}`, food);
      return response.data;
    } catch (error) {
      throw error;
    }
  },

  async delete(id: number): Promise<void> {
    try {
      await api.delete(`/foods/${id}`);
    } catch (error) {
      throw error;
    }
  },
};