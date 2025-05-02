<template>
  <div>
    <h1>Product List</h1>
    <ul>
      <li v-for="product in products" :key="product.id">{{ product.name }}</li>
    </ul>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { getAllProducts } from '@/services/api.js'; // Make sure this import path is correct

const products = ref([]);

onMounted(async () => {
  try {
    products.value = await getAllProducts();
    console.log(products.value); // Check if data is coming through
  } catch (error) {
    console.error('Error loading products:', error);
  }
});

</script>

<style>
/* Your styles for ProductList.vue */
</style>
