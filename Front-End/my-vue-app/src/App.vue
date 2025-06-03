<script setup lang="ts">
import { ref, watch } from 'vue';
import { useAuthStore } from './stores/auth';

const auth = useAuthStore();
const loading = ref(true);

// Fetch user on initial load
auth.fetchUser().finally(() => {
  loading.value = false;
});

// You can watch auth.user to do any side effects if needed
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
    <img alt="Vue logo" class="logo" src="./assets/logo.svg" width="125" height="125" />

    <div class="wrapper">
      <HelloWorld msg="You did it!" />
    </div>

    <div v-if="!loading && auth.user" class="user-info">
      Logged in as {{ auth.user.username }}
    </div>
  </header>

  <nav>
    <router-link to="/">Home</router-link> |
    <router-link to="/create">Create Product</router-link> |
    <router-link to="/productlist">Product List</router-link> |
    <router-link to="/create-tag">Create Tag</router-link>
    <router-link to="/cart" class="cart-link">🛒 Cart</router-link>
  </nav>

  <main>
    <router-view />
  </main>
</template>
