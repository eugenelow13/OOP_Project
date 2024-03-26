<template>
    <div class="event-tile">
        <img :src="event.img" alt="Event Image">
        <h3>{{ event.name }}</h3>
        <p>{{ event.date }}</p>
        <button @click="manageEvent(event)">Manage Event</button>
    </div>

<!-- vuetify -->
  <!-- <v-card> -->
    <!-- <v-card-title>{{ event.title }}</v-card-title> -->
    <!-- <v-card-subtitle>{{ event.date }}</v-card-subtitle> -->
    <!-- <v-card-text>{{ event.description }}</v-card-text> -->
    <!-- <v-card-actions> -->
      <!-- <v-btn color="primary" @click="buyTicket()">Buy Ticket</v-btn> -->
    <!-- </v-card-actions> -->
  <!-- </v-card> -->
  
</template>

<script>
export default {
  props: ['event'],
  name: 'ManageEventTile',
  created() {
    console.log('Event prop in EventTile:', this.event);
  },
  
  methods: {
    manageEvent() {
      // Navigate to the route with event information as parameters
      this.$emit('manage',this.event)
      }
    }
}
</script>




<style scoped>
.event-tile {
  border: 1px solid #ddd;
  padding: 0px;
  margin: 10px;
  text-align: center;
  flex-basis: calc(25%);
  box-sizing: border-box;
  background-color: azure;
  color: black;
  border-radius: 10px;
}

.event-tile img {
  max-width: 100%;
  height: auto;
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
}

.event-tile button{
  background-color: orange;
  width:200px;
  height:30px;
  margin-bottom: 10px;
}
</style>
