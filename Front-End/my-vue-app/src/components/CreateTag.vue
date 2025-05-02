<template>
  <div>
    <h1>Create Tag</h1>
    <form @submit.prevent="createTag">
      <label for="name">Tag Name:</label>
      <input type="text" id="name" v-model="newTag.name" required />

      <button type="submit">Create Tag</button>
    </form>
  </div>
</template>

<script>
import { createTag } from '../services/api';

export default {
  data() {
    return {
      newTag: {
        name: '', // matches backend
      },
    };
  },
  methods: {
    createTag() {
      const tagToCreate = { ...this.newTag };

      console.log("Sending tag:", tagToCreate); // Still useful for now

      createTag(tagToCreate)
        .then((createdTag) => {
          console.log("Tag created:", createdTag);
          this.$router.push('/'); // Redirect after success
        })
        .catch((error) => {
          console.error("Error creating tag:", error);
        });
    },
  },
};
</script>
