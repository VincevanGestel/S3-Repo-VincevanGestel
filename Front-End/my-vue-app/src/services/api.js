import axios from './axiosInstance'; // ✅ Use custom instance

// Tag Methods
export const createTag = (tagData) => {
  return axios.post('/tags', tagData)
    .then(response => response.data)
    .catch(error => {
      throw error;
    });
};

export const getAllTags = () => {
  return axios.get('/tags')
    .then(response => response.data)
    .catch(error => {
      console.error("Error fetching tags:", error);
    });
};

// Product Methods
export const getAllProducts = () => {
  return axios.get('/products')
    .then(response => response.data)
    .catch(error => {
      console.error("There was an error fetching products!", error);
    });
};

export const getProductById = (id) => {
  return axios.get(`/products/${id}`)
    .then(response => response.data)
    .catch(error => {
      console.error(`There was an error fetching product with id ${id}!`, error);
    });
};

export const createProduct = (product) => {
  return axios.post('/products', product)
    .then(response => response.data)
    .catch(error => {
      console.error("There was an error creating the product!", error);
    });
};

export const updateProduct = (id, product) => {
  return axios.put(`/products/${id}`, product)
    .then(response => response.data)
    .catch(error => {
      console.error(`There was an error updating product with id ${id}!`, error);
    });
};

export const deleteProduct = (id) => {
  return axios.delete(`/products/${id}`)
    .then(response => response.status === 204)
    .catch(error => {
      console.error(`There was an error deleting product with id ${id}!`, error);
    });
};
