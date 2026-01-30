import React from 'react';

const Footer: React.FC = () => {
  return (
    <footer className="bg-gray-800 text-white py-8 mt-auto">
      <div className="container mx-auto px-4">
        <div className="text-center">
          <p className="text-xl font-bold mb-2">FoodDelivery</p>
          <p className="text-gray-400 mb-4">Sistema de cardápio online</p>
          <p className="text-gray-500 text-sm">
            &copy; {new Date().getFullYear()} FoodDelivery. Todos os direitos reservados.
          </p>
          <div className="flex justify-center space-x-4 mt-4 text-gray-400 text-sm">
            <a href="#" className="hover:text-white transition-colors">Termos de Uso</a>
            <a href="#" className="hover:text-white transition-colors">Política de Privacidade</a>
            <a href="#" className="hover:text-white transition-colors">Contato</a>
          </div>
        </div>
      </div>
    </footer>
  );
};

export default Footer;