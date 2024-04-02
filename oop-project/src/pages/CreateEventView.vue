<template>
  <div class="createEventPage">
    <div class="eventManagerNav">
      <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
      <EventManagerNav @navigate="navigate"/> <!-- Include the AfterLoginNav.vue component here -->
    </div>
    <div class="create-event">
      <h2>Create Event</h2>
      <form @submit.prevent="submitForm">
        <div class="form-group">
          <label for="eventName">Name:</label>
          <input type="text" id="eventName" @input="newEvent.name = $event.target.value">
        </div>
        <div class="form-group">
          <label for="eventType">Type:</label>
          <input type="text" id="eventType"  @input="newEvent.type = $event.target.value">
        </div>
        <div class="form-group">
          <label for="eventImg">Image URL:</label>
          <input type="url" id="eventImg" :style="{width:50+'vw'}"  @input="newEvent.imageUrl = $event.target.value">
        </div>
        <div class="form-group">
          <label for="eventDes">Description:</label>
          <textarea id="eventDes" :value="newEvent.des" :style="{width:50+'vw',height:200+'px'}" @input="newEvent.des = $event.target.value"></textarea>
        </div>
        <div class="form-group">
          <label for="eventVenue">Venue:</label>
          <input type="text" id="eventVenue" :value="newEvent.venue" @input="newEvent.venue = $event.target.value">
        </div>
        <div class="form-group">
          <label for="eventDate">Date:</label>
          <input type="date" id="eventDate" :value="newEvent.date" @input="newEvent.date = $event.target.value">
        </div>
        <div class="form-group">
          <label for="eventTicketPrice">Ticket Price:</label>
          <input type="number" id="eventTicketPrice" :value="newEvent.ticketPrice" @input="newEvent.ticketPrice = $event.target.value">
        </div>
        <div class="form-group">
          <label for="eventCancellationFee">Cancellation Fee:</label>
          <input type="number" id="eventCancellationFee" :value="newEvent.cancellationFee" @input="newEvent.cancellationFee = $event.target.value">
        </div>
        <!-- Add similar text boxes for other event parameters -->
        <button class="submitButton" type="submit">Create Event</button>
        <button class ="previewButton" type="button" @click="showPreview">Show Preview</button>
      </form>
    </div>
    <div v-if=showingPreview class="preview">
      <h2>Preview</h2>
      <p>This is how your event wil be displayed on the Events page</p>
      <div class="tile">
        <EventTile :event="newEvent"/>
      </div>
      <p>This is how your event page will look like</p>
      <div class="previewPage">
        <!-- <IntoEventView :eventId="newEvent.id" :eventTitle="newEvent.name" :eventDesc="newEvent.des" :eventDate="newEvent.date" :eventImg="newEvent.imageUrl"/> -->
      </div>
    </div>
  </div>
</template>

<script>
import EventManagerNav from '@/components/EventManagerNav.vue';
import EventTile from '@/components/EventTile.vue';
import router from '@/router';
import { ref } from 'vue';
// import IntoEventView from './IntoEventView.vue';

export default {
  name: 'CreateEventView',
  components:{
    EventManagerNav,
    EventTile,
    // IntoEventView,
  },

  setup(){
    const showingPreview = ref(false);
    const navigate = (page) => {

      switch (page) {
        
        case 'profile':
          router.push({ name: 'ProfileView' }); // Navigate to ProfileView.vue
          break;
        case 'stats':
            break;
        case 'create':
          window.scrollTo({ top: 0, behavior: 'smooth' });
          break;
        case 'manage':
          router.push({name: 'EventManagerView'})
          break;
        default:
          break;
      }
    }

    return{
      newEvent:{},
      navigate,
      showingPreview,
    };
  },

  methods: {
    submitForm(){
      console.log("new event created") //put the new event into database here
    },
    showPreview(){
      this.showingPreview=true;
      window.scrollTo({ top: 0, behavior: 'smooth' });
    }
  }
}
</script>




<style scoped>
.createEventPage{
  display: flex;
}
.create-event{
  flex:1;
  margin-left:10px;
  margin-top:70px;
}
.create-event h2{
  margin-bottom:20px;
}

.form-group {
  margin-bottom: 20px;
  text-align: left;
  
}
.form-group input,
.form-group textarea{
  padding:10px;
  border: 1px solid;
  border-radius: 5px;
  
}

.form-group textarea{
  resize:vertical;
}
.form-group label {
  display: block;
  font-weight:bold;
  margin-bottom: 5px;
}
.create-event button {
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
  margin-bottom: 10px;
}
.previewButton{
  background-color: rgb(2, 180, 2);
}
.previewButton:hover {
  background-color: green
}
.submitButton{
  background-color: orange;
  margin-right:10px;
}
.submitButton:hover{
  background-color: rgb(172, 113, 2);
}

.preview{
  flex:1;
  margin-top: 70px;
}
.tile{
  margin:auto;
  width:25vw;
}




</style>
