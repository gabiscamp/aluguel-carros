import { useState, useEffect } from 'react';
import { useAuth } from '../contexts/AuthContext';
import { pedidoService } from '../services/pedidoService';
import { contratoService } from '../services/contratoService';
import { automovelService } from '../services/automovelService';
import './DashboardPage.css';

export default function DashboardPage() {
  const { user } = useAuth();
  const [stats, setStats] = useState({
    pedidos: 0,
    pedidosPendentes: 0,
    contratos: 0,
    automoveis: 0,
  });
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadStats();
  }, []);

  const loadStats = async () => {
    try {
      const isAgente = user?.tipo === 'AGENTE';
      const [pedidosRes, contratosRes, automoveisRes] = await Promise.all([
        isAgente ? pedidoService.getAll() : pedidoService.getMeus(),
        contratoService.getAll(),
        automovelService.getAll(),
      ]);
      const pedidos = Array.isArray(pedidosRes.data) ? pedidosRes.data : [];
      const contratos = Array.isArray(contratosRes.data) ? contratosRes.data : [];
      const automoveis = Array.isArray(automoveisRes.data) ? automoveisRes.data : [];
      setStats({
        pedidos: pedidos.length,
        pedidosPendentes: pedidos.filter((p) => p.status === 'PENDENTE' || p.status === 'EM_ANALISE').length,
        contratos: contratos.length,
        automoveis: automoveis.length,
      });
    } catch {
      // silently fail on dashboard
    } finally {
      setLoading(false);
    }
  };

  if (loading) return <div className="spinner" />;

  return (
    <div className="dashboard">
      <div className="welcome-banner">
        <div className="welcome-content">
          <h2>Bem-vindo(a) de volta! 👋</h2>
          <p>
            {user?.tipo === 'CLIENTE'
              ? 'Gerencie seus pedidos de aluguel e acompanhe seus contratos.'
              : 'Gerencie automóveis, avalie pedidos e administre contratos.'}
          </p>
        </div>
        <div className="welcome-decoration">🚗</div>
      </div>
      <div className="stats-grid">
        <div className="stat-card">
          <div className="stat-accent stat-accent-blue"></div>
          <div className="stat-body">
            <div className="stat-icon stat-icon-blue">📋</div>
            <div className="stat-info">
              <span className="stat-number">{stats.pedidos}</span>
              <span className="stat-label">Total de Pedidos</span>
            </div>
          </div>
        </div>
        <div className="stat-card">
          <div className="stat-accent stat-accent-yellow"></div>
          <div className="stat-body">
            <div className="stat-icon stat-icon-yellow">⏳</div>
            <div className="stat-info">
              <span className="stat-number">{stats.pedidosPendentes}</span>
              <span className="stat-label">Pedidos Pendentes</span>
            </div>
          </div>
        </div>
        <div className="stat-card">
          <div className="stat-accent stat-accent-green"></div>
          <div className="stat-body">
            <div className="stat-icon stat-icon-green">📄</div>
            <div className="stat-info">
              <span className="stat-number">{stats.contratos}</span>
              <span className="stat-label">Contratos Ativos</span>
            </div>
          </div>
        </div>
        <div className="stat-card">
          <div className="stat-accent stat-accent-purple"></div>
          <div className="stat-body">
            <div className="stat-icon stat-icon-purple">🚗</div>
            <div className="stat-info">
              <span className="stat-number">{stats.automoveis}</span>
              <span className="stat-label">Automóveis</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
