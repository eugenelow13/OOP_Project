<template>
  <button class="toggle" @click="toggleFilterMenu">advanced search</button>
          <!-- Filters content -->
        <div v-if="show" class="filter-menu">
          <div class="filters">
            <div class="eventTypeFilter">
              <label for="eventTypeFilter">Filter by Event Type:</label>
              <select v-model="selectedEventType" id="eventTypeFilter">
                <option value="">All</option> 
                <option v-for="(type,index) in eventTypes" :value="type" :key="index">{{ type }}</option> 
              </select>
            </div>
            <div class="searchAndApply">
              <div class="eventSearchFilter">
                <label for="eventSearch">Search Events:</label>
                <input type="text" v-model="searchQuery" id="eventSearch">
              </div>

              <div class="confirmFilter">
                <button type="button" @click="confirmFilter">Apply</button>
                <!-- <button type="button" @click="closeModal">Close</button> -->
              </div>
            </div>
          </div>
        </div>
</template>

<script>
import {ref, computed } from 'vue';


export default{ 
  name: 'FilterEvent',
  props: {
    EventsList: {
      type: Array,
      required: true
    }
  },
  
  

  setup(props){
        
        const show = ref(false);
        // const showModal = ref(false);
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
      const toggleFilterMenu = () => {
        show.value = !show.value;
      };
        // const showFilters =() =>{
        //   showModal.value = true;
        //   console.log(showModal.value);
        // };

        // const closeModal = () => {
        //   showModal.value = false;
        // };


        

        


    //figuring out how to pass filteredEvent into HomePage

    return{
      selectedEventType, // Return selectedEventType
      eventTypes,
      filteredEvents,
      searchQuery,
      toggleFilterMenu,
      show,
      // showFilters,
      // showModal,
      // closeModal,
    }

    },

    methods: {
      confirmFilter(){
          // Emit the 'filter-event' event with filteredEvents data
          this.$emit('confirmFilter',this.filteredEvents);
        }

    }
  }
</script>

<style>

.filter-menu{
  display:flex;
  background-color: aliceblue;
}

.toggle{
  position: absolute;
  left:12%;
  top: 65%;
  margin-bottom: 10px;
  border-top:0;
  border-right:0;
  border-left:0;
  border-color: aliceblue;
}

.filters {
  flex-direction: column;
  margin-left:12%;
}

.eventTypeFilter,.searchAndApply{
  display:flex;
  align-items: center;
  margin-bottom: 10px;
}

.eventTypeFilter select {
  font-size: 14px;
  margin-left: 10px;
}

.eventSearchFilter input {
  font-size: 14px;
  margin-left: 10px;
}

.confirmFilter button {
  margin-left: 50px;
  background-color: aliceblue;
  border-top:0;
  border-right:0;
  border-left:0;
  border-color: aliceblue;
}

</style>