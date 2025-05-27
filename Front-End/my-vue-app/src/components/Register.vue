<template>
  <div>
    <h1>Register</h1>
    <form @submit.prevent="register">
      <label for="username">Username:</label>
      <input type="text" id="username" v-model="username" required />

      <label for="password">Password:</label>
      <input type="password" id="password" v-model="password" required />

      <button type="submit">Register</button>

      <p v-if="successMessage" style="color: green">{{ successMessage }}</p>
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
    const successMessage = ref('');
    const router = useRouter();

    const register = async () => {
      try {
        const response = await axios.post(
          'http://localhost:8080/api/auth/register',
          {
            username: username.value,
            password: password.value,
          },
          {
            withCredentials: true,
          }
        );

        console.log('Registration successful:', response.data);
        errorMessage.value = '';
        successMessage.value = 'Registration successful! Redirecting to login...';

        setTimeout(() => {
          router.push('/login');
        }, 1500);
      } catch (error: any) {
        console.error('Registration failed:', error);
        errorMessage.value =
          error.response?.data?.message || 'Registration failed';
        successMessage.value = '';
      }
    };

    return {
      username,
      password,
      errorMessage,
      successMessage,
      register,
    };
  },
});
</script>
