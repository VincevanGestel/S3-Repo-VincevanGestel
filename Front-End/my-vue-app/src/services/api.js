import axios from './axiosInstance'; // ✅ Use custom instance

//Cart methods
export const getCart = () => {
  return axios.get('/cart')
    .then(res => res.data)
    .catch(err => {
      console.error('Error fetching cart:', err);
      throw err;
    });
};

export const addToCart = (productId, quantity = 1) => {
  return axios.post(`/cart/add?productId=${productId}&quantity=${quantity}`)
    .catch(err => {
      console.error('Error adding to cart:', err);
      throw err;
    });
};

export const removeFromCart = (productId) => {
  return axios.delete(`/cart/remove?productId=${productId}`)
    .catch(err => {
      console.error('Error removing from cart:', err);
      throw err;
    });
};

export const clearCart = () => {
  return axios.delete('/cart/clear')
    .catch(err => {
      console.error('Error clearing cart:', err);
      throw err;
    });
};

export const updateCartQuantity = (productId, quantity) => {
  return axios.put(`/cart/update?productId=${productId}&quantity=${quantity}`)
    .catch(err => {
      console.error('Error updating cart quantity:', err);
      throw err;
    });
};

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
