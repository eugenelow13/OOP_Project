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

              <!-- Map Modal Component -->
              <MapComponent :show="showModal" @close="showModal = false"/>
              <!-- Buy Modal Component -->
              <BuyComponent :show="showModalbuy" @close="showModalbuy = false" @confirm="book" v-model:currentPassword="currentPassword"/>
              <!-- Purchase Status Modal Component -->
              <PurchaseComponent :show="showModalpurchase" @close="showModalpurchase = false" :purchaseMessage="purchaseMessage" />


              <div class="card-header">
                <h2>Buy Your Tickets!</h2>
              </div>

              <div class="col-md-6" style="text-align:left;">
                <label for="selectedNumber"><strong>Number of tickets (maximum 5): </strong></label>
                <select v-model="selectedNumber">
                      <option v-for="number in numbers" :key="number" :value="number">{{ number }}</option>
                </select>
                    <p>
                      <strong>Ticket Price:</strong> ${{ $route.params.eventPrice }}
                    </p>


                  <!-- <div class="form-group">

                  </div> -->
                  <button @click=handleConfirm() class="btn btn-primary" type="button">Buy Ticket</button>
                <!--<p>
                  <strong>Number of tickets (maximum 5):</strong>
                  <select v-model="selectedNumber">
                    <option v-for="number in numbers" :key="number" :value="number">{{ number }}</option>
                  </select>
                </p>-->
                
              </div>

            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, computed } from 'vue';
import 'bootstrap/dist/css/bootstrap.min.css';
import MapComponent from '../components/MapComponent.vue'; 
import BuyComponent from '../components/BuyComponent.vue';
import PurchaseComponent from '../components/PurchaseComponent.vue'; 

export default {
  name: 'IntoEventView',
  components: {
    MapComponent,
    BuyComponent,
    PurchaseComponent,
  },
  setup() {
    const eventId = ref('');
    const eventImg = ref('');
    const eventTitle = ref('');
    const eventDesc = ref('');
    const eventDate = ref('');
    const eventPrice = ref('');
    const selectedNumber = ref(1);
    const showModal = ref(false);
    const showModalbuy = ref(false);
    const purchaseMessage = ref('');
    const showModalpurchase = ref(false);
    const currentPassword = ref('');
    const password = computed({
      get: () => currentPassword.value,
      set: val => currentPassword.value = val
    });

    const numbers = Array.from({ length: 5 }, (_, i) => i + 1);

    return {
      eventId,
      eventImg,
      eventTitle,
      eventDesc,
      eventDate,
      eventPrice,
      selectedNumber,
      showModal,
      showModalbuy,
      showModalpurchase,
      purchaseMessage,
      numbers,
      currentPassword,
      password,
    };
  },
  methods: {
    book() {
      console.log('Booking...');

      const requestData = {
        eventId: this.eventId,
        customerId: 1,
        tickets: [{ "noOfGuests": this.selectedNumber, "isAdmitted": false }],
        password: this.currentPassword,
      };

      const token = sessionStorage.getItem('token');

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
            // Handle error response
            return response.json().then(errorData => {
              console.log(errorData.message); // This will log the error message from the server
              this.purchaseMessage = errorData.message;
              this.showModalpurchase = true;
              throw new Error('Network response was not ok');
            });
          }
          return response.json(); // Return the JSON content of the response
        })
        .then(data => {
          // Handle successful response
          console.log(data); // Log the data received from the server
          this.purchaseMessage = 'Email sent successfully';
          this.showModalpurchase = true;
        })
        .catch(error => {
          // Handle fetch errors
          console.error('There was a problem with the fetch operation:', error);
        });

    },
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
    },
    handleConfirm() {
      console.log('Buying ticket...');
      this.showModalbuy = true;
    },
  },
  created() {
    this.eventId = this.$route.params.eventId;
    this.eventImg = this.$route.params.eventImg;
    this.eventTitle = this.$route.params.eventTitle;
    this.eventDesc = this.$route.params.eventDesc;
    this.eventDate = this.$route.params.eventDate;
    this.eventPrice = this.$route.params.eventPrice;
  },
};
</script>

<style scoped>
.image-container img {
  max-width: 50%; /* Ensure the image fits within its container */
  margin: auto; /* Center the image horizontally */
}
</style>
