import React from 'react';
import type { Food } from '../../types/food';
import { useCart } from '../../hooks/useCart';
import Button from '../ui/Button';
import Card from '../ui/Card';
import { formatPrice } from '../../utils/formatters';

interface FoodCardProps {
  food: Food;
}

const FoodCard: React.FC<FoodCardProps> = ({ food }) => {
  const { addToCart } = useCart();

  const handleAddToCart = () => {
    addToCart(food);
  };

  return (
    <Card className="hover:shadow-lg transition-shadow duration-300">
      <div className="h-48 overflow-hidden">
        <img
          src={food.image}
          alt={food.title}
          className="w-full h-full object-cover hover:scale-105 transition-transform duration-300"
          onError={(e) => {
            (e.target as HTMLImageElement).src = 'https://via.placeholder.com/300x200?text=Food+Image';
          }}
        />
      </div>
      
      <div className="p-4">
        <h3 className="font-semibold text-lg mb-2 truncate">{food.title}</h3>
        <p className="text-red-600 font-bold text-xl mb-4">{formatPrice(food.price)}</p>
        
        <Button
          onClick={handleAddToCart}
          variant="primary"
          className="w-full"
        >
          Adicionar ao Carrinho
        </Button>
      </div>
    </Card>
  );
};

export default FoodCard;