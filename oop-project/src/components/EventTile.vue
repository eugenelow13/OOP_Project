<template>
  <router-link :to="'/intoeventview/' + event.id" class="event-tile">
    <img :src="event.img" alt="Event Image">
    <h3>{{ event.title }}</h3>
    <p>{{ event.date }}</p>
    <button @click="buyTicket">Buy Ticket</button>
  </router-link>
</template>

<script>
export default {
  props: ['event'],
  name: 'EventTile',
  created() {
    console.log('Event prop in EventTile:', this.event);
  },
  methods: {
    buyTicket() {
      alert(`Ticket for ${this.event.title} purchased!`);
    }
  }
};
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
