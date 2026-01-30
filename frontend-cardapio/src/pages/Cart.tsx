import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useCart } from '../hooks/useCart';
import { useAuth } from '../hooks/useAuth';
import CartItem from '../components/cart/CartItem';
import CartSummary from '../components/cart/CartSummary';
import Button from '../components/ui/Button';
import { FiArrowLeft } from 'react-icons/fi';

const Cart: React.FC = () => {
  const { items, removeFromCart, updateQuantity, getTotalItems, getTotalPrice, clearCart } = useCart();
  const { user } = useAuth();
  const navigate = useNavigate();
  const [isCheckingOut] = useState(false);

  const handleCheckout = () => {
    if (!user) {
      navigate('/login');
      return;
    }
    navigate('/checkout');
  };

  if (items.length === 0) {
    return (
      <div className="container mx-auto px-4 py-12">
        <div className="text-center">
          <h2 className="text-2xl font-bold mb-4">Seu carrinho está vazio</h2>
          <p className="text-gray-600 mb-8">
            Adicione itens deliciosos do nosso cardápio!
          </p>
          <Button
            onClick={() => navigate('/')}
            variant="primary"
            className="flex items-center space-x-2 mx-auto"
          >
            <FiArrowLeft />
            <span>Voltar ao Cardápio</span>
          </Button>
        </div>
      </div>
    );
  }

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="mb-6">
        <h1 className="text-3xl font-bold text-gray-800">Meu Carrinho</h1>
        <p className="text-gray-600">{getTotalItems()} itens no carrinho</p>
      </div>

      <div className="lg:flex lg:space-x-8">
        <div className="lg:w-2/3">
          <div className="bg-white rounded-lg shadow p-6">
            {items.map((item) => (
              <CartItem
                key={item.id}
                item={item}
                onUpdateQuantity={updateQuantity}
                onRemove={removeFromCart}
              />
            ))}
            
            <div className="mt-6 pt-4 border-t">
              <Button
                onClick={clearCart}
                variant="outline"
              >
                Limpar Carrinho
              </Button>
            </div>
          </div>
        </div>

        <div className="lg:w-1/3 mt-6 lg:mt-0">
          <CartSummary
            totalItems={getTotalItems()}
            totalPrice={getTotalPrice()}
            onCheckout={handleCheckout}
            isLoading={isCheckingOut}
          />
        </div>
      </div>
    </div>
  );
};

export default Cart;