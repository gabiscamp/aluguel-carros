import { useAuth } from '../contexts/AuthContext';
import './Header.css';

export default function Header({ onMenuToggle }) {
  const { user, logout } = useAuth();

  const initials = user?.email?.charAt(0).toUpperCase() || '?';
  const isCliente = user?.tipo === 'CLIENTE';

  return (
    <header className="header">
      <div className="header-left">
        <button className="hamburger-btn" onClick={onMenuToggle} aria-label="Abrir menu">
          ☰
        </button>
        <h2>AutoRent</h2>
      </div>
      <div className="header-right">
        <div className="header-user">
          <div className="header-avatar">
            {initials}
          </div>
          <div className="header-user-info">
            <span className="header-email">{user?.email}</span>
            <span className={`header-role-badge ${isCliente ? 'role-cliente' : 'role-agente'}`}>
              {isCliente ? 'Cliente' : 'Agente'}
            </span>
          </div>
        </div>
        <button className="btn-logout" onClick={logout} title="Sair do sistema">
          ⏻
        </button>
      </div>
    </header>
  );
}
