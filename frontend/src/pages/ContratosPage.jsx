import React, { useState, useEffect } from 'react';
import { useAuth } from '../contexts/AuthContext';
import { contratoService } from '../services/contratoService';
import './ContratosPage.css';

export default function ContratosPage() {
  const { user } = useAuth();
  const isAgente = user?.tipo === 'AGENTE';
  const [contratos, setContratos] = useState([]);
  const [loading, setLoading] = useState(true);
  const [message, setMessage] = useState({ type: '', text: '' });
  const [detailId, setDetailId] = useState(null);
  const [detail, setDetail] = useState(null);
  const [loadingDetail, setLoadingDetail] = useState(false);

  // Credit form
  const [showCredit, setShowCredit] = useState(null);
  const [creditForm, setCreditForm] = useState({ taxaJuros: '', valorFinanciado: '', numeroParcelas: '' });
  const [saving, setSaving] = useState(false);

  useEffect(() => { loadData(); }, []);

  const loadData = async () => {
    try {
      const res = await contratoService.getAll();
      setContratos(Array.isArray(res.data) ? res.data : []);
    } catch {
      setMessage({ type: 'error', text: 'Erro ao carregar contratos.' });
    } finally {
      setLoading(false);
    }
  };

  const viewDetail = async (id) => {
    if (detailId === id) { setDetailId(null); return; }
    setLoadingDetail(true);
    setDetailId(id);
    try {
      const res = await contratoService.getById(id);
      setDetail(res.data);
    } catch {
      setMessage({ type: 'error', text: 'Erro ao carregar detalhes.' });
      setDetailId(null);
    } finally {
      setLoadingDetail(false);
    }
  };

  const handleCredit = async () => {
    setSaving(true);
    setMessage({ type: '', text: '' });
    try {
      await contratoService.associateCredit(showCredit, {
        taxaJuros: Number(creditForm.taxaJuros),
        valorFinanciado: Number(creditForm.valorFinanciado),
        numeroParcelas: Number(creditForm.numeroParcelas),
        agenteBancoId: user.id
      });
      setMessage({ type: 'success', text: 'Crédito associado com sucesso!' });
      setShowCredit(null);
      setCreditForm({ taxaJuros: '', valorFinanciado: '', numeroParcelas: '' });
      loadData();
    } catch (err) {
      setMessage({ type: 'error', text: err.response?.data?.message || 'Erro ao associar crédito.' });
    } finally {
      setSaving(false);
    }
  };

  if (loading) return <div className="spinner" />;

  return (
    <div className="contratos-page">
      <div className="page-header">
        <h1>{isAgente ? 'Contratos' : 'Meus Contratos'}</h1>
      </div>

      {contratos.length === 0 ? (
        <div className="empty-state card">
          <span className="empty-state-icon">📄</span>
          <h3>Nenhum contrato encontrado</h3>
          <p>Os contratos gerados a partir de pedidos aprovados aparecerão aqui.</p>
        </div>
      ) : (
        <div className="table-container">
          <table>
            <thead>
              <tr>
                <th>ID</th>
                <th>Tipo</th>
                <th>Valor Mensal</th>
                <th>Prazo</th>
                <th>Status</th>
                <th>Ações</th>
              </tr>
            </thead>
            <tbody>
              {contratos.map((c) => (
                <React.Fragment key={c.id}>
                  <tr>
                    <td>#{c.id}</td>
                    <td>{c.tipoContrato || c.tipo || '-'}</td>
                    <td>R$ {Number(c.valorMensal || 0).toFixed(2)}</td>
                    <td>{c.prazoMeses || '-'} meses</td>
                    <td>
                      <span className={`badge ${c.ativo ? 'badge-success' : 'badge-neutral'}`}>
                        {c.ativo ? 'Ativo' : 'Inativo'}
                      </span>
                    </td>
                    <td className="actions-cell">
                      <button className="btn btn-outline btn-sm" onClick={() => viewDetail(c.id)}>
                        {detailId === c.id ? 'Fechar' : 'Detalhes'}
                      </button>
                      {isAgente && (
                        <button className="btn btn-primary btn-sm" onClick={() => { setShowCredit(c.id); setCreditForm({ taxaJuros: '', valorFinanciado: '', numeroParcelas: '' }); }}>
                          Associar Crédito
                        </button>
                      )}
                    </td>
                  </tr>
                  {detailId === c.id && (
                    <tr key={`detail-${c.id}`}>
                      <td colSpan={6}>
                        {loadingDetail ? (
                          <div className="spinner" />
                        ) : detail ? (
                          <div className="contrato-detail">
                            <div className="detail-grid">
                              <div><strong>Pedido:</strong> #{detail.pedidoId || '-'}</div>
                              <div><strong>Tipo:</strong> {detail.tipoContrato || detail.tipo || '-'}</div>
                              <div><strong>Valor Mensal:</strong> R$ {Number(detail.valorMensal || 0).toFixed(2)}</div>
                              <div><strong>Prazo:</strong> {detail.prazoMeses || '-'} meses</div>
                              <div><strong>Data do Contrato:</strong> {detail.dataContrato || '-'}</div>
                              <div><strong>Status:</strong> {detail.ativo ? 'Ativo' : 'Inativo'}</div>
                              {detail.contratoCredito && (
                                <>
                                  <div><strong>Taxa de Juros:</strong> {detail.contratoCredito.taxaJuros}%</div>
                                  <div><strong>Valor Financiado:</strong> R$ {Number(detail.contratoCredito.valorFinanciado || 0).toFixed(2)}</div>
                                  <div><strong>Parcelas:</strong> {detail.contratoCredito.numeroParcelas}</div>
                                </>
                              )}
                            </div>
                          </div>
                        ) : null}
                      </td>
                    </tr>
                  )}
                </React.Fragment>
              ))}
            </tbody>
          </table>
        </div>
      )}

      {message.text && (
        <div className={`alert alert-${message.type}`}>{message.text}</div>
      )}

      {/* Credit Modal */}
      {showCredit && (
        <div className="modal-overlay" onClick={() => setShowCredit(null)}>
          <div className="modal" onClick={(e) => e.stopPropagation()}>
            <h2>Associar Crédito ao Contrato #{showCredit}</h2>
            <div className="form-row">
              <div className="form-group">
                <label>Taxa de Juros (%)</label>
                <input type="number" step="0.01" value={creditForm.taxaJuros} onChange={(e) => setCreditForm((f) => ({ ...f, taxaJuros: e.target.value }))} placeholder="1.5" />
              </div>
              <div className="form-group">
                <label>Valor Financiado (R$)</label>
                <input type="number" step="0.01" value={creditForm.valorFinanciado} onChange={(e) => setCreditForm((f) => ({ ...f, valorFinanciado: e.target.value }))} placeholder="50000.00" />
              </div>
            </div>
            <div className="form-row">
              <div className="form-group">
                <label>Número de Parcelas</label>
                <input type="number" value={creditForm.numeroParcelas} onChange={(e) => setCreditForm((f) => ({ ...f, numeroParcelas: e.target.value }))} placeholder="48" />
              </div>
              <div className="form-group">
              </div>
            </div>
            <div className="modal-actions">
              <button className="btn btn-outline" onClick={() => setShowCredit(null)}>Cancelar</button>
              <button className="btn btn-primary" onClick={handleCredit} disabled={saving}>
                {saving ? 'Salvando...' : 'Associar Crédito'}
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
