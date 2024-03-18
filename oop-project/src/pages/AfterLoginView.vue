<template>
  <div>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
    <AfterLoginNav @show-events="showEvents" @hide-events="hideEvents" @navigate="navigate"/> <!-- Include the AfterLoginNav.vue component here -->
    <!-- Homepage content -->

    <div class="carousel">
      <carousel :items-to-show="1" :wrap-around="true" :autoplay="3000">
        <slide v-for="(event, index) in EventsList" :key="index">
            <img :src="event.img" style="width: 100%; height:100%; ">
        </slide>

        <template #addons>
          <navigation />
          <pagination />
        </template>
      </carousel>
    </div> 
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
import { Carousel, Slide, Pagination, Navigation } from 'vue3-carousel';

export default {
  name: 'AfterLoginView',
  components: {
    AfterLoginNav, // Replace NavbarComponent with AfterLoginNav
    EventTile,
    Carousel,
    Slide,
    Pagination,
    Navigation,
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
        { id: 1, title: 'Event 1', date: '2024-03-01', des: 'this is the event description', img: 'https://www.sportshub.com.sg/sites/default/files/2023-06/Event%20Hero%20Banner%201200-675%20%E2%94%90%E2%95%9C%E2%96%92%E2%94%A4_1.jpg' },
        { id: 2, title: 'Event 2', date: '2024-03-15', des: 'this is the event description' , img: 'https://www.sportshub.com.sg/sites/default/files/2024-02/1200x675.png'},
        { id: 3, title: 'Event 3', date: '2024-03-20', des: 'this is the event description', img: 'https://www.sportshub.com.sg/sites/default/files/2024-01/SH2-BrunoMars-Event%20Hero%20Banner_0.jpg' },
        { id: 4, title: 'Event 4', date: '2024-03-25', des: 'this is the event description' , img: 'https://www.sportshub.com.sg/sites/default/files/2023-11/Event%20Hero%20Banner%201200x675pxKeyArt.jpg'},
      ],
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
