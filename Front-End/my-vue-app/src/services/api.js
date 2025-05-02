import axios from 'axios'; //plannetje, wat moet gebeuren, hoe je dit kunt aantonen. en document tests, wat handig (Testplannetje maken, Welke tests waarvoor gebruikt zouden kunnen worden, end to end test/systeem test), 

const API_URL = 'http://localhost:8080/api'; // Base URL for the back-end API endpoint (removed `/products`)

// Tag Methods
export const createTag = (tagData) => {
  return axios.post(`${API_URL}/tags`, tagData)  // No need to include /products for tags
    .then(response => response.data)
    .catch(error => {
      throw error;
    });
};

export const getAllTags = () => {
  return axios.get(`${API_URL}/tags`)
    .then(response => response.data)
    .catch(error => {
      console.error("Error fetching tags:", error);
    });
};

// Product Methods
export const getAllProducts = () => {
  return axios.get(`${API_URL}/products`)  // Use `/products` for product-specific requests
    .then(response => response.data)
    .catch(error => {
      console.error("There was an error fetching products!", error);
    });
};

export const getProductById = (id) => {
  return axios.get(`${API_URL}/products/${id}`)  // Use `/products` for fetching product by ID
    .then(response => response.data)
    .catch(error => {
      console.error(`There was an error fetching product with id ${id}!`, error);
    });
};

export const createProduct = (product) => {
  return axios.post(`${API_URL}/products`, product)  // Use `/products` for creating products
    .then(response => response.data)
    .catch(error => {
      console.error("There was an error creating the product!", error);
    });
};

export const updateProduct = (id, product) => {
  return axios.put(`${API_URL}/products/${id}`, product)  // Use `/products` for updating products
    .then(response => response.data)
    .catch(error => {
      console.error(`There was an error updating product with id ${id}!`, error);
    });
};

export const deleteProduct = (id) => {
  return axios.delete(`${API_URL}/products/${id}`)  // Use `/products` for deleting products
    .then(response => response.status === 204)
    .catch(error => {
      console.error(`There was an error deleting product with id ${id}!`, error);
    });
};
