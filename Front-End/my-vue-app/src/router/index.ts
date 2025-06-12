import { createRouter, createWebHistory } from 'vue-router';
import type { RouteRecordRaw } from 'vue-router';
import ProductList from '@/components/ProductList.vue'; // Adjust the path as needed
import CreateProduct from '@/components/CreateProduct.vue'; // Adjust the path as needed
import CreateTag from '@/components/CreateTag.vue';
import Login from '@/components/Login.vue';
import Register from '@/components/Register.vue';
import Cart from '../components/Cart.vue';
import Chat from '@/components/Chat.vue';

// Explicitly typing the routes to ensure correct type-checking with TypeScript
const routes: Array<RouteRecordRaw> = [
  { path: '/login', component: Login },
  { path: '/register', component: Register },
  { path: '/chat', component: Chat },
  { path: '/cart', component: Cart },
  {
    path: '/productlist',
    name: 'ProductList',
    component: ProductList,  // This will display when visiting '/'
  },
  {
    path: '/create',
    name: 'CreateProduct',
    component: CreateProduct,  //This will display when visiting '/create'
  },
  {
    path: '/create-tag',
    name: 'CreateTag',
    component: CreateTag,
  },
];

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL || '/'),
//history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
