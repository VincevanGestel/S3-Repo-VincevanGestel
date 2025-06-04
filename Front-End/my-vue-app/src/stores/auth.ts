import { defineStore } from 'pinia';
import api from '@/plugins/axios';

export const useAuthStore = defineStore('auth', {
  state: () => ({
    user: null as null | { username: string; roles: string[] },
    error: '',
  }),
  actions: {
    async login(username: string, password: string) {
      try {
        await api.post('/auth/login', { username, password });
        const userRes = await api.get('/auth/me');
        this.user = userRes.data;
        this.error = '';
      } catch (err: any) {
        this.error = err.response?.data?.message || 'Login failed';
        this.user = null;
      }
    },

    async logout() {
      try {
        await api.post('/auth/logout');
      } catch {
        // ignore
      }
      this.user = null;
    },

    async fetchUser() {
      try {
        const userRes = await api.get('/auth/me');
        this.user = userRes.data;
      } catch {
        this.user = null;
      }
    },
  },
});
