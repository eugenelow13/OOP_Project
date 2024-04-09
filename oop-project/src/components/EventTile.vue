<template>
  <div v-if="event.eventStatus != 'CANCELLED'" class="event-tile">
      <img :src="event.imageUrl" alt="Event Image">
      <p class="event-type">{{ event.type }}</p>
      <div class="event-info">
          <h3>{{ event.name }}</h3>
          <p>{{ event.date }}</p>
          <button @click="buyTicket">Buy Ticket</button>
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
  name: 'EventTile',
  created() {
    console.log('Event prop in EventTile:', this.event);
  },
  computed: {
    defaultImageUrl() {
      // Set a default image URL if event.imageUrl is null
      return this.event.imageUrl || "https://img.freepik.com/free-photo/glowing-stage-light-illuminates-cheering-rock-fans-generated-by-ai_188544-37983.jpg?w=1800&t=st=1712506661~exp=1712507261~hmac=25a0a50261c1947373e3c0728407f953b049f6511a12b14320d957dda48f1667";
    }
  },
  methods: {
    buyTicket() {
      this.$emit('buyTicket', this.event);
      if (!this.$attrs.onBuyTicket){
      // Navigate to the route with event information as parameters
        this.$router.push({ 
          name: 'IntoEventView', 
          params: { 
            eventId: this.event.id, // Event ID
            eventTitle: this.event.name, // Event Title
            eventDesc: this.event.description, // Event Description
            eventDate: this.event.date, // Event Date
            eventImg: this.defaultImageUrl // Use the default image URL
          }
        });
      }
    }
  }
};
</script>




<style scoped>
.event-tile {
  width:200px; 
  height:400px;
  background-color: #fff; /* Set background color to white */
  border: 1px solid #ccc; /* Add border for better visibility */
  padding: 10px;
  margin: 30px;
  text-align: center;
  flex-basis: calc(20%);
  box-sizing: border-box;
  background-color: azure;
  color: black;
  border-radius: 5px;
  cursor:pointer;
  display:flex;
  flex-direction: column;
  justify-content: space-between;
}
.event-tile h3 {
  margin: 0; /* Remove default margin for heading */
  font-size:20px;
}

.event-tile p {
  margin: 10px 0; /* Add margin for paragraphs */
}

.event-tile img {
  max-width: 100%;
  height: auto;
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
}

.event-tile button{
  background-color: turquoise;
  border:none;
  color:black;
  border-radius: 5px;
  cursor:pointer;
  transition: background-color 0.3s;
  padding:10px 20px;
  margin-bottom:10px;
  font-weight: bold;
}
.event-tile:hover{
  transform:scale(1.01);
}


.event-tile button:hover{
  background-color: rgb(49, 168, 156);
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
  width: 200px;
  height: 400px; /* Adjust the height of the tile */
  background-color: #fff; /* Set background color to white */
  border: 1px solid #ccc; /* Add border for better visibility */
  padding: 10px;
  margin: 20px;
  text-align: center;
  flex-basis: calc(20%);
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
