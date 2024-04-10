<template>
  <div>
    <div class="modal" v-if="show">
      <div class="modal-content">

        <!-- Modal content -->
        <strong>Confirm Delete Booking?</strong>

        <!-- Example buttons for confirmation -->
        <div class="button-container">
          <button class="confirmation-button" @click="cancelBooking">Yes</button>
          <button class="confirmation-button" @click="closeModal">No</button>
        </div>
      </div>
    </div>
  </div>
   <RegisterErrorComponent :show="showError" :errorMessage="errorMessage" @close="showError = false" />
</template>

<script>
//import { useRouter } from 'vue-router';
import axios from 'axios';
import RegisterErrorComponent from '../components/RegisterErrorComponent.vue';

export default {
  name: 'CancelBookingComponent', // Ensure the component name matches the filename
  props: {
    show: Boolean,
    bookingId: Number 
  },
  components: {
    RegisterErrorComponent,
  },
  data() {
      return {
          showError: false,
          errorMessage: ''
      };
  },
  methods: {
    cancelBooking(){
        console.log("Booking cancelled (From Component):", this.bookingId);

        const cancelURL = 'http://localhost:8080/api/bookings/cancelBooking';

        const bookingId = this.bookingId;
        const email = sessionStorage.getItem('email');
        const token = sessionStorage.getItem('token');

        console.log(email)

        axios.delete(`${cancelURL}?bookingId=${bookingId}&email=${email}`, {
        headers: {
            Authorization: `Bearer ${token}`
        }
        })
        .then(response => {
        // Handle the response data
        console.log('Response:', response.data);
        window.location.reload();
        })
        .catch(error => {
        // Handle errors
        //alert(error.response.data.message);
        this.showError = true;
        this.errorMessage = error.response.data.message;
        console.error('Error:', error);
        });


        this.$emit('close');



    },
    closeModal() {
      // Emit an event to parent component to close the modal
      this.$emit('close');
    }
  }
};
</script>

<style scoped>
/* Modal styles */

.close-button {
  position: absolute;
  top: 0.5rem;
  right: 0.5rem;
  padding: 0.5rem;
  cursor: pointer;
}

.modal {
  display: block;
  position: fixed;
  z-index: 999;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0,0,0,0.5);
}

.modal-content {
  background-color: #fefefe;
  margin: 15% auto;
  padding: 20px;
  border: 1px solid #888;
  width: 80%;
  max-width: 600px;
  text-align: center;
}

button {
  margin-top: 20px;
  padding: 10px 20px;
  background-color: #3498db;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  background-color: #2980b9;
}

.button-container {
  display: flex;
  justify-content: space-between;
  margin-top: 1rem; /* Adjust margin as needed */
}

.confirmation-button {
  flex: 1;
  margin: 0 0.5rem; /* Adjust margin as needed */
  padding: 0.5rem 1rem;
  font-size: 0.8rem; /* Adjust font size as needed */
}
</style>
