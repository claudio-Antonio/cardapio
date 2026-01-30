import React from 'react';
import { FiPlus, FiMinus, FiTrash2 } from 'react-icons/fi';
import type { CartItem as CartItemType } from '../../contexts/CartContext';
import { formatPrice } from '../../utils/formatters';

interface CartItemProps {
  item: CartItemType;
  onUpdateQuantity: (id: number, quantity: number) => void;
  onRemove: (id: number) => void;
}

const CartItem: React.FC<CartItemProps> = ({ item, onUpdateQuantity, onRemove }) => {
  const handleIncrease = () => {
    onUpdateQuantity(item.id, item.quantity + 1);
  };

  const handleDecrease = () => {
    if (item.quantity > 1) {
      onUpdateQuantity(item.id, item.quantity - 1);
    } else {
      onRemove(item.id);
    }
  };

  return (
    <div className="flex items-center border-b py-4">
      <div className="w-20 h-20 flex-shrink-0 overflow-hidden rounded-lg">
        <img
          src={item.image}
          alt={item.title}
          className="w-full h-full object-cover"
          onError={(e) => {
            (e.target as HTMLImageElement).src = 'https://via.placeholder.com/80x80?text=Food';
          }}
        />
      </div>
      
      <div className="ml-4 flex-grow">
        <h3 className="font-semibold">{item.title}</h3>
        <p className="text-red-600 font-bold">{formatPrice(item.price)}</p>
      </div>
      
      <div className="flex items-center space-x-2">
        <button
          onClick={handleDecrease}
          className="p-1 hover:bg-gray-100 rounded-full"
        >
          <FiMinus />
        </button>
        
        <span className="w-8 text-center font-medium">{item.quantity}</span>
        
        <button
          onClick={handleIncrease}
          className="p-1 hover:bg-gray-100 rounded-full"
        >
          <FiPlus />
        </button>
      </div>
      
      <div className="ml-4 text-right">
        <p className="font-bold">{formatPrice(item.price * item.quantity)}</p>
        <button
          onClick={() => onRemove(item.id)}
          className="text-red-500 hover:text-red-700 p-1 mt-1"
        >
          <FiTrash2 />
        </button>
      </div>
    </div>
  );
};

export default CartItem;