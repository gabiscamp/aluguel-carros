import api from './api';

export const automovelService = {
  getAll: () => api.get('/api/automoveis'),
  getById: (id) => api.get(`/api/automoveis/${id}`),
  create: (data) => api.post('/api/automoveis', data),
  update: (id, data) => api.put(`/api/automoveis/${id}`, data),
  remove: (id) => api.delete(`/api/automoveis/${id}`),
};
