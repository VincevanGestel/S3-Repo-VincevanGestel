<template>
  <div>
    <h1>Product List</h1>
    <ul>
      <li v-for="product in products" :key="product.id" style="margin-bottom: 1rem;">
        <strong>{{ product.name }}</strong> - €{{ product.price.toFixed(2) }}
        <button @click="addProductToCart(product.id)" style="margin-left: 1rem;">
          Add to Cart
        </button>
      </li>
    </ul>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { getAllProducts, addToCart } from '@/services/api.js'; // Adjust path if needed

const products = ref([]);

onMounted(async () => {
  try {
    products.value = await getAllProducts();
  } catch (error) {
    console.error('Error loading products:', error);
  }
});

const addProductToCart = async (productId) => {
  try {
    await addToCart(productId, 1);
    alert('Product added to cart!');
  } catch (error) {
    console.error('Error adding product to cart:', error);
    alert('Failed to add product to cart.');
  }
};
</script>

<style scoped>
button {
  padding: 0.4rem 0.8rem;
  background-color: #42b983;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}
button:hover {
  background-color: #369c72;
}
</style>
