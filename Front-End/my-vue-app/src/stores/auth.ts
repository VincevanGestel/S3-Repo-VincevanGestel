import { defineStore } from 'pinia';
import api from '@/plugins/axios';

export const useAuthStore = defineStore('auth', {
  state: () => ({
    user: null as null | { username: string, roles: string[] },
    error: '',
  }),
  actions: {
    async register(username: string, password: string) {
      try {
        const res = await api.post('/api/auth/register', { username, password });
        this.user = res.data;
        this.error = '';
      } catch (err: any) {
        this.error = err.response?.data?.message || 'Registration failed';
      }
    },
    async login(username: string, password: string) {
      const formData = new URLSearchParams();
      formData.append('username', username);
      formData.append('password', password);
      try {
        await api.post('/login', formData, {
          headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
        });
        this.user = { username, roles: [] }; // You may want to call a /me endpoint to get full user
        this.error = '';
      } catch (err: any) {
        this.error = err.response?.data?.message || 'Login failed';
      }
    },
    async logout() {
      await api.post('/logout'); // Spring default
      this.user = null;
    }
  },
});
