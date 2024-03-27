<template>
    <div class="filters">
        <div class="eventTypeFilter">
          <label for="eventTypeFilter">Filter by Event Type:</label>
          <select v-model="selectedEventType" id="eventTypeFilter">
            <option value="">All</option> 
            <option v-for="(type,index) in eventTypes" :value="type" :key="index">{{ type }}</option> 
          </select>
        </div>

        <div class="eventSearchFilter">
          <label for="eventSearch">Search Events:</label>
          <input type="text" v-model="searchQuery" id="eventSearch">
        </div>
      </div>
</template>

<script>
import {ref, computed} from 'vue';


export default{ 
  name: 'FilterEvent',
  props: {
    EventsList: {
      type: Array,
      required: true
    }
  },

    setup(props){
        
        const selectedEventType = ref(""); // Initialize selectedEventType
        const eventTypes = [...new Set(props.EventsList.map(event => event.type))];
        const searchQuery = ref('');
        const filteredEvents = computed(() => {
          let filtered = props.EventsList;

          // Filter by selected event type
          if (selectedEventType.value) {
              filtered = filtered.filter(event => event.type === selectedEventType.value);
          }

          // Filter by search query
          if (searchQuery.value.trim() !== '') {
              const searchTerm = searchQuery.value.trim().toLowerCase();
              filtered = filtered.filter(event => event.name.toLowerCase().includes(searchTerm));
          }

          return filtered;
        });

    //figuring out how to pass filteredEvent into HomePage

    return{
      selectedEventType, // Return selectedEventType
      eventTypes,
      filteredEvents,
      searchQuery,
    }

    }
}
</script>

<style>
.filters{
  display:flex;
}
.eventTypeFilter{
  flex:1;
}
.eventSearchFilter{
  flex:1;
}
.eventTypeFilter select{
  margin-left:10px;
  font-size:14px;
}
.eventSearchFilter input{
  margin-left: 10px;
}
</style>