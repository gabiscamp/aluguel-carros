import { NavLink } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';
import './Sidebar.css';

export default function Sidebar({ isOpen, onClose }) {
  const { user } = useAuth();
  const isCliente = user?.tipo === 'CLIENTE';

  const clienteLinks = [
    { to: '/dashboard', label: 'Dashboard', icon: '📊' },
    { to: '/perfil', label: 'Meu Perfil', icon: '👤' },
    { to: '/automoveis', label: 'Automóveis', icon: '🚘' },
    { to: '/pedidos', label: 'Meus Pedidos', icon: '📋' },
    { to: '/contratos', label: 'Meus Contratos', icon: '📄' },
  ];

  const agenteLinks = [
    { to: '/dashboard', label: 'Dashboard', icon: '📊' },
    { to: '/automoveis', label: 'Automóveis', icon: '🚘' },
    { to: '/pedidos', label: 'Pedidos', icon: '📋' },
    { to: '/contratos', label: 'Contratos', icon: '📄' },
  ];

  const links = isCliente ? clienteLinks : agenteLinks;

  return (
    <>
      {isOpen && <div className="sidebar-backdrop" onClick={onClose} />}
      <aside className={`sidebar ${isOpen ? 'sidebar-open' : ''}`}>
      <div className="sidebar-brand">
        <span className="sidebar-logo">🚗</span>
        <div>
          <div className="sidebar-title">AutoRent</div>
          <div className="sidebar-subtitle">Aluguel de Carros</div>
        </div>
      </div>
      <nav className="sidebar-nav">
        <span className="sidebar-nav-label">Menu Principal</span>
        {links.map((link) => (
          <NavLink
            key={link.to}
            to={link.to}
            onClick={onClose}
            className={({ isActive }) =>
              `sidebar-link ${isActive ? 'sidebar-link-active' : ''}`
            }
          >
            <span className="sidebar-icon">{link.icon}</span>
            <span>{link.label}</span>
          </NavLink>
        ))}
      </nav>
      <div className="sidebar-extras">
        <NavLink to="/design" onClick={onClose} className="sidebar-link sidebar-link-muted">
          <span className="sidebar-icon">📐</span>
          <span>Design System</span>
        </NavLink>
      </div>
      <div className="sidebar-footer">
        <small>AutoRent &copy; 2024 - Todos os direitos reservados</small>
      </div>
    </aside>
    </>
  );
}
