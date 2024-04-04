<template>
  <div>
    <div class="loginnav">
      <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
      <AfterLoginNav  @navigate="navigate"/> <!-- Include the AfterLoginNav.vue component here -->
    </div>

    <!-- Homepage content -->
    <div class="home">

    <div class="carousel">
      <carousel :items-to-show="1" :wrap-around="true" :autoplay="3000">
        <slide v-for="(event, index) in EventsList" :key="index">
        <router-link :to="{ name: 'IntoEventView', params: { 
          eventId: event.id, 
          eventImg: event.imageUrl,
          eventTitle: event.name,
          eventDesc: event.desc,
          eventDate: event.date
        }}">            
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
import { onMounted, ref } from 'vue';
import EventTile from '../components/EventTile.vue';
import AfterLoginNav from '../components/AfterLoginNav.vue'; // Import the AfterLoginNav.vue component
import router from '../router'; // Import the router instance
import FilterEvent from '../components/FilterEvent.vue';

export default {
  name: 'AfterLoginView',
  components: {
    AfterLoginNav, // Replace NavbarComponent with AfterLoginNav
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
    }
  },

  setup() {
    const eventsSection = ref(null);
    const contactSection = ref(null);
    const currentSection = ref(null);
    const username = ref(''); // Define a ref for username
    const password = ref(''); // Define a ref for password
    const filteredEvents = ref(null);
    const EventsList = ref([]);
    
    const handleFilter = (events) => {
      filteredEvents.value=events;
    }

    const navigate = (page) => {
      let targetSection = null;

      switch (page) {
        case 'login':
          router.push({ name: 'LoginPageView' }); // Navigate to LoginPageView.vue
          break;
        case 'profile':
          router.push({ name: 'ProfileView' }); // Navigate to ProfileView.vue
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
      return true; // Change this to your authentication check
    };

    onMounted(() => {
    
      const token = sessionStorage.getItem('token');

      const options = {
        method: 'GET',
        headers: {
          'Authorization': `Bearer ${token}`
        }
      };

      fetch("http://localhost:8080/api/events", options)
        .then(response => {
          if (!response.ok) {
            alert('Error fetching events');
            throw new Error('Network response was not ok');
          }
          return response.json();
        })
        .then(data => {
          console.log('Events:', data);
          data.data.forEach(event => {
            const originalDate = new Date(event.date);
            const formattedDate = `${(originalDate.getMonth() + 1).toString().padStart(2, '0')}/${originalDate.getDate().toString().padStart(2, '0')}/${originalDate.getFullYear()}, ${originalDate.toLocaleString('en-US', { hour: 'numeric', minute: '2-digit', hour12: true })}`;
            event.date = formattedDate;
          });
          EventsList.value = data.data;
          console.log(EventsList)
        })
        .catch(error => {
          console.error('There was a problem with the fetch operation:', error);
        });

    });


    return {
      navigate,
      isLoggedIn,
      username, // Expose username to the template
      password, // Expose password to the template
      EventsList,
      /*
      EventsList: [
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
      */
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
/* Component-specific styles */
/* Import Navbar.css */
@import '../components/Navbar.css';

/* Adjust the styles of the individual event tiles */
.event-tile {
  width: 300px; /* Adjust the width of the tile */
  height: 400px; /* Adjust the height of the tile */
  background-color: #fff; /* Set background color to white */
  border: 1px solid #ccc; /* Add border for better visibility */
  border-radius: 5px; /* Add border-radius for rounded corners */
  padding: 20px; /* Add padding inside the tile */
  box-sizing: border-box; /* Ensure padding doesn't increase the size of the tile */
  display: flex; /* Use flexbox for layout */
  flex-direction: column; /* Stack contents vertically */
  justify-content: space-between; /* Vertically center contents */
}

/* Optionally, you can adjust the styles of the title, description, etc. */
.event-tile h3 {
  margin: 0; /* Remove default margin for heading */
}

.event-tile p {
  margin: 10px 0; /* Add margin for paragraphs */
}

/* Adjust the layout of the event grid */
.event-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 0px;
}


</style>
