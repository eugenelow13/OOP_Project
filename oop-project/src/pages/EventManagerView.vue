<template>
    <div>
        <div class="eventManagerNav">
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
            <EventManagerNav @navigate="navigate"/> <!-- Include the AfterLoginNav.vue component here -->
        </div>
        <!-- Events Section -->
        <div class="events" ref="eventsSection">
            <div>
                <h1>Events</h1>
            </div> 
            <FilterEvent :EventsList = "EventsList" @confirmFilter="handleFilter"/>
            <div class="event-grid">   
                <ManageEventTile v-for="event in filteredEvents" :key="event.name" :event="event" @manage="manage" />
                <div class="event-grid" v-if="!filteredEvents">
                  <ManageEventTile v-for="event in EventsList" :key="event.name" :event="event" @manage="manage"/>
                </div>
            </div>
        </div>

        <div v-if=showManage class="manageEvent" ref="manageSection">
            <ManageEvent :managedEvent="managedEvent" @update-event="updateEvent"/>
        </div>



    </div>
</template>

<script>
import EventManagerNav from '../components/EventManagerNav.vue';
import ManageEventTile from '../components/ManageEventTile.vue';
import {ref} from 'vue';
import ManageEvent from '../components/ManageEvent.vue';

import router from '../router'; // Import the router instance
import FilterEvent from '../components/FilterEvent.vue';


export default{
    name: 'EventManagerView',
    components:{
        EventManagerNav,
        ManageEventTile,
        ManageEvent,
        FilterEvent,
    },

    setup(){
    
    const showManage = ref(false);
    const managedEvent = ref(null);
    const manageSection = ref(null);
    const filteredEvents = ref(null);

    const handleFilter = (events) => {
      filteredEvents.value=events;
    }

    const navigate = (page) => {

      switch (page) {
        
        case 'profile':
          router.push({ name: 'ProfileView' }); // Navigate to ProfileView.vue
          break;
        case 'stats':
            break;
        case 'create':
            router.push({ name: 'CreateEventView'}); //Navigate to CreateEvent.vue
          break;
        case 'manage':
          window.scrollTo({ top: 0, behavior: 'smooth' });
          break;
        default:
          break;
      }
    }

    const manage = (event) => {
        showManage.value = true;
        managedEvent.value = event;
        console.log(manageSection.value);
          if (manageSection.value){
            manageSection.value.scrollIntoView({ behavior: 'smooth', block: 'start'});
          }
    }
      

    return{
        navigate,
        manage,
        showManage,
        managedEvent,
        manageSection,
        EventsList: [
        { 
          id: 1, 
          name: 'Event 1', 
          type: 'Concert', 
          img: 'https://www.sportshub.com.sg/sites/default/files/2023-06/Event%20Hero%20Banner%201200-675%20%E2%94%90%E2%95%9C%E2%96%92%E2%94%A4_1.jpg',
          des: 'this is the event description',
          venue:'Concert Hall',  
          date: '2024-03-01', 
          ticketPrice: 50.0,cancellationFee:10.0,ticketsAvailable:98,customerAttendance:0,eventStatus:"planned" 
        },

        { 
          id: 2, 
          name: 'Event 2', 
          type: 'Sports', 
          img: 'https://www.sportshub.com.sg/sites/default/files/2024-02/1200x675.png',
          des: 'this is the event description',
          venue:'Sports Hub',  
          date: '2024-03-15', 
          ticketPrice: 50.0,cancellationFee:10.0,ticketsAvailable:98,customerAttendance:0,eventStatus:"planned" 
        },
        { 
          id: 3, 
          name: 'Event 3', 
          type: 'Concert', 
          img: 'https://www.sportshub.com.sg/sites/default/files/2024-01/SH2-BrunoMars-Event%20Hero%20Banner_0.jpg',
          des: 'this is the event description',
          venue:'Concert Hall',  
          date: '2024-03-20', 
          ticketPrice: 50.0,cancellationFee:10.0,ticketsAvailable:98,customerAttendance:0,eventStatus:"planned" 
        },
        { 
          id: 4, 
          name: 'Event 4', 
          type: 'Theatre', 
          img: 'https://www.sportshub.com.sg/sites/default/files/2023-11/Event%20Hero%20Banner%201200x675pxKeyArt.jpg',
          des: 'this is the event description',
          venue:'Theatre Hall',  
          date: '2024-03-15', 
          ticketPrice: 50.0,cancellationFee:10.0,ticketsAvailable:98,customerAttendance:0,eventStatus:"planned" 
        },
        // Add more events as needed
      ],
      handleFilter,
      filteredEvents,
    }
    },
  methods:{
    updateEvent(editedEvent){
      const index = this.EventsList.findIndex(event => event.id === editedEvent.id);
      if (index !== -1) {
      // Update the event object in the EventsList array
      this.EventsList.splice(index, 1, editedEvent);
      }
      console.log("event updated");
      // Hide the ManageEvent section after updating the event
      this.showManage = false;
      }
  }
    
}


</script>

<style>
.events{
    margin-top: 70px;
}

</style>