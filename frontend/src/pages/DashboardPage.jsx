import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';
import { pedidoService } from '../services/pedidoService';
import { contratoService } from '../services/contratoService';
import { automovelService } from '../services/automovelService';
import './DashboardPage.css';

export default function DashboardPage() {
  const { user } = useAuth();
  const navigate = useNavigate();
  const isAgente = user?.tipo === 'AGENTE';
  const [stats, setStats] = useState({
    pedidos: 0,
    pedidosPendentes: 0,
    pedidosEmAnalise: 0,
    pedidosAprovados: 0,
    contratos: 0,
    automoveis: 0,
  });
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadStats();
  }, []);

  const loadStats = async () => {
    try {
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
        pedidosPendentes: pedidos.filter((p) => p.status === 'PENDENTE').length,
        pedidosEmAnalise: pedidos.filter((p) => p.status === 'EM_ANALISE').length,
        pedidosAprovados: pedidos.filter((p) => p.status === 'APROVADO').length,
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

  const userName = user?.nome || user?.razaoSocial || user?.email?.split('@')[0] || 'Usuário';

  return (
    <div className="dashboard">
      {/* Welcome Banner */}
      <div className="welcome-banner">
        <div className="welcome-content">
          <h2>Bem-vindo(a), {userName}!</h2>
          <p>
            {isAgente
              ? 'Gerencie a frota, avalie pedidos e administre contratos.'
              : 'Alugue o carro ideal para você. Acompanhe seus pedidos e contratos.'}
          </p>
          {isAgente && <span className="role-badge">Agente</span>}
        </div>
        <div className="welcome-decoration">🚗</div>
      </div>

      {/* How it works / Management Panel */}
      <div className="steps-section">
        <h3 className="section-title">
          {isAgente ? 'Painel de Gestão' : 'Como funciona?'}
        </h3>
        <div className="steps-grid">
          {isAgente ? (
            <>
              <div className="step-card">
                <div className="step-number">1</div>
                <div className="step-icon">🚘</div>
                <h4>Gerencie automóveis</h4>
                <p>Cadastre e gerencie a frota de veículos disponíveis para aluguel.</p>
              </div>
              <div className="step-card">
                <div className="step-number">2</div>
                <div className="step-icon">📋</div>
                <h4>Avalie pedidos</h4>
                <p>Analise financeiramente os pedidos recebidos dos clientes.</p>
              </div>
              <div className="step-card">
                <div className="step-number">3</div>
                <div className="step-icon">📄</div>
                <h4>Controle contratos</h4>
                <p>Acompanhe contratos ativos e associe condições de crédito.</p>
              </div>
            </>
          ) : (
            <>
              <div className="step-card">
                <div className="step-number">1</div>
                <div className="step-icon">👤</div>
                <h4>Complete seu perfil</h4>
                <p>Preencha seus dados pessoais e cadastre seus empregadores.</p>
              </div>
              <div className="step-card">
                <div className="step-number">2</div>
                <div className="step-icon">🚘</div>
                <h4>Escolha um automóvel</h4>
                <p>Navegue pelos carros disponíveis e encontre o ideal para você.</p>
              </div>
              <div className="step-card">
                <div className="step-number">3</div>
                <div className="step-icon">📋</div>
                <h4>Faça seu pedido</h4>
                <p>Solicite o aluguel e acompanhe o status do seu pedido.</p>
              </div>
            </>
          )}
        </div>
      </div>

      {/* Stats Grid */}
      <div className="stats-grid">
        {isAgente ? (
          <>
            <div className="stat-card stat-border-blue">
              <div className="stat-body">
                <div className="stat-icon stat-icon-blue">📋</div>
                <div className="stat-info">
                  <span className="stat-number">{stats.pedidos}</span>
                  <span className="stat-label">Total de Pedidos</span>
                </div>
              </div>
            </div>
            <div className="stat-card stat-border-yellow">
              <div className="stat-body">
                <div className="stat-icon stat-icon-yellow">⏳</div>
                <div className="stat-info">
                  <span className="stat-number">{stats.pedidosPendentes}</span>
                  <span className="stat-label">Pendentes</span>
                </div>
              </div>
            </div>
            <div className="stat-card stat-border-orange">
              <div className="stat-body">
                <div className="stat-icon stat-icon-orange">🔍</div>
                <div className="stat-info">
                  <span className="stat-number">{stats.pedidosEmAnalise}</span>
                  <span className="stat-label">Em Análise</span>
                </div>
              </div>
            </div>
            <div className="stat-card stat-border-green">
              <div className="stat-body">
                <div className="stat-icon stat-icon-green">📄</div>
                <div className="stat-info">
                  <span className="stat-number">{stats.contratos}</span>
                  <span className="stat-label">Contratos</span>
                </div>
              </div>
            </div>
          </>
        ) : (
          <>
            <div className="stat-card stat-border-blue">
              <div className="stat-body">
                <div className="stat-icon stat-icon-blue">📋</div>
                <div className="stat-info">
                  <span className="stat-number">{stats.pedidos}</span>
                  <span className="stat-label">Meus Pedidos</span>
                </div>
              </div>
            </div>
            <div className="stat-card stat-border-green">
              <div className="stat-body">
                <div className="stat-icon stat-icon-green">✅</div>
                <div className="stat-info">
                  <span className="stat-number">{stats.pedidosAprovados}</span>
                  <span className="stat-label">Pedidos Aprovados</span>
                </div>
              </div>
            </div>
            <div className="stat-card stat-border-purple">
              <div className="stat-body">
                <div className="stat-icon stat-icon-purple">📄</div>
                <div className="stat-info">
                  <span className="stat-number">{stats.contratos}</span>
                  <span className="stat-label">Contratos Ativos</span>
                </div>
              </div>
            </div>
            <div className="stat-card stat-border-yellow">
              <div className="stat-body">
                <div className="stat-icon stat-icon-yellow">🚗</div>
                <div className="stat-info">
                  <span className="stat-number">{stats.automoveis}</span>
                  <span className="stat-label">Automóveis</span>
                </div>
              </div>
            </div>
          </>
        )}
      </div>

      {/* Quick Actions */}
      <div className="quick-actions">
        <h3 className="section-title">Ações Rápidas</h3>
        <div className="actions-row">
          {isAgente ? (
            <>
              <button className="action-btn action-btn-primary" onClick={() => navigate('/automoveis')}>
                🚘 Cadastrar Automóvel
              </button>
              <button className="action-btn action-btn-warning" onClick={() => navigate('/pedidos')}>
                📋 Ver Pedidos Pendentes
              </button>
              <button className="action-btn action-btn-success" onClick={() => navigate('/contratos')}>
                📄 Ver Contratos
              </button>
            </>
          ) : (
            <>
              <button className="action-btn action-btn-primary" onClick={() => navigate('/pedidos')}>
                📋 Novo Pedido
              </button>
              <button className="action-btn action-btn-success" onClick={() => navigate('/automoveis')}>
                🚘 Ver Automóveis
              </button>
              <button className="action-btn action-btn-purple" onClick={() => navigate('/perfil')}>
                👤 Meu Perfil
              </button>
            </>
          )}
        </div>
      </div>
    </div>
  );
}
