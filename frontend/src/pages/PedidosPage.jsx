import { useState, useEffect } from 'react';
import { useAuth } from '../contexts/AuthContext';
import { pedidoService } from '../services/pedidoService';
import { automovelService } from '../services/automovelService';
import './PedidosPage.css';

const statusBadge = (status) => {
  const map = {
    PENDENTE: 'badge-warning',
    EM_ANALISE: 'badge-info',
    APROVADO: 'badge-success',
    REJEITADO: 'badge-danger',
    CANCELADO: 'badge-neutral',
  };
  const labelMap = {
    PENDENTE: 'Pendente',
    EM_ANALISE: 'Em Análise',
    APROVADO: 'Aprovado',
    REJEITADO: 'Rejeitado',
    CANCELADO: 'Cancelado',
  };
  return <span className={`badge ${map[status] || 'badge-neutral'}`}>{labelMap[status] || status}</span>;
};

export default function PedidosPage() {
  const { user } = useAuth();
  const isAgente = user?.tipo === 'AGENTE';
  const [pedidos, setPedidos] = useState([]);
  const [automoveis, setAutomoveis] = useState([]);
  const [loading, setLoading] = useState(true);
  const [message, setMessage] = useState({ type: '', text: '' });

  // Create pedido
  const [showCreate, setShowCreate] = useState(false);
  const [createForm, setCreateForm] = useState({ automovelId: '', dataInicio: '', dataFim: '' });
  const [saving, setSaving] = useState(false);

  // Evaluate pedido
  const [showAvaliar, setShowAvaliar] = useState(null);
  const [parecerFinanceiro, setParecerFinanceiro] = useState('');

  // Approve pedido
  const [showAprovar, setShowAprovar] = useState(null);
  const [aprovarForm, setAprovarForm] = useState({ tipoContrato: 'CLIENTE', valorMensal: '', prazoMeses: '' });

  // Rejeitar pedido
  const [showRejeitar, setShowRejeitar] = useState(null);
  const [motivoRejeicao, setMotivoRejeicao] = useState('');

  const showMsg = (type, text) => {
    setMessage({ type, text });
    setTimeout(() => setMessage({ type: '', text: '' }), 4000);
  };

  useEffect(() => { loadData(); }, []);

  const loadData = async () => {
    try {
      const [pedidosRes, automoveisRes] = await Promise.all([
        isAgente ? pedidoService.getAll() : pedidoService.getMeus(),
        automovelService.getAll(),
      ]);
      setPedidos(Array.isArray(pedidosRes.data) ? pedidosRes.data : []);
      setAutomoveis(Array.isArray(automoveisRes.data) ? automoveisRes.data : []);
    } catch {
      showMsg('error', 'Erro ao carregar dados.');
    } finally {
      setLoading(false);
    }
  };

  const handleCreate = async () => {
    setSaving(true);
    setMessage({ type: '', text: '' });
    try {
      await pedidoService.create({
        ...createForm,
        automovelId: Number(createForm.automovelId)
      });
      showMsg('success', 'Pedido criado com sucesso!');
      setShowCreate(false);
      setCreateForm({ automovelId: '', dataInicio: '', dataFim: '' });
      loadData();
    } catch (err) {
      showMsg('error', err.response?.data?.message || 'Erro ao criar pedido.');
    } finally {
      setSaving(false);
    }
  };

  const handleAvaliar = async () => {
    setSaving(true);
    try {
      await pedidoService.avaliar(showAvaliar, parecerFinanceiro);
      showMsg('success', 'Pedido avaliado com sucesso!');
      setShowAvaliar(null);
      setParecerFinanceiro('');
      loadData();
    } catch (err) {
      showMsg('error', err.response?.data?.message || 'Erro ao avaliar.');
    } finally {
      setSaving(false);
    }
  };

  const handleAprovar = async () => {
    setSaving(true);
    try {
      await pedidoService.aprovar(showAprovar, {
        tipoContrato: aprovarForm.tipoContrato,
        valorMensal: Number(aprovarForm.valorMensal),
        prazoMeses: Number(aprovarForm.prazoMeses),
      });
      showMsg('success', 'Pedido aprovado com sucesso!');
      setShowAprovar(null);
      setAprovarForm({ tipoContrato: 'CLIENTE', valorMensal: '', prazoMeses: '' });
      loadData();
    } catch (err) {
      showMsg('error', err.response?.data?.message || 'Erro ao aprovar.');
    } finally {
      setSaving(false);
    }
  };

  const handleRejeitar = async () => {
    setSaving(true);
    try {
      await pedidoService.rejeitar(showRejeitar, motivoRejeicao);
      showMsg('success', 'Pedido rejeitado.');
      setShowRejeitar(null);
      setMotivoRejeicao('');
      loadData();
    } catch (err) {
      showMsg('error', err.response?.data?.message || 'Erro ao rejeitar.');
    } finally {
      setSaving(false);
    }
  };

  const handleCancelar = async (id) => {
    if (!window.confirm('Tem certeza que deseja cancelar este pedido?')) return;
    try {
      await pedidoService.cancelar(id);
      showMsg('success', 'Pedido cancelado.');
      loadData();
    } catch {
      showMsg('error', 'Erro ao cancelar pedido.');
    }
  };

  if (loading) return <div className="spinner" />;

  return (
    <div className="pedidos-page">
      <div className="page-header">
        <h1>{isAgente ? 'Pedidos' : 'Meus Pedidos'}</h1>
        {!isAgente && (
          <button className="btn btn-primary" onClick={() => { setMessage({ type: '', text: '' }); setShowCreate(true); }}>
            + Novo Pedido
          </button>
        )}
      </div>

      {pedidos.length === 0 ? (
        <div className="empty-state card">
          <span className="empty-state-icon">📋</span>
          <h3>Nenhum pedido encontrado</h3>
          <p>{isAgente ? 'Os pedidos dos clientes aparecerão aqui.' : 'Crie seu primeiro pedido de aluguel!'}</p>
        </div>
      ) : (
        <div className="table-container">
          <table>
            <thead>
              <tr>
                <th>ID</th>
                {isAgente && <th>Cliente</th>}
                <th>Automóvel</th>
                <th>Data Início</th>
                <th>Data Fim</th>
                <th>Status</th>
                <th>Ações</th>
              </tr>
            </thead>
            <tbody>
              {pedidos.map((p) => (
                <tr key={p.id}>
                  <td>#{p.id}</td>
                  {isAgente && <td>{p.clienteNome || p.clienteId || '-'}</td>}
                  <td>{p.automovelDescricao || p.automovelId || '-'}</td>
                  <td>{p.dataInicio || '-'}</td>
                  <td>{p.dataFim || '-'}</td>
                  <td>{statusBadge(p.status)}</td>
                  <td className="actions-cell">
                    {isAgente && p.status === 'PENDENTE' && (
                      <button className="btn btn-outline btn-sm" onClick={() => { setMessage({ type: '', text: '' }); setShowAvaliar(p.id); setParecerFinanceiro(''); }}>
                        Avaliar
                      </button>
                    )}
                    {isAgente && p.status === 'EM_ANALISE' && (
                      <>
                        <button className="btn btn-success btn-sm" onClick={() => { setMessage({ type: '', text: '' }); setShowAprovar(p.id); setAprovarForm({ tipoContrato: 'CLIENTE', valorMensal: '', prazoMeses: '' }); }}>
                          Aprovar
                        </button>
                        <button className="btn btn-danger btn-sm" onClick={() => { setMessage({ type: '', text: '' }); setShowRejeitar(p.id); setMotivoRejeicao(''); }}>
                          Rejeitar
                        </button>
                      </>
                    )}
                    {!isAgente && (p.status === 'PENDENTE' || p.status === 'EM_ANALISE') && (
                      <button className="btn btn-danger btn-sm" onClick={() => handleCancelar(p.id)}>
                        Cancelar
                      </button>
                    )}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      {message.text && (
        <div className={`alert alert-${message.type}`}>{message.text}</div>
      )}

      {/* Create Modal */}
      {showCreate && (
        <div className="modal-overlay" onClick={() => setShowCreate(false)}>
          <div className="modal" onClick={(e) => e.stopPropagation()}>
            <h2>Novo Pedido de Aluguel</h2>
            <div className="form-group">
              <label>Automóvel</label>
              <select value={createForm.automovelId} onChange={(e) => setCreateForm((f) => ({ ...f, automovelId: e.target.value }))}>
                <option value="">Selecione um automóvel</option>
                {automoveis.filter(a => a.disponivel).map((a) => (
                  <option key={a.id} value={a.id}>
                    {a.marca} {a.modelo} ({a.ano})
                  </option>
                ))}
              </select>
            </div>
            <div className="form-row">
              <div className="form-group">
                <label>Data Início</label>
                <input type="date" value={createForm.dataInicio} onChange={(e) => setCreateForm((f) => ({ ...f, dataInicio: e.target.value }))} />
              </div>
              <div className="form-group">
                <label>Data Fim</label>
                <input type="date" value={createForm.dataFim} onChange={(e) => setCreateForm((f) => ({ ...f, dataFim: e.target.value }))} />
              </div>
            </div>
            <div className="modal-actions">
              <button className="btn btn-outline" onClick={() => setShowCreate(false)}>Cancelar</button>
              <button className="btn btn-primary" onClick={handleCreate} disabled={saving}>
                {saving ? 'Criando...' : 'Criar Pedido'}
              </button>
            </div>
            {message.text && (
              <div className={`alert alert-${message.type}`}>{message.text}</div>
            )}
          </div>
        </div>
      )}

      {/* Avaliar Modal */}
      {showAvaliar && (
        <div className="modal-overlay" onClick={() => setShowAvaliar(null)}>
          <div className="modal" onClick={(e) => e.stopPropagation()}>
            <h2>Avaliar Pedido #{showAvaliar}</h2>
            <div className="form-group">
              <label>Parecer Financeiro</label>
              <textarea
                rows={4}
                value={parecerFinanceiro}
                onChange={(e) => setParecerFinanceiro(e.target.value)}
                placeholder="Digite o parecer financeiro sobre este pedido..."
                style={{ width: '100%', padding: '10px 14px', border: '1.5px solid var(--gray-200)', borderRadius: 'var(--radius)', resize: 'vertical', transition: 'var(--transition)' }}
              />
            </div>
            <div className="modal-actions">
              <button className="btn btn-outline" onClick={() => setShowAvaliar(null)}>Cancelar</button>
              <button className="btn btn-primary" onClick={handleAvaliar} disabled={saving}>
                {saving ? 'Salvando...' : 'Salvar Parecer'}
              </button>
            </div>
            {message.text && (
              <div className={`alert alert-${message.type}`}>{message.text}</div>
            )}
          </div>
        </div>
      )}

      {/* Aprovar Modal */}
      {showAprovar && (
        <div className="modal-overlay" onClick={() => setShowAprovar(null)}>
          <div className="modal" onClick={(e) => e.stopPropagation()}>
            <h2>Aprovar Pedido #{showAprovar}</h2>
            <div className="form-group">
              <label>Tipo de Contrato</label>
              <select value={aprovarForm.tipoContrato} onChange={(e) => setAprovarForm((f) => ({ ...f, tipoContrato: e.target.value }))}>
                <option value="CLIENTE">Cliente</option>
                <option value="EMPRESA">Empresa</option>
                <option value="BANCO">Banco</option>
              </select>
            </div>
            <div className="form-row">
              <div className="form-group">
                <label>Valor Mensal (R$)</label>
                <input type="number" step="0.01" value={aprovarForm.valorMensal} onChange={(e) => setAprovarForm((f) => ({ ...f, valorMensal: e.target.value }))} placeholder="1500.00" />
              </div>
              <div className="form-group">
                <label>Prazo (meses)</label>
                <input type="number" value={aprovarForm.prazoMeses} onChange={(e) => setAprovarForm((f) => ({ ...f, prazoMeses: e.target.value }))} placeholder="12" />
              </div>
            </div>
            <div className="modal-actions">
              <button className="btn btn-outline" onClick={() => setShowAprovar(null)}>Cancelar</button>
              <button className="btn btn-success" onClick={handleAprovar} disabled={saving}>
                {saving ? 'Aprovando...' : 'Aprovar Pedido'}
              </button>
            </div>
            {message.text && (
              <div className={`alert alert-${message.type}`}>{message.text}</div>
            )}
          </div>
        </div>
      )}

      {/* Rejeitar Modal */}
      {showRejeitar && (
        <div className="modal-overlay" onClick={() => setShowRejeitar(null)}>
          <div className="modal" onClick={(e) => e.stopPropagation()}>
            <h2>Rejeitar Pedido #{showRejeitar}</h2>
            <div className="form-group">
              <label>Motivo da Rejeição</label>
              <textarea
                rows={4}
                value={motivoRejeicao}
                onChange={(e) => setMotivoRejeicao(e.target.value)}
                placeholder="Informe o motivo da rejeição..."
                style={{ width: '100%', padding: '10px 14px', border: '1.5px solid var(--gray-200)', borderRadius: 'var(--radius)', resize: 'vertical', transition: 'var(--transition)' }}
              />
            </div>
            <div className="modal-actions">
              <button className="btn btn-outline" onClick={() => setShowRejeitar(null)}>Cancelar</button>
              <button className="btn btn-danger" onClick={handleRejeitar} disabled={saving}>
                {saving ? 'Rejeitando...' : 'Rejeitar Pedido'}
              </button>
            </div>
            {message.text && (
              <div className={`alert alert-${message.type}`}>{message.text}</div>
            )}
          </div>
        </div>
      )}
    </div>
  );
}
