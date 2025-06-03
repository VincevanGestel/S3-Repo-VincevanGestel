// src/services/axiosInstance.ts
import axios from 'axios';

const axiosInstance = axios.create({
  baseURL: 'http://localhost:8080/api', // Backend API prefix
  withCredentials: true, // Send cookies with every request
});

export default axiosInstance;
