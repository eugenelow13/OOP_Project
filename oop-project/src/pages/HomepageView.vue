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

// HomepageView.vue
<script>
import { ref } from 'vue';
import EventTile from '../components/EventTile.vue';
import NavbarComponent from '../components/NavbarComponent.vue';
import router from '../router'; // Import the router instance

export default {
  name: 'HomepageView',
  components: {
    NavbarComponent,
    EventTile
  },
  setup() {
    const eventsClicked = ref(false);

    const showEvents = () => {
      eventsClicked.value = true;
    };

    const hideEvents = () => {
      eventsClicked.value = false;
    };

    const navigate = (page) => {
      if (page === 'login') {
        router.push({ name: 'LoginPageView' }); // Navigate to LoginPageView.vue
      } else {
        eventsClicked.value = false;
      }
    };

    return {
      eventsClicked,
      showEvents,
      hideEvents,
      navigate,
      EventsList: [
        { title: 'Event 1', date: '2024-03-01', des: 'this is the event description' },
        { title: 'Event 2', date: '2024-03-15', des: 'this is the event description' },
        { title: 'Event 3', date: '2024-03-20', des: 'this is the event description' },
        { title: 'Event 4', date: '2024-03-25', des: 'this is the event description' }
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
