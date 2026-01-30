import React, { useEffect, useState } from 'react';
import { useAuth } from '../hooks/useAuth';
import { orderService } from '../services/order.service';
import type { Order } from '../types/order';
import Card from '../components/ui/Card';
import { formatDate, getStatusColor, formatPrice } from '../utils/formatters';
import { FiClock, FiCheckCircle, FiTruck, FiXCircle } from 'react-icons/fi';
import toast from 'react-hot-toast';

const Profile: React.FC = () => {
  const { user } = useAuth();
  const [orders, setOrders] = useState<Order[]>([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    if (user) {
      loadOrders();
    }
  }, [user]);

  const loadOrders = async () => {
    try {
      setIsLoading(true);
      const data = await orderService.getAll();
      // Filter orders by current user
      const userOrders = data.filter(order => order.user.id === user?.id);
      setOrders(userOrders.sort((a, b) => new Date(b.dataOrder).getTime() - new Date(a.dataOrder).getTime()));
    } catch (error) {
      toast.error('Erro ao carregar histórico de pedidos');
    } finally {
      setIsLoading(false);
    }
  };

  const getStatusIcon = (status: string) => {
    switch (status) {
      case 'PENDENTE':
        return <FiClock className="text-orange-500" />;
      case 'EM_PREPARO':
        return <FiClock className="text-blue-500" />;
      case 'PRONTO':
        return <FiCheckCircle className="text-green-500" />;
      case 'ENTREGUE':
        return <FiTruck className="text-gray-500" />;
      case 'CANCELADO':
        return <FiXCircle className="text-red-500" />;
      default:
        return <FiClock className="text-gray-500" />;
    }
  };

  if (isLoading) {
    return (
      <div className="container mx-auto px-4 py-8">
        <div className="animate-pulse space-y-4">
          {[...Array(3)].map((_, i) => (
            <div key={i} className="h-32 bg-gray-200 rounded-lg"></div>
          ))}
        </div>
      </div>
    );
  }

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-gray-800 mb-2">Minha Conta</h1>
        <div className="bg-white rounded-lg shadow p-6">
          <h2 className="text-xl font-semibold mb-4">Informações do Usuário</h2>
          <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
            <div>
              <p className="font-medium">Usuário:</p>
              <p className="text-gray-600">{user?.username}</p>
            </div>
            <div>
              <p className="font-medium">Tipo de Conta:</p>
              <p className="text-gray-600">
                {user?.role === 'ADMIN' ? 'Administrador' : 'Usuário'}
              </p>
            </div>
          </div>
        </div>
      </div>

      <div>
        <h2 className="text-2xl font-bold text-gray-800 mb-4">Histórico de Pedidos</h2>
        
        {orders.length === 0 ? (
          <Card className="p-8 text-center">
            <p className="text-gray-600">Nenhum pedido realizado ainda.</p>
          </Card>
        ) : (
          <div className="space-y-4">
            {orders.map((order) => (
              <Card key={order.id} className="p-6">
                <div className="flex justify-between items-start mb-4">
                  <div>
                    <h3 className="font-semibold text-lg">Pedido #{order.id}</h3>
                    <p className="text-gray-600 text-sm">{formatDate(order.dataOrder)}</p>
                  </div>
                  <div className="flex items-center space-x-2">
                    {getStatusIcon(order.status)}
                    <span className={`px-3 py-1 rounded-full text-sm font-medium ${getStatusColor(order.status)}`}>
                      {order.status}
                    </span>
                  </div>
                </div>

                <div className="mb-4">
                  <h4 className="font-medium mb-2">Itens:</h4>
                  <div className="space-y-2">
                    {order.foods.map((food) => (
                      <div key={food.id} className="flex justify-between text-sm">
                        <span>{food.title}</span>
                        <span>{formatPrice(food.price)}</span>
                      </div>
                    ))}
                  </div>
                </div>

                <div className="border-t pt-4 flex justify-between items-center">
                  <div>
                    <p className="text-sm text-gray-600">Total do pedido:</p>
                    <p className="text-xl font-bold text-red-600">{formatPrice(order.total)}</p>
                  </div>
                  <button
                    onClick={() => toast('Funcionalidade em desenvolvimento')}
                    className="text-red-600 hover:text-red-700 font-medium"
                  >
                    Ver Detalhes
                  </button>
                </div>
              </Card>
            ))}
          </div>
        )}
      </div>
    </div>
  );
};

export default Profile;