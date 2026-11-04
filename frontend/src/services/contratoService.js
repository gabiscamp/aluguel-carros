import api from './api';

export const contratoService = {
  getAll: () => api.get('/api/contratos'),
  getById: (id) => api.get(`/api/contratos/${id}`),
  associateCredit: (id, data) => api.post(`/api/contratos/${id}/credito`, data),
};
