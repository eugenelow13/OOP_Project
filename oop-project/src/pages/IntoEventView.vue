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
      <div class="col-md-12 text-center"> 
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
                <router-link :to="'/payment?eventImg=' + $route.params.eventImg"> <!-- Adjust the route to include eventImg parameter -->
                  <button class="btn btn-primary" type="button">Buy Ticket</button>
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
// import PaymentView from 'PaymentView.vue'; // Adjust the path based on your project structure
import 'bootstrap/dist/css/bootstrap.min.css';
import PopupComponent from '../components/PopupComponent.vue'; // Adjust the path based on your project structure

export default {
  name: 'IntoEventView',
  // Component options here
  components: {
    PopupComponent
  },
  data() {
    return {
      selectedNumber: null,
      numbers: [1, 2, 3, 4, 5],
      eventImg: '',
      showModal: false,
    };
  },
  created() {
    this.eventImg = this.$route.params.eventImg;
  },
  methods: {
    test() {
      console.log(this.$route.params.eventImg);
      console.log(this.$route.params.eventTitle);
      console.log(this.$route.params.eventDesc);
      console.log(this.$route.params.eventDate);
    },
    seatingplan() {
      const imgSrc = this.$route.params.eventImg;
      console.log(imgSrc);
      this.showModal = true;
    }

  }
}
</script>

<style scoped>
.image-container img {
  max-width: 50%; /* Ensure the image fits within its container */
  margin: auto; /* Center the image horizontally */
}
</style>
