<template>
  <div>
    <h1>Login</h1>
    <form @submit.prevent="login">
      <label for="username">Username:</label>
      <input type="text" id="username" v-model="username" required />

      <label for="password">Password:</label>
      <input type="password" id="password" v-model="password" required />

      <button type="submit">Login</button>

      <p v-if="errorMessage" style="color: red">{{ errorMessage }}</p>
    </form>
  </div>
</template>

<script lang="ts">
import axios from 'axios';
import { defineComponent, ref } from 'vue';
import { useRouter } from 'vue-router';

export default defineComponent({
  setup() {
    const username = ref('');
    const password = ref('');
    const errorMessage = ref('');
    const router = useRouter();

    const login = async () => {
      try {
        const response = await axios.post(
          'http://localhost:8080/api/auth/login',
          {
            username: username.value,
            password: password.value,
          },
          {
            headers: {
              'Content-Type': 'application/json',
            },
            withCredentials: true,
          }
        );

        console.log('Login successful:', response.data.message);
        errorMessage.value = '';
        router.push('/'); // redirect to home
      } catch (error: any) {
        console.error('Login failed:', error);
        errorMessage.value = 'Invalid username or password';
      }
    };

    return {
      username,
      password,
      errorMessage,
      login,
    };
  },
});
</script>
