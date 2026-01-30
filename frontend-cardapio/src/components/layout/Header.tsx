import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { FiShoppingCart, FiUser, FiLogOut, FiHome, FiPackage } from 'react-icons/fi';
import { useAuth } from '../../hooks/useAuth';
import { useCart } from '../../hooks/useCart';
import toast from 'react-hot-toast';

const Header: React.FC = () => {
  const { user, logout } = useAuth();
  const { getTotalItems } = useCart();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    toast.success('Logout realizado com sucesso!');
    navigate('/login');
  };

  return (
    <header className="bg-red-600 text-white shadow-lg sticky top-0 z-50">
      <div className="container mx-auto px-4 py-3">
        <div className="flex items-center justify-between">
          <Link to="/" className="flex items-center space-x-2 text-2xl font-bold hover:text-red-200 transition-colors">
            <FiHome />
            <span>FoodDelivery</span>
          </Link>

          <nav className="flex items-center space-x-4">
            {user ? (
              <>
                <Link to="/orders" className="flex items-center space-x-2 hover:text-red-200 transition-colors px-3 py-2 rounded">
                  <FiPackage />
                  <span className="hidden md:inline">Pedidos</span>
                </Link>

                <Link to="/profile" className="flex items-center space-x-2 hover:text-red-200 transition-colors px-3 py-2 rounded">
                  <FiUser />
                  <span className="hidden md:inline">{user.username}</span>
                </Link>

                {user.role === 'ADMIN' && (
                  <Link to="/admin/foods" className="flex items-center space-x-2 hover:text-red-200 transition-colors px-3 py-2 rounded">
                    <FiPackage />
                    <span className="hidden md:inline">Admin</span>
                  </Link>
                )}

                <Link to="/cart" className="relative flex items-center hover:text-red-200 transition-colors px-3 py-2 rounded">
                  <FiShoppingCart size={20} />
                  {getTotalItems() > 0 && (
                    <span className="absolute -top-1 -right-1 bg-yellow-500 text-xs text-white rounded-full h-5 w-5 flex items-center justify-center">
                      {getTotalItems()}
                    </span>
                  )}
                </Link>

                <button
                  onClick={handleLogout}
                  className="flex items-center space-x-2 hover:text-red-200 transition-colors px-3 py-2 rounded hover:bg-red-700"
                >
                  <FiLogOut />
                  <span className="hidden md:inline">Sair</span>
                </button>
              </>
            ) : (
              <>
                <Link to="/login" className="hover:text-red-200 transition-colors px-3 py-2 rounded hover:bg-red-700">
                  Login
                </Link>
                <Link to="/register" className="bg-white text-red-600 hover:bg-red-50 transition-colors px-4 py-2 rounded font-medium">
                  Registrar
                </Link>
              </>
            )}
          </nav>
        </div>
      </div>
    </header>
  );
};

export default Header;