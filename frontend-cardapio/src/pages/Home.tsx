import React, { useEffect, useState } from 'react';
import FoodList from '../components/food/FoodList';
import { foodService } from '../services/food.service';
import type { Food } from '../types/food';
import { useAuth } from '../hooks/useAuth';
import toast from 'react-hot-toast';

const Home: React.FC = () => {
  const [foods, setFoods] = useState<Food[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const { user } = useAuth();

  useEffect(() => {
    loadFoods();
  }, []);

  const loadFoods = async () => {
    try {
      setIsLoading(true);
      const data = await foodService.getAll();
      setFoods(data);
    } catch (error) {
      toast.error('Erro ao carregar cardápio');
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-gray-800 mb-2">
          Cardápio Online
        </h1>
        {user && (
          <p className="text-gray-600">
            Bem-vindo, {user.username}! Faça seu pedido.
          </p>
        )}
      </div>

      <FoodList foods={foods} isLoading={isLoading} />
    </div>
  );
};

export default Home;