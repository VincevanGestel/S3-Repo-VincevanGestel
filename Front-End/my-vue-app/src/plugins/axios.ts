import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080', // your backend
  withCredentials: true, // needed for Spring Session cookies
});

export default api;
