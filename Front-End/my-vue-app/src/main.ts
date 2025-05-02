// src/main.js

import './assets/main.css'  // Your main stylesheet
import { createApp } from 'vue'
import App from './App.vue'

import { createRouter, createWebHistory } from 'vue-router'
import router from './router/index';

import vuetify from './plugins/vuetify'  // If you're using Vuetify, import it
import { createPinia } from 'pinia'  // If you're using Pinia for state management
import axios from 'axios'
import VueAxios from 'vue-axios'

// Create the Vue app instance
const app = createApp(App);

// Use the router, Vuetify, Pinia, and VueAxios (for API calls)
app
  .use(router)        // Use the router configuration
  .use(vuetify)       // Use Vuetify for styling (if needed)
  .use(createPinia()) // Use Pinia for state management (if you're using it)
  .use(VueAxios, axios) // Use Axios for making API calls
  .mount('#app');
