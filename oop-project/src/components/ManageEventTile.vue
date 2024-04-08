<template>
  <div v-if="event.eventStatus!='CANCELLED'" class="event-tile">
      <img :src="event.imageUrl" alt="Event Image">
      <p class="event-type">{{ event.type }}</p>
      <div class="event-info">
          <h3>{{ event.name }}</h3>
          <p>{{ event.date }}</p>
          <p>{{ event.eventStatus }}</p>
          <button @click="manageEvent(event)">Manage Event</button>
      </div>
  </div>
  <div v-if="event.eventStatus === 'CANCELLED'" class="cancelled">
    
    <img :src="event.imageUrl" alt="Event Image">
    <p class="event-type">{{ event.type }}</p>
      <div class="event-info">
          <h3>{{ event.name }}</h3>
          <p>{{ event.date }}</p>
          <h3>Event Cancelled</h3>
      </div>
  </div>
  
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
  width: 300px; /* Adjust the width of the tile */
  height: 400px; /* Adjust the height of the tile */
  background-color: #fff; /* Set background color to white */
  border: 1px solid #ccc; /* Add border for better visibility */
  padding: 20px;
  margin: 10px;
  text-align: center;
  flex-basis: calc(25%);
  box-sizing: border-box;
  background-color: azure;
  color: black;
  border-radius: 5px;
  cursor:pointer;
  display:flex;
  flex-direction: column;
  justify-content: space-between; /* Vertically center contents */
}
.event-tile h3 {
  margin: 0; /* Remove default margin for heading */
}

.event-tile p {
  margin: 10px 0; /* Add margin for paragraphs */
}

.event-tile:hover{
  transform:scale(1.01);
}

.event-tile img {
  max-width: 100%;
  height: auto;
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
}

.event-tile button{
  background-color: orange;
  border:none;
  color:black;
  border-radius: 5px;
  cursor:pointer;
  transition: background-color 0.3s;
  padding:10px 20px;
  margin-bottom:10px;
  font-weight: bold;
}
.event-tile button:hover{
  background-color: rgb(172, 113, 2);
}
.event-type {
  position: absolute; /* Position the event type tag absolutely */
  background-color: rgb(79, 153, 183);
  color: white;
  padding: 2px 6px;
  border-bottom-right-radius: 4px;
  font-size:14px;
}
.cancelled{
  width: 300px; /* Adjust the width of the tile */
  height: 400px; /* Adjust the height of the tile */
  background-color: #fff; /* Set background color to white */
  border: 1px solid #ccc; /* Add border for better visibility */
  padding: 20px;
  margin: 10px;
  text-align: center;
  flex-basis: calc(25%);
  box-sizing: border-box;
  background-color: lightgrey;
  color: black;
  border-radius: 5px;
  cursor:pointer;
  display:flex;
  flex-direction: column;
  justify-content: space-between; /* Vertically center contents */

}
</style>
