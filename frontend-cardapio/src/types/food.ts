export interface Food {
  id: number;
  title: string;
  image: string;
  price: number;
}

export interface FoodResponseDTO extends Food {}