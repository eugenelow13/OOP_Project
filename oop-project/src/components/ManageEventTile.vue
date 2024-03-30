<template>
  <div class="event-tile">
      <img :src="event.img" alt="Event Image">
      <p class="event-type">{{ event.type }}</p>
      <div class="event-info">
          <h3>{{ event.name }}</h3>
          <p>{{ event.date }}</p>
          <button @click="manageEvent(event)">Manage Event</button>
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
.event-type {
  position: absolute; /* Position the event type tag absolutely */
  background-color: rgb(79, 153, 183);
  color: #333;
  padding: 2px 6px;
  border-bottom-right-radius: 4px;
  font-size:14px;
}
</style>
