// src/stores/auth.js
import { defineStore } from 'pinia';
import api from '@/services/axiosInstance';

export const useAuthStore = defineStore('auth', {
  state: () => ({
    user: null,
    error: '',
  }),
  actions: {
    async login(username, password) {
      try {
        await api.post('/api/auth/login', {
          username,
          password,
        }, {
          withCredentials: true,
        });

        const userRes = await api.get('/api/auth/me', {
          withCredentials: true,
        });

        this.user = userRes.data;
        this.error = '';
      } catch (err) {
        this.error = (err.response && err.response.data && err.response.data.message) || 'Login failed';
        this.user = null;
      }
    },

    async logout() {
      try {
        await api.post('/api/auth/logout', null, { withCredentials: true });
      } catch {
        // ignore
      }
      this.user = null;
    },

    async fetchUser() {
      try {
        const userRes = await api.get('/api/auth/me', { withCredentials: true });
        this.user = userRes.data;
      } catch {
        this.user = null;
      }
    },
  },
});
