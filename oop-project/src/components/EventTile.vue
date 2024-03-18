<template>
    <div class="event-tile">
        <img :src="event.img" alt="Event Image">
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
  <router-link :to="'/intoeventview/' + event.id" class="event-tile">
    <img :src="event.image" alt="Event Image">
    <h3>{{ event.title }}</h3>
    <p>{{ event.date }}</p>
    <button @click="buyTicket">Buy Ticket</button>
  </router-link>
</template>

<script>
export default {
  props: ['event'],
  name: 'EventTile',
  methods: {
    buyTicket() {
      alert(`Ticket for ${this.event.title} purchased!`);
    },
  },
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
  background-color: skyblue;
  width:100px;
  height:30px;
  margin-top: 10px;
  margin-bottom: 10px;
}
</style>
