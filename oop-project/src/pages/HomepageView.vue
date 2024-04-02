<template>
  <div>
    <!-- Include the NavbarComponent.vue component here -->
    <div class="navbar">
      <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
      <NavbarComponent   @navigate="navigate"/>
    </div>

    <!-- Homepage content -->

    <div class="home">

    <div class="carousel">
      <carousel :items-to-show="1" :wrap-around="true" :autoplay="3000">
        <slide v-for="(event, index) in EventsList" :key="index">
          <router-link :to="'/intoeventview/' + event.id" >
            <img :src="event.imageUrl" style="width: 100%; height:100%; ">
          </router-link>
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
      <FilterEvent :EventsList = "EventsList" @confirmFilter="handleFilter"/>
      <div class="event-grid">   
          <EventTile v-for="event in filteredEvents" :key="event.name" :event="event" />
          <div class="event-grid" v-if="!filteredEvents">
            <EventTile v-for="event in EventsList" :key="event.name" :event="event" />
          </div>
      </div>
    </div>

    <!-- Contact Section-->
    <div class="contact" ref="contactSection">
      <h1>Contact Us</h1>
      <p>Have questions or need assistance? Feel free to reach out to us.</p>
      <form @submit.prevent="submitForm" class="contact-form">
        <div class="form-group">
          <label for="name">Your Name</label>
          <input type="text" id="name" v-model="formData.name" required>
        </div>
        <div class="form-group">
          <label for="email">Your Email</label>
          <input type="email" id="email" v-model="formData.email" required>
        </div>
        <div class="form-group">
          <label for="message">Your Message</label>
          <textarea id="message" rows="4" v-model="formData.message" required></textarea>
        </div>
        <button type="submit">Send Message</button>
      </form>
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
import FilterEvent from '../components/FilterEvent.vue';
// import { Dropdown } from 'primevue/dropdown';

export default {
  name: 'HomepageView',
  components: {
    NavbarComponent,
    EventTile,
    Carousel,
    Slide,
    Pagination,
    Navigation,
    FilterEvent,
  },
  methods: {

    submitForm() {
      // Here you can implement the logic to send the form data to your backend or handle it in any way you prefer
      console.log('Form submitted:', this.formData);
      // For demonstration purposes, let's just clear the form data after submission
      this.formData = {
        name: '',
        email: '',
        message: ''
      };
    },


  },

  setup() {

    const eventsSection = ref(null);
    const contactSection = ref(null);
    const currentSection = ref(null);
    const filteredEvents = ref(null);
    
    const handleFilter = (events) => {
      filteredEvents.value=events;
    }

    const navigate = (page) => {
      let targetSection = null;

      switch (page) {
        case 'login':
          router.push({ name: 'LoginPageView' });
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

      if (targetSection && targetSection.value && targetSection.value != currentSection.value) {
        targetSection.value.scrollIntoView({ behavior: 'smooth', block: 'start' });
        console.log(targetSection.value);
        currentSection.value = targetSection.value;
        console.log(currentSection.value);
      }
    };


    const isLoggedIn = () => {
      // Check if the user is logged in
      // You can implement your authentication logic here
      // For demonstration purposes, returning true
      return false; // Change this to your authentication check
    };


    

    return {
      navigate,
      isLoggedIn,
      EventsList:[
      { 
          id: 1, 
          name: 'Event 1', 
          type: 'Concert', 
          imageUrl: 'https://www.sportshub.com.sg/sites/default/files/2023-06/Event%20Hero%20Banner%201200-675%20%E2%94%90%E2%95%9C%E2%96%92%E2%94%A4_1.jpg',
          des: 'this is the event description',
          venue:'Concert Hall',  
          date: '2024-03-01', 
          ticketPrice: 50.0,cancellationFee:10.0,ticketsAvailable:98,customerAttendance:0,eventStatus:"planned" 
        },

        { 
          id: 2, 
          name: 'Event 2', 
          type: 'Sports', 
          imageUrl: 'https://www.sportshub.com.sg/sites/default/files/2024-02/1200x675.png',
          des: 'this is the event description',
          venue:'Sports Hub',  
          date: '2024-03-15', 
          ticketPrice: 50.0,cancellationFee:10.0,ticketsAvailable:98,customerAttendance:0,eventStatus:"planned" 
        },
        { 
          id: 3, 
          name: 'Event 3', 
          type: 'Concert', 
          imageUrl: 'https://www.sportshub.com.sg/sites/default/files/2024-01/SH2-BrunoMars-Event%20Hero%20Banner_0.jpg',
          des: 'this is the event description',
          venue:'Concert Hall',  
          date: '2024-03-20', 
          ticketPrice: 50.0,cancellationFee:10.0,ticketsAvailable:98,customerAttendance:0,eventStatus:"planned" 
        },
        { 
          id: 4, 
          name: 'Event 4', 
          type: 'Theatre', 
          imageUrl: 'https://www.sportshub.com.sg/sites/default/files/2023-11/Event%20Hero%20Banner%201200x675pxKeyArt.jpg',
          des: 'this is the event description',
          venue:'Theatre Hall',  
          date: '2024-03-15', 
          ticketPrice: 50.0,cancellationFee:10.0,ticketsAvailable:98,customerAttendance:0,eventStatus:"planned" 
        },
        // Add more events as needed
      ],
      eventsSection,
      contactSection,
      currentSection,
      formData: {
        name: '',
        email: '',
        message: ''
      },
      handleFilter,
      filteredEvents,
      
      // selectedEventType,
      // filteredEvents,
    


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
  margin: auto;
  
  
}
/* Component-specific styles */
@import '../components/Navbar.css';

.home{
  margin-top: 70px;
}

.events {
  text-align: center;
  background-color:#f9f9f9;
  margin-top: 10px;
}


.contact {
  background-color: #f9f9f9;
  padding: 40px;
  text-align: center;
  margin-top: 10px;
}

.contact h1 {
  margin-bottom: 10px;
}

.contact p {
  color: #666;
  margin-bottom: 30px;
}

.contact-form .form-group {
  margin-bottom: 20px;
  text-align: left;
}

.contact-form label {
  display: block;
  font-weight: bold;
  margin-bottom: 5px;
}

.contact-form input[type="text"],
.contact-form input[type="email"],
.contact-form textarea {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.contact-form textarea {
  resize: vertical;
}

.contact-form button {
  padding: 10px 20px;
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.contact-form button:hover {
  background-color: #0056b3;
}


</style>
