export type UserIfoodRole = 'ADMIN' | 'USER';

export interface AuthenticationDTO {
  username: string;
  password: string;
}

export interface RegisterDTO {
  username: string;
  password: string;
  role: UserIfoodRole;
}

export interface UserResponseDTO {
  id: number;
  username: string;
  role: string;
}