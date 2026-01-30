import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useCart } from '../hooks/useCart';
import { useAuth } from '../hooks/useAuth';
import { orderService } from '../services/order.service';
import CartSummary from '../components/cart/CartSummary';
import Button from '../components/ui/Button';
import Card from '../components/ui/Card';
import { FiCheckCircle, FiArrowLeft } from 'react-icons/fi';
import toast from 'react-hot-toast';
import type { OrderRequestDTO, StatusRequest } from '../types/order';
import { formatPrice } from '../utils/formatters';

const Checkout: React.FC = () => {
  const { items, getTotalItems, getTotalPrice, clearCart } = useCart();
  const { user } = useAuth();
  const navigate = useNavigate();
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [orderCompleted, setOrderCompleted] = useState(false);
  const [orderId, setOrderId] = useState<number | null>(null);

const handleSubmitOrder = async () => {
  if (!user) {
    navigate('/login');
    return;
  }

  setIsSubmitting(true);
  try {
    const orderData: OrderRequestDTO = {
      // ❌ REMOVA: dataOrder
      // ❌ REMOVA: status
      total: getTotalPrice(),
      // ❌ REMOVA: user
      food: items.map(item => ({ id: item.id })),
    };

    const order = await orderService.create(orderData);
    setOrderId(order.id);
    clearCart();
    setOrderCompleted(true);
    toast.success('Pedido realizado com sucesso!');
  } catch (error) {
    console.error('Erro ao criar pedido:', error); // ✅ Para debug
    toast.error('Erro ao realizar pedido');
  } finally {
    setIsSubmitting(false);
  }
};

  if (orderCompleted) {
    return (
      <div className="container mx-auto px-4 py-12">
        <Card className="max-w-2xl mx-auto p-8">
          <div className="text-center">
            <FiCheckCircle className="text-green-500 text-6xl mx-auto mb-6" />
            <h2 className="text-2xl font-bold mb-4">Pedido Confirmado!</h2>
            <p className="text-gray-600 mb-2">
              Seu pedido #{orderId} foi realizado com sucesso.
            </p>
            <p className="text-gray-600 mb-8">
              Total: <span className="font-bold">{formatPrice(getTotalPrice())}</span>
            </p>
            <div className="space-y-4">
              <Button
                onClick={() => navigate('/profile')}
                variant="primary"
                className="w-full"
              >
                Ver Meus Pedidos
              </Button>
              <Button
                onClick={() => navigate('/')}
                variant="outline"
                className="w-full"
              >
                Continuar Comprando
              </Button>
            </div>
          </div>
        </Card>
      </div>
    );
  }

  if (items.length === 0) {
    return (
      <div className="container mx-auto px-4 py-12">
        <div className="text-center">
          <h2 className="text-2xl font-bold mb-4">Seu carrinho está vazio</h2>
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
        <h1 className="text-3xl font-bold text-gray-800">Finalizar Pedido</h1>
        <p className="text-gray-600">Revise seu pedido antes de confirmar</p>
      </div>

      <div className="lg:flex lg:space-x-8">
        <div className="lg:w-2/3">
          <Card className="p-6 mb-6">
            <h2 className="text-xl font-semibold mb-4">Resumo do Pedido</h2>
            <div className="space-y-4">
              {items.map((item) => (
                <div key={item.id} className="flex items-center justify-between py-2 border-b">
                  <div>
                    <p className="font-medium">{item.title}</p>
                    <p className="text-sm text-gray-600">
                      {item.quantity} x {formatPrice(item.price)}
                    </p>
                  </div>
                  <p className="font-bold">
                    {formatPrice(item.price * item.quantity)}
                  </p>
                </div>
              ))}
            </div>
          </Card>

          <Card className="p-6">
            <h2 className="text-xl font-semibold mb-4">Informações do Cliente</h2>
            <div className="space-y-2">
              <p>
                <span className="font-medium">Nome:</span>{' '}
                <span className="text-gray-600">{user?.username}</span>
              </p>
              <p>
                <span className="font-medium">Tipo:</span>{' '}
                <span className="text-gray-600">{user?.role === 'ADMIN' ? 'Administrador' : 'Usuário'}</span>
              </p>
              <p>
                <span className="font-medium">Data do Pedido:</span>{' '}
                <span className="text-gray-600">
                  {new Date().toLocaleDateString('pt-BR')}
                </span>
              </p>
            </div>
          </Card>
        </div>

        <div className="lg:w-1/3 mt-6 lg:mt-0">
          <CartSummary
            totalItems={getTotalItems()}
            totalPrice={getTotalPrice()}
            onCheckout={handleSubmitOrder}
            isLoading={isSubmitting}
          />
        </div>
      </div>
    </div>
  );
};

export default Checkout;