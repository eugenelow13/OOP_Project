<template>
    <h1>Hello Ticketing Officer</h1>
    <form @submit.prevent="submitForm">
        <div class="form-group">
            <label for="bookingID">Enter Booking ID:</label>
            <input type="text" v-model="bookingID" @input="resetValid">
            <button class="verify" :class="{ 'verified': isValid }" type="submit">{{ isValid ? 'Verified &#10004;' : 'Verify' }}</button>
        </div>
    </form>
</template>

<script>
import {ref} from 'vue';

export default{
    name: 'TicketingOfficerView',

    setup(){
        const bookingID = ref(null);
        const isValid = ref(false);
        
        const submitForm = () => {
            console.log(bookingID.value);
            isValid.value = true;
        }

        const resetValid = () => {
            isValid.value=false;
        }
        return{
            bookingID,
            submitForm,
            isValid,
            resetValid,
        }
    }
}

</script>

<style>
.form-group {
  margin-bottom: 20px;
  text-align: left;
  margin-left:10px;
  
}
.form-group input{
  padding:10px;
  border: 1px solid;
  border-radius: 5px;
  margin-right:10px;
  width:50vw;
  
}
.form-group label {
  display: block;
  font-weight:bold;
  margin-bottom: 5px;
}
.form-group button {
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
  margin-bottom: 10px;
  background-color: orange;
  margin-right:10px;
}
.form-group button:hover{
  background-color: rgb(172, 113, 2);
}

.form-group button.verified {
    background-color: rgb(1, 177, 1);
}
</style>