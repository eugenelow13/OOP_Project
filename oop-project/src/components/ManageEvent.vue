<template>
    <div class="manage-event">
      <h2>Manage Event</h2>
      <form @submit.prevent="submitForm">
        <div class="form-group">
          <label for="eventName">Name:</label>
          <input type="text" id="eventName" :value="editedEvent.name" @input="editedEvent.name = $event.target.value">
        </div>
        <div class="form-group">
          <label for="eventType">Type:</label>
          <input type="text" id="eventType" :value="editedEvent.type" @input="editedEvent.type = $event.target.value">
        </div>
        <div class="form-group">
          <label for="eventImg">Image URL:</label>
          <input type="url" id="eventImg" :value="editedEvent.img" :style="{width:50+'vw'}"  @input="editedEvent.img = $event.target.value">
        </div>
        <div class="form-group">
          <label for="eventDes">Description:</label>
          <input type="text" id="eventDes" :value="editedEvent.des" :style="{width:50+'vw'}" @input="editedEvent.des = $event.target.value">
        </div>
        <div class="form-group">
          <label for="eventVenue">Venue:</label>
          <input type="text" id="eventVenue" :value="editedEvent.venue" @input="editedEvent.venue = $event.target.value">
        </div>
        <div class="form-group">
          <label for="eventDate">Date:</label>
          <input type="date" id="eventDate" :value="editedEvent.date" @input="editedEvent.date = $event.target.value">
        </div>
        <div class="form-group">
          <label for="eventTicketPrice">Ticket Price:</label>
          <input type="number" id="eventTicketPrice" :value="editedEvent.ticketPrice" @input="editedEvent.ticketPrice = $event.target.value">
        </div>
        <div class="form-group">
          <label for="eventCancellationFee">Cancellation Fee:</label>
          <input type="number" id="eventCancellationFee" :value="editedEvent.cancellationFee" @input="editedEvent.cancellationFee = $event.target.value">
        </div>
        <!-- Add similar text boxes for other event parameters -->
        <button type="submit">Save Changes</button>
      </form>
    </div>
</template>

<script>
export default {
  props: ['managedEvent'],
  name: 'ManagedEvent',

  setup(){
    return{
      editedEvent:{}
    };
  },

  watch: {
    managedEvent: {
      handler(newVal){
        this.editedEvent = {...newVal};
      },
      immediate:true
    }
  },
  methods: {
    submitForm(){
      this.$emit('update-event',this.editedEvent);

    }
  }
}
</script>




<style scoped>
.manage-event{
  margin-left:10px;
}
.manage-event h2{
  padding-top: 70px;
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
.manage-event button{
  background-color: orange;
}




</style>
