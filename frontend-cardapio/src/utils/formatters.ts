export const formatPrice = (price: number): string => {
  return new Intl.NumberFormat('pt-BR', {
    style: 'currency',
    currency: 'BRL',
  }).format(price);
};

export const formatDate = (dateString: string): string => {
  const date = new Date(dateString);
  return new Intl.DateTimeFormat('pt-BR', {
    dateStyle: 'short',
    timeStyle: 'short',
  }).format(date);
};

export const getStatusColor = (status: string): string => {
  const colors: Record<string, string> = {
    PENDENTE: 'bg-orange-100 text-orange-800',
    EM_PREPARO: 'bg-blue-100 text-blue-800',
    PRONTO: 'bg-green-100 text-green-800',
    ENTREGUE: 'bg-gray-100 text-gray-800',
    CANCELADO: 'bg-red-100 text-red-800',
  };
  
  return colors[status] || 'bg-gray-100 text-gray-800';
};