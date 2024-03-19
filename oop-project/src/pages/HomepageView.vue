<template>
  <div>
    <!-- Include the NavbarComponent.vue component here -->
    <div class="navbar">
      <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
      <!-- <NavbarComponent @show-contact="showContact" @hide-contact="hideContact" @show-events="showEvents" @hide-events="hideEvents" @navigate="navigate"/> Include the NavbarComponent.vue component here -->
      <!-- <NavbarComponent @show-contact="showContact"  @show-events="showEvents"  @navigate="navigate"/>  -->
      <NavbarComponent   @navigate="navigate"/>
    </div>

    <!-- Homepage content -->

    <div class="home">

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
    
    <!-- Events Section -->
    <div class="events" ref="eventsSection">
      <div>
        <h1>Events</h1>
      </div> 
      <div class="event-grid">      
        <EventTile v-for="event in EventsList" :key="event.title" :event="event" @click="handleEventClick(event)"/>
      </div>
    </div>

    <!-- Contact Section-->
    <div class="contact" ref="contactSection">
      <h1>Contact Us</h1>
    </div>

   
    </div>
  </div>
</template>


<script>
import 'vue3-carousel/dist/carousel.css'
import { Carousel, Slide, Pagination, Navigation } from 'vue3-carousel'
import { ref } from 'vue';
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
    // const eventsClicked = ref(false);
    // const contactClicked = ref(false);
    const eventsSection = ref(null);
    const contactSection = ref(null);
    const sectionInView = ref(false);

    // const showEvents = () => {
    //   eventsClicked.value = true;
    // };

    // const hideEvents = () => {
    //   eventsClicked.value = false;
    // };

    // const showContact = () => {
    //   contactClicked.value = true;
    // };

    // const hideContact = () => {
    //   contactClicked.value = false;
    // };

    // const navigate = (page) => {
    //   if (page === 'home') {
    //     window.scrollTo({ top: 0, behavior: 'smooth' });
    //   } else if (page === 'login') {
    //     router.push({ name: 'LoginPageView' }); // Navigate to LoginPageView.vue
    //   } else if (page === 'events') {
    //     // if (eventsSection.value) {
    //       eventsSection.value.scrollIntoView({ behavior: 'smooth', block: 'start' });
    //       // eventsSection.value = false;
    //     // }
    //   } else if (page === 'contact') {
    //     // if (contactSection.value) {
    //       contactSection.value.scrollIntoView({ behavior: 'smooth', block: 'start' });
    //       // eventsSection.value=false;
    //     // }
    //   }
    // };
    const navigate = (page) => {
      let targetSection = null;

      switch (page) {
        case 'login':
          router.push({ name: 'LoginPageView' }); // Navigate to LoginPageView.vue
          break;
        case 'events':
          targetSection = eventsSection;
          break;
        case 'contact':
          targetSection = contactSection;
          break;
        case 'home':
          window.scrollTo({ top: 0, behavior: 'smooth' });
          break;
        default:
          break;
      }

      if (targetSection && targetSection.value) {
        targetSection.value.scrollIntoView({ behavior: 'smooth', block: 'start' });
      }
    };




    // watch(eventsClicked, (newVal) => {
    //   if (newVal && eventsSection.value) {
    //     eventsSection.value.scrollIntoView({ behavior: 'smooth', block: 'start' });
    //   }
    // });

    // watch(contactClicked, (newVal2) => {
    //   if (newVal2 && contactSection.value) {
    //     contactSection.value.scrollIntoView({ behavior: 'smooth', block: 'start' });
    //   }
    
    // });

    const handleEventClick = (event) => {
      // Check if the user is logged in
      // If not logged in, prompt the user to log in
      if (!isLoggedIn()) {
        alert('Please login first.');
      } else {
        // Proceed with event handling logic
        // For example, navigate to a detailed event view
        router.push({ name: 'IntoEventView', params: { id: event.id } });
      }
    };

    const isLoggedIn = () => {
      // Check if the user is logged in
      // You can implement your authentication logic here
      // For demonstration purposes, returning true
      return false; // Change this to your authentication check
    };


    

    return {
      // eventsClicked,
      // contactClicked,
      // showContact,
      // hideContact,
      // showEvents,
      // hideEvents,
      navigate,
      handleEventClick,
      EventsList: [
        { id: 1, title: 'Event 1', date: '2024-03-01', des: 'this is the event description', img: 'https://www.sportshub.com.sg/sites/default/files/2023-06/Event%20Hero%20Banner%201200-675%20%E2%94%90%E2%95%9C%E2%96%92%E2%94%A4_1.jpg' },
        { id: 2, title: 'Event 2', date: '2024-03-15', des: 'this is the event description' , img: 'https://www.sportshub.com.sg/sites/default/files/2024-02/1200x675.png'},
        { id: 3, title: 'Event 3', date: '2024-03-20', des: 'this is the event description', img: 'https://www.sportshub.com.sg/sites/default/files/2024-01/SH2-BrunoMars-Event%20Hero%20Banner_0.jpg' },
        { id: 4, title: 'Event 4', date: '2024-03-25', des: 'this is the event description' , img: 'https://www.sportshub.com.sg/sites/default/files/2023-11/Event%20Hero%20Banner%201200x675pxKeyArt.jpg'},
        // Add more events as needed
      ],
      eventsSection,
      contactSection,
      sectionInView,
    


    };
  }
};
</script>

<style>
.event-grid {
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
@import '../components/Navbar.css';

.home{
  margin-top: 70px;
}
</style>
