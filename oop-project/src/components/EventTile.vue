<!-- EventTile.vue -->
<template>
    <div class="event-tile">
        <img :src="event.image" alt="Event Image">
        <h3>{{ event.title }}</h3>
        <p>{{ event.date }}</p>
        <router-link :to="{ name: 'EventPage', params: { eventId: event.id},props: { event:event } }">
          <button>Buy Ticket</button>
        </router-link>
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
export default{
  props:['event'],
  name: 'EventTile',
  created() {
      console.log('Event prop in EventTile:', this.event);
    }
  };
</script>

<style scoped>
.event-tile {
  border: 1px solid #ddd;
  padding: 15px;
  margin: 10px;
  text-align: center;
  flex-basis: calc(25%);
  box-sizing: border-box;
  background-color:azure;
  color:black;
  border-radius:10px;
}

.event-tile img {
  max-width: 100%;
  height: auto;
}
</style>