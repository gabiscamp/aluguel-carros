import { useState, useEffect } from 'react';
import { useAuth } from '../contexts/AuthContext';
import { clienteService } from '../services/clienteService';
import { maskCPF, maskCEP } from '../utils/masks';
import './PerfilPage.css';

export default function PerfilPage() {
  const { user } = useAuth();
  const [perfil, setPerfil] = useState(null);
  const [editing, setEditing] = useState(false);
  const [form, setForm] = useState({});
  const [cep, setCep] = useState('');
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [message, setMessage] = useState({ type: '', text: '' });

  const showMsg = (type, text) => {
    setMessage({ type, text });
    setTimeout(() => setMessage({ type: '', text: '' }), 4000);
  };

  useEffect(() => {
    loadPerfil();
  }, []);

  const loadPerfil = async () => {
    if (user?.tipo !== 'CLIENTE') {
      setLoading(false);
      return;
    }
    try {
      const res = await clienteService.getById(user.id);
      setPerfil(res.data);
      setForm(res.data);
    } catch {
      showMsg('error', 'Erro ao carregar perfil.');
    } finally {
      setLoading(false);
    }
  };

  const handleChange = (field, value) => {
    setForm((prev) => ({ ...prev, [field]: value }));
  };

  const handleCEP = async (rawCep) => {
    const masked = maskCEP(rawCep);
    setCep(masked);
    const cleanCEP = masked.replace(/\D/g, '');
    if (cleanCEP.length === 8) {
      try {
        const res = await fetch(`https://viacep.com.br/ws/${cleanCEP}/json/`);
        const data = await res.json();
        if (!data.erro) {
          handleChange('endereco', `${data.logradouro}, ${data.bairro}, ${data.localidade} - ${data.uf}`);
        }
      } catch {}
    }
  };

  const handleEmpregadorChange = (index, field, value) => {
    const empregadores = [...(form.empregadores || [])];
    empregadores[index] = { ...empregadores[index], [field]: value };
    setForm((prev) => ({ ...prev, empregadores }));
  };

  const addEmpregador = () => {
    const empregadores = form.empregadores || [];
    if (empregadores.length >= 3) return;
    setForm((prev) => ({
      ...prev,
      empregadores: [...empregadores, { nome: '', cargo: '', salario: '' }],
    }));
  };

  const removeEmpregador = (index) => {
    const empregadores = [...(form.empregadores || [])];
    empregadores.splice(index, 1);
    setForm((prev) => ({ ...prev, empregadores }));
  };

  const handleSave = async () => {
    setSaving(true);
    setMessage({ type: '', text: '' });
    try {
      const payload = {
        ...form,
        rendaMensal: form.rendaMensal ? Number(form.rendaMensal) : null,
        empregadores: (form.empregadores || []).map(emp => ({
          ...emp,
          salario: emp.salario ? Number(emp.salario) : null,
        })),
      };
      const res = await clienteService.update(user.id, payload);
      setPerfil(res.data);
      setEditing(false);
      showMsg('success', 'Perfil atualizado com sucesso!');
    } catch (err) {
      showMsg('error', err.response?.data?.message || 'Erro ao salvar perfil.');
    } finally {
      setSaving(false);
    }
  };

  const handleCancel = () => {
    setForm(perfil);
    setEditing(false);
  };

  if (loading) return <div className="spinner" />;

  if (user?.tipo !== 'CLIENTE') {
    return (
      <div className="perfil-page">
        <div className="page-header"><h1>Perfil</h1></div>
        <div className="card">
          <div className="empty-state">
            <span className="empty-state-icon">👤</span>
            <h3>Área exclusiva</h3>
            <p>A página de perfil está disponível apenas para clientes.</p>
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className="perfil-page">
      <div className="page-header">
        <h1>Meu Perfil</h1>
        {!editing && (
          <button className="btn btn-primary" onClick={() => { setMessage({ type: '', text: '' }); setEditing(true); }}>
            Editar Perfil
          </button>
        )}
      </div>

      <div className="card perfil-card">
        <div className="perfil-section">
          <h3>Dados Pessoais</h3>
          <div className="form-row">
            <div className="form-group">
              <label>Nome</label>
              <input
                type="text"
                value={form.nome || ''}
                onChange={(e) => handleChange('nome', e.target.value)}
                disabled={!editing}
              />
            </div>
            <div className="form-group">
              <label>CPF</label>
              <input
                type="text"
                value={form.cpf || ''}
                onChange={(e) => handleChange('cpf', maskCPF(e.target.value))}
                disabled={!editing}
                placeholder="000.000.000-00"
              />
            </div>
          </div>
          <div className="form-row">
            <div className="form-group">
              <label>RG</label>
              <input
                type="text"
                value={form.rg || ''}
                onChange={(e) => handleChange('rg', e.target.value)}
                disabled={!editing}
              />
            </div>
            <div className="form-group">
              <label>Profissão</label>
              <input
                type="text"
                value={form.profissao || ''}
                onChange={(e) => handleChange('profissao', e.target.value)}
                disabled={!editing}
              />
            </div>
          </div>
          <div className="form-row">
            <div className="form-group">
              <label>CEP</label>
              <input
                type="text"
                value={cep}
                onChange={(e) => handleCEP(e.target.value)}
                disabled={!editing}
                placeholder="00000-000"
              />
            </div>
            <div className="form-group">
              <label>Renda Mensal (R$)</label>
              <input
                type="number"
                value={form.rendaMensal || ''}
                onChange={(e) => handleChange('rendaMensal', e.target.value)}
                disabled={!editing}
                step="0.01"
              />
            </div>
          </div>
          <div className="form-group">
            <label>Endereço</label>
            <input
              type="text"
              value={form.endereco || ''}
              onChange={(e) => handleChange('endereco', e.target.value)}
              disabled={!editing}
              placeholder="Rua, bairro, cidade - UF"
            />
          </div>
        </div>

        <div className="perfil-section">
          <div className="section-header">
            <h3>Empregadores (máx. 3)</h3>
            {editing && (form.empregadores || []).length < 3 && (
              <button className="btn btn-outline btn-sm" onClick={addEmpregador}>
                + Adicionar
              </button>
            )}
          </div>
          {(form.empregadores || []).length === 0 && (
            <p className="empty-text">Nenhum empregador cadastrado.</p>
          )}
          {(form.empregadores || []).map((emp, i) => (
            <div key={i} className="empregador-item">
              <div className="empregador-header">
                <span>Empregador {i + 1}</span>
                {editing && (
                  <button className="btn btn-danger btn-sm" onClick={() => removeEmpregador(i)}>
                    Remover
                  </button>
                )}
              </div>
              <div className="form-row">
                <div className="form-group">
                  <label>Nome da Empresa</label>
                  <input
                    type="text"
                    value={emp.nome || ''}
                    onChange={(e) => handleEmpregadorChange(i, 'nome', e.target.value)}
                    disabled={!editing}
                  />
                </div>
                <div className="form-group">
                  <label>Cargo</label>
                  <input
                    type="text"
                    value={emp.cargo || ''}
                    onChange={(e) => handleEmpregadorChange(i, 'cargo', e.target.value)}
                    disabled={!editing}
                  />
                </div>
              </div>
              <div className="form-group">
                <label>Salário (R$)</label>
                <input
                  type="number"
                  value={emp.salario || ''}
                  onChange={(e) => handleEmpregadorChange(i, 'salario', e.target.value)}
                  disabled={!editing}
                  step="0.01"
                />
              </div>
            </div>
          ))}
        </div>

        {editing && (
          <div className="perfil-actions">
            <button className="btn btn-outline" onClick={handleCancel}>
              Cancelar
            </button>
            <button className="btn btn-primary" onClick={handleSave} disabled={saving}>
              {saving ? 'Salvando...' : 'Salvar Alterações'}
            </button>
          </div>
        )}

        {message.text && (
          <div className={`alert alert-${message.type}`}>{message.text}</div>
        )}
      </div>
    </div>
  );
}
