<template>
  <div class="container">
    <!-- First Row -->
    <div class="row">
      <div class="col-md-12">
        <router-link to="/afterlogin" class="btn btn-secondary mb-3">Back</router-link>
      </div>
    </div>

    <div>
      <button @click="test">test</button>
    </div>

    <!-- Second Row -->
    <div class="row">
      <!-- Image Container -->
      <div class="col-md-12 text-center"> <!-- Apply text-center class here -->
        <div class="image-container">
          <img :src="eventImg" alt="Event Image">
        </div>
      </div>
    </div>

    <!-- Third Row -->
    <div class="row">
      <!-- Left Column -->
      <div class="col-md-12">
        <div class="container-xl px-4 mt-4">
          <div class="card mb-4 mb-xl-0" style="text-align:center">
              <div class="card-header"><h2>Event Details</h2></div>
                <p><strong>Event ID:</strong> {{ $route.params.eventId }}</p>
                <p><strong>Event Title:</strong> {{ $route.params.eventTitle }}</p>
                <p><strong>Event Description:</strong> {{ $route.params.eventDesc }}</p>
                <p><strong>Event Date:</strong> {{ $route.params.eventDate }}</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  // Component options here
  data() {
    return {
      eventImg: ''
    };
  },
  created() {
    this.eventImg = this.$route.params.eventImg;
  },
  methods: {
    test() {
      console.log(this.$route.params.eventImg);
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
