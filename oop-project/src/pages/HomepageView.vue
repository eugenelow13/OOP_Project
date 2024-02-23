<!-- src/pages/HomepageView.vue -->
<template>
  <div>
    <div class="navbar">
      <NavbarComponent /> <!-- Include the NavbarComponent.vue component here -->
    </div>
    <!-- Homepage content -->
    <div class = "content">
      <div>
        <h1>Events</h1>
      </div> 
      <div class="event-grid">      
        <EventTile v-for="event in EventsList" :key="event.title" :event = "event"/>
      </div>
    </div>
  </div>  
</template>

<script>
import EventTile from '../components/EventTile.vue';
import NavbarComponent from '../components/NavbarComponent.vue'; // Import the NavbarComponent.vue component

export default {
  name: 'HomepageView', // Update the component name
  components: {
    NavbarComponent, // Register the NavbarComponent.vue component
    EventTile
  },
  setup() {
      // const event = ref({ id:1,title: 'Event 1', date: '2024-03-01' }, event)
    return{
      
      EventsList: [
        { title: 'Event 1', date: '2024-03-01',des:'this is the event description'},
        { title: 'Event 2', date: '2024-03-15',des:'this is the event description'},
        { title: 'Event 3', date: '2024-03-20',des:'this is the event description'},
        { title: 'Event 4', date: '2024-03-25',des:'this is the event description'}
        // Add more events as needed
      ]
      
    }
  }
}
</script>

<style>
.event-grid{
    /* margin-left:120px; */
    display : flex;
    flex-wrap: wrap;
  }
.content{
  width:100vw;
}
/* Component-specific styles */
/* Import Navbar.css */
@import '../components/Navbar.css';
</style>
