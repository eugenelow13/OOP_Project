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
            <FilterEvent v-if="EventsList.length" :events = "EventsList" @confirmFilter="handleFilter"/>
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
import {onMounted, ref} from 'vue';
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
    const EventsList = ref([]);
    

    const handleFilter = (events) => {
      filteredEvents.value=events;
    }

    const navigate = (page) => {

      switch (page) {
        
        case 'profile':
          router.push({ name: 'ProfileView' }); // Navigate to ProfileView.vue
          break;
        case 'stats':
          router.push({name:'StatisticsView'});
            break;
        case 'create':
            router.push({ name: 'CreateEventView'}); //Navigate to CreateEvent.vue
          break;
        case 'manage':
          window.scrollTo({ top: 0, behavior: 'smooth' });
          break;
        case 'newTicketOfficer':
          router.push({name:'AddTicketingOfficer'});
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

      onMounted(() => {
    
      const token = sessionStorage.getItem('token');

      const options = {
        method: 'GET',
        headers: {
          'Authorization': `Bearer ${token}`
        }
      };

      fetch("http://localhost:8080/api/events", options)
        .then(response => {
          if (!response.ok) {
            alert('Error fetching events');
            throw new Error('Network response was not ok');
          }
          return response.json();
        })
        .then(data => {
          console.log('Events:', data);
          data.data.forEach(event => {
            const originalDate = new Date(event.date);
            const formattedDate = `${(originalDate.getMonth() + 1).toString().padStart(2, '0')}/${originalDate.getDate().toString().padStart(2, '0')}/${originalDate.getFullYear()}, ${originalDate.toLocaleString('en-US', { hour: 'numeric', minute: '2-digit', hour12: true })}`;
            event.date = formattedDate;
          });
          EventsList.value = data.data;
          console.log(EventsList)
        })
        .catch(error => {
          console.error('There was a problem with the fetch operation:', error);
        });
     });

      

    return{
        navigate,
        manage,
        showManage,
        managedEvent,
        manageSection,
        EventsList,
      handleFilter,
      filteredEvents,
    }
    },
  methods:{
    // updateEvent(editedEvent){
    //   const index = this.EventsList.findIndex(event => event.id === editedEvent.id);
    //   if (index !== -1) {
    //   // Update the event object in the EventsList array
    //   this.EventsList.splice(index, 1, editedEvent);
    //   }
    //   console.log("event updated");
    //   // Hide the ManageEvent section after updating the event
    //   this.showManage = false;
    //   }
  }
    
}


</script>

<style>
.events{
    margin-top: 70px;
}

.event-grid {
  display: flex;
  flex-wrap:wrap;
  align-items: center; 
  justify-content:center;
}

</style>