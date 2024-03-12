<template>
  <div>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
    <AfterLoginNav @show-events="showEvents" @hide-events="hideEvents" @navigate="navigate"/> <!-- Include the AfterLoginNav.vue component here -->
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
import { ref } from 'vue';
import EventTile from '../components/EventTile.vue';
import AfterLoginNav from '../components/AfterLoginNav.vue'; // Import the AfterLoginNav.vue component
import router from '../router'; // Import the router instance

export default {
  name: 'AfterLoginView',
  components: {
    AfterLoginNav, // Replace NavbarComponent with AfterLoginNav
    EventTile
  },
  setup() {
    const eventsClicked = ref(false);
    const username = ref(''); // Define a ref for username
    const password = ref(''); // Define a ref for password

    const showEvents = () => {
      eventsClicked.value = true;
    };

    const hideEvents = () => {
      eventsClicked.value = false;
    };

    const navigate = (page) => {
      if (page === 'login') {
        router.push({ name: 'LoginPageView' }); // Navigate to LoginPageView.vue
      } else if (page === 'profile') {
        router.push({ name: 'ProfileView' }); // Navigate to ProfileView.vue
      } else {
        eventsClicked.value = false;
      }
    };

    return {
      eventsClicked,
      showEvents,
      hideEvents,
      navigate,
      username, // Expose username to the template
      password, // Expose password to the template
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
