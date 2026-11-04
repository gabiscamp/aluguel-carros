import api from './api';

export const authService = {
  login: (email, senha) => api.post('/api/auth/login', { email, senha }),
  register: (payload) => api.post('/api/auth/register', payload),
};
