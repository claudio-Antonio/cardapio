import type { UserResponseDTO } from './user';
import type { Food } from './food';

export type StatusRequest = 
  | 'PENDENTE' 
  | 'EM_PREPARO' 
  | 'PRONTO' 
  | 'ENTREGUE' 
  | 'CANCELADO';

export interface Order {
  id: number;
  dataOrder: string;
  status: StatusRequest;
  total: number;
  user: UserResponseDTO;
  foods: Food[];
}

export interface OrderRequestDTO {
  total: number;
  food: Array<{ id: number }>;
}

export interface OrderResponseDTO {
  id: number;
  dataOrder: string;
  status: StatusRequest;
  total: number;
  user: UserResponseDTO;
  food: Food[];
}