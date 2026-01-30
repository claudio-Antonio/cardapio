import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { orderService } from '../services/order.service';
import type { Order } from '../types/order';
import { formatPrice } from '../utils/formatters';
import Card from '../components/ui/Card';
import { FiArrowLeft, FiClock, FiCheckCircle } from 'react-icons/fi';
import Button from '../components/ui/Button';
import toast from 'react-hot-toast';

const Orders: React.FC = () => {
  const [orders, setOrders] = useState<Order[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [cancellingOrderId, setCancellingOrderId] = useState<number | null>(null); // ✅ ADICIONE
  const navigate = useNavigate();

  useEffect(() => {
    loadOrders();
  }, []);

  const loadOrders = async () => {
    try {
      setIsLoading(true);
      const data = await orderService.getAll();
      setOrders(data);
    } catch (error) {
      toast.error('Erro ao carregar pedidos');
    } finally {
      setIsLoading(false);
    }
  };

  // ✅ ADICIONE ESTA FUNÇÃO
  const handleCancelOrder = async (orderId: number) => {
    if (!window.confirm('Tem certeza que deseja cancelar este pedido?')) {
      return;
    }

    try {
      setCancellingOrderId(orderId);
      await orderService.cancel(orderId);
      toast.success('Pedido cancelado com sucesso!');
      // Recarrega a lista de pedidos
      await loadOrders();
    } catch (error) {
      toast.error('Erro ao cancelar pedido');
    } finally {
      setCancellingOrderId(null);
    }
  };

  const getStatusIcon = (status: string) => {
    switch (status) {
      case 'PENDENTE':
        return <FiClock className="text-yellow-500" />;
      case 'EM_PREPARO':
        return <FiClock className="text-blue-500" />;
      case 'PRONTO':
        return <FiCheckCircle className="text-green-500" />;
      case 'ENTREGUE':
        return <FiCheckCircle className="text-green-600" />;
      case 'CANCELADO':
        return <FiClock className="text-red-500" />;
      default:
        return null;
    }
  };

  const getStatusLabel = (status: string) => {
    const statusMap: Record<string, string> = {
      PENDENTE: 'Pendente',
      EM_PREPARO: 'Em Preparo',
      PRONTO: 'Pronto',
      ENTREGUE: 'Entregue',
      CANCELADO: 'Cancelado',
    };
    return statusMap[status] || status;
  };

  const getStatusColor = (status: string) => {
    const colorMap: Record<string, string> = {
      PENDENTE: 'bg-yellow-50 border-yellow-200',
      EM_PREPARO: 'bg-blue-50 border-blue-200',
      PRONTO: 'bg-green-50 border-green-200',
      ENTREGUE: 'bg-green-100 border-green-300',
      CANCELADO: 'bg-red-50 border-red-200',
    };
    return colorMap[status] || 'bg-gray-50 border-gray-200';
  };

  if (isLoading) {
    return (
      <div className="container mx-auto px-4 py-12">
        <div className="text-center">
          <div className="animate-spin rounded-full h-12 w-12 border-t-2 border-b-2 border-red-600 mx-auto"></div>
          <p className="text-gray-600 mt-4">Carregando pedidos...</p>
        </div>
      </div>
    );
  }

  if (orders.length === 0) {
    return (
      <div className="container mx-auto px-4 py-12">
        <div className="text-center">
          <h2 className="text-2xl font-bold mb-4">Nenhum pedido encontrado</h2>
          <p className="text-gray-600 mb-8">Você ainda não fez nenhum pedido.</p>
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
      <div className="mb-8">
        <button
          onClick={() => navigate('/')}
          className="flex items-center space-x-2 text-red-600 hover:text-red-700 font-medium mb-4"
        >
          <FiArrowLeft />
          <span>Voltar</span>
        </button>
        <h1 className="text-3xl font-bold text-gray-800">Meus Pedidos</h1>
        <p className="text-gray-600">Total de {orders.length} pedido(s)</p>
      </div>

      <div className="space-y-6">
        {orders.map((order) => (
          <Card key={order.id} className={`border-2 ${getStatusColor(order.status)}`}>
            <div className="flex items-start justify-between mb-4 pb-4 border-b">
              <div>
                <p className="text-sm text-gray-600">Pedido #{order.id}</p>
                <p className="text-sm text-gray-500">
                  {new Date(order.dataOrder).toLocaleDateString('pt-BR', {
                    year: 'numeric',
                    month: 'long',
                    day: 'numeric',
                    hour: '2-digit',
                    minute: '2-digit',
                  })}
                </p>
              </div>
              <div className="flex items-center space-x-2">
                {getStatusIcon(order.status)}
                <span className="font-semibold text-gray-700">
                  {getStatusLabel(order.status)}
                </span>
              </div>
            </div>

            <div className="mb-4">
              <h3 className="font-semibold text-gray-800 mb-2">Itens do Pedido:</h3>
              <div className="space-y-2">
                {order.foods.map((food) => (
                  <div key={food.id} className="flex justify-between text-sm">
                    <span className="text-gray-700">{food.title}</span>
                    <span className="text-gray-600">{formatPrice(food.price)}</span>
                  </div>
                ))}
              </div>
            </div>

            <div className="border-t pt-4 flex justify-between items-center">
              <div>
                <p className="text-sm text-gray-600">Total do pedido:</p>
                <p className="text-xl font-bold text-red-600">{formatPrice(order.total)}</p>
              </div>
              {order.status === 'PENDENTE' && (
                <Button 
                  variant="danger" 
                  className="text-sm"
                  onClick={() => handleCancelOrder(order.id)} // ✅ ADICIONE ESTA LINHA
                  disabled={cancellingOrderId === order.id} // ✅ ADICIONE ESTA LINHA
                >
                  {cancellingOrderId === order.id ? 'Cancelando...' : 'Cancelar Pedido'}
                </Button>
              )}
            </div>
          </Card>
        ))}
      </div>
    </div>
  );
};

export default Orders;