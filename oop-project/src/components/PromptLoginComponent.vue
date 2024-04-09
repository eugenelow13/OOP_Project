<template>
  <div>
    <div class="modal" v-if="show">
      <div class="modal-content">

        <!-- Modal content -->
        <strong>Please sign in to purchase tickets</strong>

        <!-- Example buttons for confirmation -->
        <div class="button-container">
            <button class="navigation-button" @click="goToLogin">Go to Login</button>
            <button class="navigation-button" @click="closeModal">Continue Browsing</button>
         
        </div>
       
      </div>
    </div>
  </div>
</template>

<script>
//import { useRouter } from 'vue-router';

export default {
  name: 'PromptLoginComponent', // Ensure the component name matches the filename
  props: {
    show: Boolean
  },
  methods: {
    goToLogin(){
        console.log("Redirecting to Login...");
        //const router = useRouter(); // Access the router
        this.$router.push({ name: 'LoginPageView' });
     
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
    width: 100%;
    height: 40px;
    border-radius: 40px;
    background: #3498db;
    border: none;
    outline: none;
    cursor: pointer;
    font-size: 1em;
    font-weight: 600;
    color: #fff; /* Changed color to white */
    
}

button:hover {
  background-color: #2980b9;
}

.button-container {
  display: flex;
  justify-content: space-between;
  margin-top: 2rem; /* Adjust margin as needed */
}



</style>
