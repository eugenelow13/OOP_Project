<template>
  <div>
    <div class="navbar">
      <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
      <NavbarComponent @show-events="showEvents" @hide-events="hideEvents" @navigate="navigate"/> <!-- Include the NavbarComponent.vue component here -->
    </div>
    <!-- Homepage content -->
    <div>
      <h1>Hello</h1>
    </div>

    <div class="home">
      <!-- <h1>Home</h1> -->
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
    </div>


    <div class="events" ref="eventsSection">
      <h1>Events</h1>
      <div v-if="eventsClicked" class="event-grid">      
        <EventTile v-for="event in EventsList" :key="event.title" :event="event" />
      </div>
    </div>



  </div>  
</template>

// HomepageView.vue
<script>
import 'vue3-carousel/dist/carousel.css'
import { Carousel, Slide, Pagination, Navigation } from 'vue3-carousel'
import { ref,watch } from 'vue';
import EventTile from '../components/EventTile.vue';
import NavbarComponent from '../components/NavbarComponent.vue';
import router from '../router'; // Import the router instance

export default {
  name: 'HomepageView',
  components: {
    NavbarComponent,
    EventTile,
    Carousel,
    Slide,
    Pagination,
    Navigation,
  },
  setup() {
    const eventsClicked = ref(false);
    const eventsSection = ref(null);

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
        window.scrollTo({ top: 0, behavior: 'smooth' });
      }
    };

    watch(eventsClicked, (newVal) => {
      if (newVal && eventsSection.value) {
        eventsSection.value.scrollIntoView({ behavior: 'smooth', block: 'start' });
      }
    });

    return {
      eventsClicked,
      showEvents,
      hideEvents,
      navigate,
      EventsList: [
        { id: 1, title: 'Event 1', date: '2024-03-01', des: 'this is the event description', img: 'https://www.sportshub.com.sg/sites/default/files/2023-06/Event%20Hero%20Banner%201200-675%20%E2%94%90%E2%95%9C%E2%96%92%E2%94%A4_1.jpg' },
        { id: 2, title: 'Event 2', date: '2024-03-15', des: 'this is the event description' , img: 'https://www.sportshub.com.sg/sites/default/files/2024-02/1200x675.png'},
        { id: 3, title: 'Event 3', date: '2024-03-20', des: 'this is the event description', img: 'https://www.sportshub.com.sg/sites/default/files/2024-01/SH2-BrunoMars-Event%20Hero%20Banner_0.jpg' },
        { id: 4, title: 'Event 4', date: '2024-03-25', des: 'this is the event description' , img: 'https://www.sportshub.com.sg/sites/default/files/2023-11/Event%20Hero%20Banner%201200x675pxKeyArt.jpg'},
        // Add more events as needed

      ],
      eventsSection,
    
    };
  }
};
</script>


<style>
.event-grid {
  /* margin-left:120px; */
  display: flex;
  flex-wrap: wrap;
  justify-content: center; /* Center horizontally */
  align-items: center; 
}

.carousel{
  background: transparent;
  width: 80vw;
  margin-left: auto;
  margin-right: auto;
}
/* Component-specific styles */
/* Import Navbar.css */
@import '../components/Navbar.css';
</style>
