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
          <input type="url" id="eventImg" :style="{width:50+'vw'}"  @input="newEvent.img = $event.target.value">
        </div>
        <div class="form-group">
          <label for="eventDes">Description:</label>
          <input type="text" id="eventDes" :value="newEvent.des" :style="{width:50+'vw',height:200+'px'}" @input="newEvent.des = $event.target.value">
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
        <button class ="previewButton" type="button" @click="showPreview">Show Preview</button>
        <button class="submitButton" type="submit">Create Event</button>
      </form>
    </div>
    <div v-if=showingPreview class="preview">
      <h2>Preview</h2>
      <p>This is how your event wil be displayed on the Events page</p>
      <div class="tile">
        <EventTile :event="newEvent"/>
      </div>
    </div>
  </div>
</template>

<script>
import EventManagerNav from '@/components/EventManagerNav.vue';
import EventTile from '@/components/EventTile.vue';
import router from '@/router';
import { ref } from 'vue';

export default {
  name: 'CreateEventView',
  components:{
    EventManagerNav,
    EventTile,
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

.form-group {
  margin-bottom: 15px;
  
}
.form-group input{
  border-color:orange;
}

.form-group label {
  display: block;
}
.create-event button{
  background-color: orange;
}
.preview{
  flex:1;
  margin-top: 70px;
}
.tile{
  margin:auto;
  width:25vw;
}
.previewButton{
  margin-right: 10px;
}




</style>
