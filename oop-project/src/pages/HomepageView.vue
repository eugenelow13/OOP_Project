<template>
  <div>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
    <NavbarComponent @show-events="showEvents" @hide-events="hideEvents" @navigate="navigate"/> <!-- Include the NavbarComponent.vue component here -->
    <!-- Homepage content -->
    <div class="content">
      <div>
        <h1>Events</h1>
      </div> 
      <div v-if="eventsClicked" class="event-grid">      
        <EventTile v-for="event in EventsList" :key="event.title" :event="event"/>
      </div>
    </div>
  </div>  
</template>

<script>
import { ref } from 'vue'; // Import ref from vue
import EventTile from '../components/EventTile.vue';
import NavbarComponent from '../components/NavbarComponent.vue'; // Import the NavbarComponent.vue component

export default {
  name: 'HomepageView', // Update the component name
  components: {
    NavbarComponent, // Register the NavbarComponent.vue component
    EventTile
  },
  setup() {
    const eventsClicked = ref(false); // Define a reactive variable to track if Events button is clicked

    const showEvents = () => {
      eventsClicked.value = true; // Set eventsClicked to true when Events button is clicked
    };

    const hideEvents = () => {
      eventsClicked.value = false; // Set eventsClicked to false when any other button is clicked
    };

    const navigate = (page) => {
      if (page !== 'events') {
        eventsClicked.value = false; // Set eventsClicked to false when navigating to any page other than Events
      }
    };

    return {
      eventsClicked,
      showEvents,
      hideEvents,
      navigate,
      EventsList: [
        { id: 1, title: 'Event 1', date: '2024-03-01', des: 'this is the event description' },
        { id: 2, title: 'Event 2', date: '2024-03-15', des: 'this is the event description' },
        { id: 3, title: 'Event 3', date: '2024-03-20', des: 'this is the event description' },
        { id: 4, title: 'Event 4', date: '2024-03-25', des: 'this is the event description' }
        // Add more events as needed
      ]
    };
  }
};
</script>

<style>
.event-grid {
  /* margin-left:120px; */
  display: flex;
  flex-wrap: wrap;
}
.content {
  width: 100vw;
}
/* Component-specific styles */
/* Import Navbar.css */
@import '../components/Navbar.css';
</style>
