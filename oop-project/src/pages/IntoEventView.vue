<template>
  <div class="container">
    <!-- First Row -->
    <div class="row">
      <div class="col-md-12">
        <router-link to="/afterlogin" class="btn btn-secondary mb-3">Back</router-link>
      </div>
    </div>

    <!-- Second Row -->
    <div class="row">
      <!-- Image Container -->
      <div class="col-md-12 text-center" v-if="eventImg"> <!-- Only render if eventImg is not null -->
        <div class="image-container">
          <img :src="$route.params.eventImg" alt="Event Image">
        </div>
      </div>
    </div>

    <!-- Third Row -->
    <div class="row">
      <!-- Event Details -->
      <div class="col-md-12">
        <div class="container-xl px-4 mt-4">
          <div class="card mb-4 mb-xl-0" style="text-align:center"> 
            <div class="card-header">
              <h2>Event Details</h2>
            </div>

            <!-- Card Body -->
            <div class="card-body row">
              <!-- Left Side -->
              <div class="col-md-6" style="text-align:left;">
                <p><strong>Event ID:</strong> {{ $route.params.eventId }}</p> 
                <p><strong>Event Title:</strong> {{ $route.params.eventTitle }}</p>
                <p><strong>Event Description:</strong> {{ $route.params.eventDesc }}</p>
                <p><strong>Event Date:</strong> {{ $route.params.eventDate }}</p>
              </div>

              <div class="col-md-6" style="text-align:right;">
                <button @click=seatingplan() class="btn btn-secondary mb-3" >View Seating Plan</button>
              </div>

              <!-- Modal Component -->
              <PopupComponent :show="showModal" @close="showModal = false"/>
              
              <div class="card-header">
                <h2>Buy Your Tickets!</h2>
              </div>

              <div class="col-md-6" style="text-align:left;">
                <p>
                  <strong>Number of tickets (maximum 5):</strong>
                  <select v-model="selectedNumber">
                    <option v-for="number in numbers" :key="number" :value="number">{{ number }}</option>
                  </select>
                </p>    
                <router-link v-if="eventImg" :to="'/payment?eventImg=' + $route.params.eventImg"> <!-- Only render if eventImg is not null -->
                  <button @click.prevent="book" class="btn btn-primary" type="button">Buy Ticket</button>
                </router-link>
              </div>

            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue'; // Import ref from Vue
import 'bootstrap/dist/css/bootstrap.min.css';
import PopupComponent from '../components/PopupComponent.vue'; // Adjust the path based on your project structure

export default {
  name: 'IntoEventView',
  // Component options here
  components: {
    PopupComponent
  },
  setup() {
    const eventId = ref('');
    const eventImg = ref('');
    const eventTitle = ref('');
    const eventDesc = ref('');
    const eventDate = ref('');
    const selectedNumber = ref(1); // Initialize selectedNumber with 1
    const showModal = ref(false);

    const book = () => {
      console.log('Booking...');

      const requestData = {
        eventId: eventId.value,
        customerId: 1,
        tickets: [ { "noOfGuests": selectedNumber.value, "isAdmitted": false}],
        password: sessionStorage.getItem('password'),
      };

      const token = sessionStorage.getItem('token');

      console.log(requestData)
      const options = {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`,
        },
        body: JSON.stringify(requestData)
      };

      fetch("http://localhost:8080/api/bookings/placeBooking", options)
        .then(response => {
          if (!response.ok) {
            alert('Something went wrong with the booking');
            throw new Error('Network response was not ok');
          }
          return response.json();
        })
        .then(data => {
          // Handle the response data here
          console.log(data);
        })
        .catch(error => {
          // Handle errors here
          console.error('There was a problem with the fetch operation:', error);
        });
    }

    const numbers = Array.from({ length: 5 }, (_, i) => i + 1); // Array containing numbers from 1 to 5

    return {
      eventId,
      eventImg,
      eventTitle,
      eventDesc,
      eventDate,
      selectedNumber,
      showModal,
      numbers, // Pass the numbers array to the template
      book
    };
  },
  methods: {
    test() {
      console.log(this.eventId);
      console.log(this.eventImg);
      console.log(this.eventTitle);
      console.log(this.eventDesc);
      console.log(this.eventDate);
    },
    seatingplan() {
      const imgSrc = this.eventImg;
      console.log(imgSrc);
      this.showModal = true;
    }
  },
  created() {
    this.eventId = this.$route.params.eventId;
    this.eventImg = this.$route.params.eventImg;
    this.eventTitle = this.$route.params.eventTitle;
    this.eventDesc = this.$route.params.eventDesc;
    this.eventDate = this.$route.params.eventDate;
  },

}
</script>




<style scoped>
.image-container img {
  max-width: 50%; /* Ensure the image fits within its container */
  margin: auto; /* Center the image horizontally */
}
</style>
