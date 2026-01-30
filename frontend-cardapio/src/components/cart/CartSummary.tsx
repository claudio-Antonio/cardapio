import React from 'react';
import Button from '../ui/Button';
import { formatPrice } from '../../utils/formatters';

interface CartSummaryProps {
  totalItems: number;
  totalPrice: number;
  onCheckout: () => void;
  isLoading?: boolean;
}

const CartSummary: React.FC<CartSummaryProps> = ({
  totalItems,
  totalPrice,
  onCheckout,
  isLoading = false,
}) => {
  return (
    <div className="bg-gray-50 p-6 rounded-lg">
      <h3 className="text-lg font-semibold mb-4">Resumo do Pedido</h3>
      
      <div className="space-y-3 mb-6">
        <div className="flex justify-between">
          <span>Itens:</span>
          <span>{totalItems}</span>
        </div>
        
        <div className="flex justify-between text-xl font-bold">
          <span>Total:</span>
          <span className="text-red-600">{formatPrice(totalPrice)}</span>
        </div>
      </div>
      
      <Button
        onClick={onCheckout}
        variant="primary"
        className="w-full"
        disabled={totalItems === 0 || isLoading}
        isLoading={isLoading}
      >
        Finalizar Pedido
      </Button>
      
      <p className="text-sm text-gray-500 text-center mt-4">
        * Impostos e taxas inclusos
      </p>
    </div>
  );
};

export default CartSummary;