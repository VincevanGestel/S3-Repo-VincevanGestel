<script setup lang="ts">
</script>

<template>
  <header>
    <img alt="Vue logo" class="logo" src="./assets/logo.svg" width="125" height="125" />

    <div class="wrapper">
      <HelloWorld msg="You did it!" />
    </div>
  </header>

  <!-- Navigation bar -->
  <nav>
    <router-link to="/">Home</router-link> |
    <router-link to="/create">Create Product</router-link> |
    <router-link to="/productlist">Product List</router-link> |
    <router-link to="/create-tag">Create Tag</router-link>
    <router-link to="/cart" class="cart-link">🛒 Cart</router-link>
  </nav>

  <main>
    <TheWelcome />
    <router-view /> 
  </main>
</template>

<style scoped>
header {
  line-height: 1.5;
}

.logo {
  display: block;
  margin: 0 auto 2rem;
}

nav {
  margin: 20px 0;
  text-align: center;
}

nav a {
  margin: 0 15px;
  text-decoration: none;
  color: blue;
  font-weight: bold;
}
.cart-link {
  color: green;
  font-weight: bold;
}


@media (min-width: 1024px) {
  header {
    display: flex;
    place-items: center;
    padding-right: calc(var(--section-gap) / 2);
  }

  .logo {
    margin: 0 2rem 0 0;
  }

  header .wrapper {
    display: flex;
    place-items: flex-start;
    flex-wrap: wrap;
  }
}
</style>
