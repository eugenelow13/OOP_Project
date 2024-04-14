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
          <input type="text" id="eventName" v-model="newEvent.name">
        </div>
        <div class="form-group">
          <label for="eventType">Type:</label>
          <input type="text" id="eventType"  v-model="newEvent.type">
        </div>
        <div class="form-group">
          <label for="eventImg">Image URL:</label>
          <input type="url" id="eventImg" :style="{width:50+'vw'}"  v-model="newEvent.imageUrl">
        </div>
        <div class="form-group">
          <label for="eventDes">Description:</label>
          <textarea id="eventDescription" :style="{width:50+'vw',height:200+'px'}" v-model="newEvent.description"></textarea>
        </div>
        <div class="form-group">
          <label for="eventVenue">Venue:</label>
          <input type="text" id="eventVenue" v-model="newEvent.venue">
        </div>
        <div class="form-group">
          <label for="eventDate">Date:</label>
          <input type="date" id="eventDate" v-model="newEvent.date" :min="minDate">
        </div>
        
        <div class="form-group">
          <label for="eventTime">Time:</label>
          <input type="time" id="eventTime" v-model="newEvent.time" :min="minTime" :max="maxTime">
        </div>

        <div class="form-group">
          <label for="eventTicketPrice">Tickets Available:</label>
          <input min="0" type="number" id="eventTicketPrice" v-model="newEvent.ticketsAvailable">
        </div>

        <div class="form-group">
          <label for="eventTicketPrice">Ticket Price:</label>
          <input type="number" id="eventTicketPrice" v-model="newEvent.ticketPrice">
        </div>

        <div class="form-group">
          <label for="eventCancellationFee">Cancellation Fee:</label>
          <input type="number" id="eventCancellationFee" v-model="newEvent.cancellationFee">
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
      
    </div>
  </div>
</template>

<script>
import EventManagerNav from '@/components/EventManagerNav.vue';
import EventTile from '@/components/EventTile.vue';
import router from '@/router';
import { ref, computed } from 'vue';

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
    const newEvent = ref({});
    const navigate = (page) => {

      switch (page) {
        
        case 'profile':
          router.push({ name: 'ProfileView' }); // Navigate to ProfileView.vue
          break;
        case 'stats':
          router.push({name:'StatisticsView'});
            break;
        case 'create':
          window.scrollTo({ top: 0, behavior: 'smooth' });
          break;
        case 'manage':
          router.push({name: 'EventManagerView'})
          break;
        case 'newTicketOfficer':
          router.push({name:'AddTicketingOfficer'})
          break;
        default:
          break;
      }
    };

    const minDate = computed(() => {
      const today = new Date();
      const year = today.getFullYear();
      let month = today.getMonth() + 1;
      let day = today.getDate();
      if (month < 10) month = `0${month}`;
      if (day < 10) day = `0${day}`;
      return `${year}-${month}-${day}`;
    });

    newEvent.value.customerAttendance = 0;
    newEvent.value.eventStatus = "PLANNED";
    const responseData = ref(null);

    const submitForm = async() => {
      const datetimeString = `${newEvent.value.date}T${newEvent.value.time}`;

      // Set the date value to the concatenated datetime string
      // newEvent.value.date = new Date(datetimeString).toISOString().slice(0, -1);
      newEvent.value.date = datetimeString;

      try {
        const response = await fetch("http://localhost:8080/api/events",{
          method: 'POST',
          headers:{
            'Content-Type': 'application/json',

          },
          body: JSON.stringify(newEvent.value),
        });
        responseData.value = await response.json();
        if (response.ok){
          window.confirm(responseData.value.message);
          console.log(responseData.value);
        }else{
          //Handle error response
        }
      } catch(error){
        console.error('Error',responseData.value.error);
      }
    }

    return{
      newEvent,
      navigate,
      showingPreview,
      submitForm,
      minDate,
    };
  },

  methods: {
    
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
