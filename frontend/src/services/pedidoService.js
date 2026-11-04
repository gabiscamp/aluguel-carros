import api from './api';

export const pedidoService = {
  getAll: () => api.get('/api/pedidos'),
  getMeus: () => api.get('/api/pedidos/meus'),
  getById: (id) => api.get(`/api/pedidos/${id}`),
  create: (data) => api.post('/api/pedidos', data),
  update: (id, data) => api.put(`/api/pedidos/${id}`, data),
  cancelar: (id) => api.put(`/api/pedidos/${id}/cancelar`),
  avaliar: (id, parecerFinanceiro) => api.put(`/api/pedidos/${id}/avaliar`, { parecerFinanceiro }),
  aprovar: (id, data) => api.put(`/api/pedidos/${id}/aprovar`, data),
  rejeitar: (id, motivo) => api.put(`/api/pedidos/${id}/rejeitar`, { motivo }),
  getDisponiveis: () => api.get('/api/automoveis/disponiveis'),
};
