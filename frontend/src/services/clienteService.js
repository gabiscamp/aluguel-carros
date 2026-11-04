import api from './api';

export const clienteService = {
  getById: (id) => api.get(`/api/clientes/${id}`),
  update: (id, data) => api.put(`/api/clientes/${id}`, data),
  remove: (id) => api.delete(`/api/clientes/${id}`),
};
