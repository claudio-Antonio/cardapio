import api from './api';
import type { Order, OrderRequestDTO, OrderResponseDTO } from '../types/order';

const normalizeOrder = (resp: OrderResponseDTO | any): Order => {
  return {
    id: resp.id,
    dataOrder: typeof resp.dataOrder === 'string' ? resp.dataOrder : resp.dataOrder?.toString(),
    status: resp.status as Order['status'],
    total: resp.total,
    user: resp.user,
    foods: resp.foods ?? resp.food ?? [],
  };
};

export const orderService = {
  async create(orderData: OrderRequestDTO): Promise<Order> {
    try {
      const response = await api.post('/orders', orderData);
      return normalizeOrder(response.data);
    } catch (error) {
      throw error;
    }
  },

  async getAll(): Promise<Order[]> {
    try {
      const response = await api.get('/orders');
      return (response.data ?? []).map((r: OrderResponseDTO | any) => normalizeOrder(r));
    } catch (error) {
      throw error;
    }
  },

  async getById(id: number): Promise<Order> {
    try {
      const response = await api.get(`/orders/${id}`);
      return normalizeOrder(response.data);
    } catch (error) {
      throw error;
    }
  },

  async updateStatus(id: number, status: string): Promise<Order> {
    try {
      const response = await api.patch(`/orders/${id}/status`, { status });
      return normalizeOrder(response.data);
    } catch (error) {
      throw error;
    }
  },

  // ✅ ADICIONE ESTA FUNÇÃO
  async cancel(id: number): Promise<void> {
    try {
      await api.delete(`/orders/${id}`);
    } catch (error) {
      throw error;
    }
  },
};