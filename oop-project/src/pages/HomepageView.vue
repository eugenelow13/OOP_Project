<template>
  <div>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
    <NavbarComponent /> <!-- Include the NavbarComponent.vue component here -->
    <!-- Homepage content -->
  </div>
</template>

<script>
import NavbarComponent from '../components/NavbarComponent.vue'; // Import the NavbarComponent.vue component

export default {
  name: 'HomepageView', // Update the component name
  components: {
    NavbarComponent // Register the NavbarComponent.vue component
  }
}
</script>

<style>
/* Component-specific styles */
/* Import Navbar.css */
@import '../components/Navbar.css';
</style>
