<script setup lang="ts">
import { ref, watch, onMounted } from 'vue';
import { useAuthStore } from './stores/auth';

const auth = useAuthStore();
const loading = ref(true);

// Fetch user on initial app load to restore session
onMounted(async () => {
  await auth.fetchUser();
  loading.value = false;
});

// Log to console when user logs in or out
watch(() => auth.user, (newUser) => {
  if (newUser) {
    console.log('User logged in:', newUser.username);
  } else {
    console.log('User logged out');
  }
});
</script>

<template>
  <header>
    <img
      alt="Vue logo"
      class="logo"
      src="./assets/logo.svg"
      width="125"
      height="125"
    />

    <div class="wrapper">
      <HelloWorld msg="You did it!" />
    </div>

    <div v-if="!loading">
      <div v-if="auth.user" class="user-info">
        Logged in as {{ auth.user.username }}
      </div>
      <div v-else class="user-info">
        Not logged in
      </div>
    </div>
  </header>

  <nav>
    <router-link to="/">Home</router-link> |
    <router-link to="/create">Create Product</router-link> |
    <router-link to="/productlist">Product List</router-link> |
    <router-link to="/create-tag">Create Tag</router-link> |
    <router-link to="/cart" class="cart-link">🛒 Cart</router-link> |
    <router-link to="/chat">Chat</router-link> 
  </nav>

  <main>
    <router-view />
  </main>
</template>

<style scoped>
.user-info {
  margin-top: 1rem;
  font-weight: bold;
}
</style>
