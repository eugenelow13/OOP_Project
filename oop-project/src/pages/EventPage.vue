<!-- src/components/EventPage.vue -->
<template>
  <div>
    <h1>{{ event.title }}</h1>
    <!-- <h2>{{ event.title }}</h2> -->
    <!-- Add other components and details for the event page -->
  </div>
</template>

<script>
//   import NavbarComponent from '../components/NavbarComponent.vue'; 

export default {
    props: ['event'],
    name: 'EventPage',
    // Add any additional logic or methods as needed
  };

</script>
  
<style scoped>
/* Add component-specific styles */

</style>
  