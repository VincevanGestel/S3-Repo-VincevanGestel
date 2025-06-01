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
