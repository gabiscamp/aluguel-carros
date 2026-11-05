import { useState, useEffect } from 'react';
import { useAuth } from '../contexts/AuthContext';
import { automovelService } from '../services/automovelService';
import { maskPlaca } from '../utils/masks';
import './AutomoveisPage.css';

const emptyForm = {
  matricula: '', marca: '', modelo: '', ano: '', placa: '',
};

export default function AutomoveisPage() {
  const { user } = useAuth();
  const isAgente = user?.tipo === 'AGENTE';
  const [automoveis, setAutomoveis] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showModal, setShowModal] = useState(false);
  const [editingId, setEditingId] = useState(null);
  const [form, setForm] = useState(emptyForm);
  const [saving, setSaving] = useState(false);
  const [message, setMessage] = useState({ type: '', text: '' });

  const showMsg = (type, text) => {
    setMessage({ type, text });
    setTimeout(() => setMessage({ type: '', text: '' }), 4000);
  };

  useEffect(() => { loadData(); }, []);

  const loadData = async () => {
    try {
      const res = await automovelService.getAll();
      setAutomoveis(Array.isArray(res.data) ? res.data : []);
    } catch {
      showMsg('error', 'Erro ao carregar automóveis.');
    } finally {
      setLoading(false);
    }
  };

  const openCreate = () => {
    setForm(emptyForm);
    setEditingId(null);
    setMessage({ type: '', text: '' });
    setShowModal(true);
  };

  const openEdit = (auto) => {
    setForm({
      matricula: auto.matricula || '',
      marca: auto.marca || '',
      modelo: auto.modelo || '',
      ano: auto.ano || '',
      placa: auto.placa || '',
    });
    setEditingId(auto.id);
    setMessage({ type: '', text: '' });
    setShowModal(true);
  };

  const handleSave = async () => {
    setSaving(true);
    setMessage({ type: '', text: '' });
    try {
      const payload = {
        matricula: form.matricula,
        ano: form.ano ? Number(form.ano) : null,
        marca: form.marca,
        modelo: form.modelo,
        placa: form.placa,
      };
      if (editingId) {
        await automovelService.update(editingId, payload);
        showMsg('success', 'Automóvel atualizado com sucesso!');
      } else {
        await automovelService.create(payload);
        showMsg('success', 'Automóvel cadastrado com sucesso!');
      }
      setShowModal(false);
      loadData();
    } catch (err) {
      showMsg('error', err.response?.data?.message || 'Erro ao salvar.');
    } finally {
      setSaving(false);
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm('Tem certeza que deseja excluir este automóvel?')) return;
    try {
      await automovelService.remove(id);
      showMsg('success', 'Automóvel excluído com sucesso!');
      loadData();
    } catch {
      showMsg('error', 'Erro ao excluir automóvel.');
    }
  };

  const handleChange = (field, value) => {
    setForm((prev) => ({ ...prev, [field]: value }));
  };

  if (loading) return <div className="spinner" />;

  return (
    <div className="automoveis-page">
      <div className="page-header">
        <h1>Automóveis</h1>
        {isAgente && (
          <button className="btn btn-primary" onClick={openCreate}>
            + Novo Automóvel
          </button>
        )}
      </div>

      {automoveis.length === 0 ? (
        <div className="empty-state card">
          <span className="empty-state-icon">🚘</span>
          <h3>Nenhum automóvel cadastrado</h3>
          <p>Os automóveis disponíveis aparecerão aqui.</p>
        </div>
      ) : (
        <div className="table-container">
          <table>
            <thead>
              <tr>
                <th>Matrícula</th>
                <th>Marca</th>
                <th>Modelo</th>
                <th>Ano</th>
                <th>Placa</th>
                <th>Disponível</th>
                {isAgente && <th>Ações</th>}
              </tr>
            </thead>
            <tbody>
              {automoveis.map((auto) => (
                <tr key={auto.id}>
                  <td>{auto.matricula || '-'}</td>
                  <td>{auto.marca}</td>
                  <td><strong>{auto.modelo}</strong></td>
                  <td>{auto.ano}</td>
                  <td><span className="badge badge-neutral">{auto.placa}</span></td>
                  <td>
                    <span className={`badge ${auto.disponivel ? 'badge-success' : 'badge-danger'}`}>
                      {auto.disponivel ? 'Sim' : 'Não'}
                    </span>
                  </td>
                  {isAgente && (
                    <td className="actions-cell">
                      <button className="btn btn-outline btn-sm" onClick={() => openEdit(auto)}>
                        Editar
                      </button>
                      <button className="btn btn-danger btn-sm" onClick={() => handleDelete(auto.id)}>
                        Excluir
                      </button>
                    </td>
                  )}
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      {message.text && (
        <div className={`alert alert-${message.type}`}>{message.text}</div>
      )}

      {showModal && (
        <div className="modal-overlay" onClick={() => setShowModal(false)}>
          <div className="modal" onClick={(e) => e.stopPropagation()}>
            <h2>{editingId ? 'Editar Automóvel' : 'Novo Automóvel'}</h2>
            <div className="form-group">
              <label>Matrícula</label>
              <input value={form.matricula} onChange={(e) => handleChange('matricula', e.target.value)} placeholder="Ex: AUTO-001" />
            </div>
            <div className="form-row">
              <div className="form-group">
                <label>Marca</label>
                <input value={form.marca} onChange={(e) => handleChange('marca', e.target.value)} placeholder="Ex: Toyota" />
              </div>
              <div className="form-group">
                <label>Modelo</label>
                <input value={form.modelo} onChange={(e) => handleChange('modelo', e.target.value)} placeholder="Ex: Corolla" />
              </div>
            </div>
            <div className="form-row">
              <div className="form-group">
                <label>Ano</label>
                <input type="number" value={form.ano} onChange={(e) => handleChange('ano', e.target.value)} placeholder="2024" />
              </div>
              <div className="form-group">
                <label>Placa</label>
                <input value={form.placa} onChange={(e) => handleChange('placa', maskPlaca(e.target.value))} placeholder="ABC-1234" />
              </div>
            </div>
            <div className="modal-actions">
              <button className="btn btn-outline" onClick={() => setShowModal(false)}>Cancelar</button>
              <button className="btn btn-primary" onClick={handleSave} disabled={saving}>
                {saving ? 'Salvando...' : 'Salvar'}
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
