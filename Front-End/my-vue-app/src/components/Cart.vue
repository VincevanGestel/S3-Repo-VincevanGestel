<template>
  <div>
    <h2>Your Cart</h2>
    <div v-if="cart?.items?.length">
      <ul>
        <li v-for="item in cart.items" :key="item.product.id">
          {{ item.product.name }} -
          €{{ (item.product.price * item.quantity).toFixed(2) }}

          <input
            type="number"
            min="1"
            v-model.number="item.quantity"
            @change="updateQuantity(item.product.id, item.quantity)"
            style="width: 50px; margin-left: 10px;"
          />

          <button @click="remove(item.product.id)" style="margin-left: 10px;">
            Remove
          </button>
        </li>
      </ul>

      <p><strong>Total:</strong> €{{ total.toFixed(2) }}</p>
      <button @click="clear">Clear Cart</button>
    </div>

    <div v-else>
      <p>Your cart is empty.</p>
    </div>
  </div>
</template>

<script>
import { computed, defineComponent, onMounted, ref } from 'vue';
import { getCart, removeFromCart, clearCart, updateCartQuantity } from '../services/api';

export default defineComponent({
  setup() {
    const cart = ref(null);

    const fetchCart = async () => {
      cart.value = await getCart();
    };

    const remove = async (productId) => {
      await removeFromCart(productId);
      await fetchCart();
    };

    const clear = async () => {
      await clearCart();
      await fetchCart();
    };

    const updateQuantity = async (productId, quantity) => {
      if (quantity < 1) {
        await remove(productId);
      } else {
        await updateCartQuantity(productId, quantity);
      }
      await fetchCart();
    };

    const total = computed(() => {
      return cart.value?.items?.reduce((sum, item) => {
        return sum + item.product.price * item.quantity;
      }, 0) || 0;
    });

    onMounted(fetchCart);

    return {
      cart,
      remove,
      clear,
      updateQuantity,
      total,
    };
  },
});
</script>
