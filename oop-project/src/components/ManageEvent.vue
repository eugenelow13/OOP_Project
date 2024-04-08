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
          <textarea id="eventDes" rows="4" :value="editedEvent.des" :style="{width:50+'vw'}" @input="editedEvent.des = $event.target.value"></textarea>
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
        <button type="button" @click="cancelEvent">Cancel Event</button>
      </form>
    </div>
</template>

<script>
import {ref} from 'vue';
export default {
  props: ['managedEvent'],
  name: 'ManagedEvent',

  setup(props){
    const editedEvent = ref({});
    const responseData = ref(null);
    const submitForm = async() => {
      
      editedEvent.value.date = new Date(editedEvent.value.date).toISOString();
      console.log(editedEvent.value.date);
      try {
        const response = await fetch(`http://localhost:8080/api/events/${editedEvent.value.id}`,{
          method: 'PUT',
          headers:{
            'Content-Type': 'application/json',

          },
          body: JSON.stringify(editedEvent.value),
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
    };
  
  const cancelEvent = async () => {
    try{
      const response = await fetch(`http://localhost:8080/api/events/${props.managedEvent.id}/cancel`,{
        method: 'PUT',

      });
      
      responseData.value=await response.json();
      if (response.ok){
          window.confirm(responseData.value.message);
          window.location.reload();
        }else{
          console.error("Response error");
        }
      } catch(error){
        console.error(error);
      }
    };
    
    return{
      editedEvent,
      submitForm,
      responseData,
      cancelEvent,
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
  
}
</script>




<style scoped>
.manage-event{
  margin-left:10px;
  background-color: #f9f9f9;
}
.manage-event h2{
  padding-top: 70px;
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

.form-group label {
  display: block;
  font-weight: bold;
  margin-bottom: 5px;
}
.manage-event button[type=submit]{
  background-color: orange;
  padding: 10px 20px;
  border:none;
  border-radius:5px;
  cursor:pointer;
  transition: background-color 0.3s;
  margin-bottom: 10px;
  margin-left:10px;
}
.manage-event button[type=submit]:hover{
  background-color: rgb(172, 113, 2);
}
.manage-event textarea{
  resize:vertical;
}
.manage-event button[type=button]{
  background-color: red;
  padding: 10px 20px;
  border:none;
  border-radius:5px;
  cursor:pointer;
  transition: background-color 0.3s;
  margin-bottom: 10px;
  margin-left:10px;
}
.manage-event button[type=button]:hover{
  background-color: rgb(143, 0, 0);
}




</style>
