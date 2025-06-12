<template>
  <div>
    <h1>Create Product</h1>
    <form @submit.prevent="createProduct">
      <label for="name">Name:</label>
      <input type="text" id="name" v-model="newProduct.name" required />

      <label for="price">Price:</label>
      <input type="number" id="price" v-model="newProduct.price" required />

      <label for="tags">Tags:</label>
      <select id="tags" v-model="selectedTagIds" multiple required style="width: 200px; height: 100px;">
        <option v-for="tag in availableTags" :key="tag.id" :value="tag.id">
          {{ tag.name }}
        </option>
      </select>

      <button type="submit">Create Product</button>
    </form>
  </div>
</template>

<script>
import { createProduct, getAllTags } from '../services/api';

export default {
  data() {
    return {
      newProduct: {
        name: '',
        price: 0,
      },
      selectedTagIds: [],
      availableTags: [],
    };
  },
  methods: {
    createProduct() {
  const productToCreate = {
    ...this.newProduct,
    tagIds: this.selectedTagIds.map(Number), // ✅ convert to numbers
  };

  createProduct(productToCreate)
    .then((createdProduct) => {
      this.$router.push('/');
    })
    .catch((error) => {
      console.error("Error creating product:", error);
    });
},
    fetchTags() {
      getAllTags()
        .then((tags) => {
          console.log("Fetched tags:", tags);
          this.availableTags = tags;
        })
        .catch((error) => {
          console.error("Error fetching tags:", error);
        });
    },
  },
  mounted() {
    this.fetchTags();
  },
};
</script>
