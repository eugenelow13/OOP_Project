<template>
    <div class="addTicketingOfficerPage">
        <div class="eventManagerNav">
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
            <EventManagerNav @navigate="navigate"/> <!-- Include the AfterLoginNav.vue component here -->
        </div>
        <div class="addTO">
            <h2>Fill in your new Ticketing Officer's information below:</h2>
            <form @submit.prevent="submitForm">
            <div class="form-group">
                <label for="fullName" >Full Name: </label>
                <input type="text" v-model="fullName" @input="resetAdded">
                <label for="emailAddress">Email Address:</label>
                <input type="text" v-model="emailAddress" @input="resetAdded">
                <div class="pw-group">
                    <label for="password">Default Password:</label>
                    <input type="text" v-model="password" @input="resetAdded">
                    <button class="generate" type="button" @click="generatePw">Generate</button>
                </div>
                <button class="submit" :class="{'submitted':isAdded}" type="submit">{{ isAdded ? 'Added Sucessfully':'Add Ticketing Officer' }}</button>
                <!-- <button class="search" :class="{'searching':isSearching}" type="submit">{{ isSearching ? 'Searching...':'Search' }}</button> -->
            </div>
            </form>
            

        </div>
    </div>


</template>

<script>
import EventManagerNav from '@/components/EventManagerNav.vue';
import router from '@/router';
import {ref} from 'vue';

export default{
    name: 'AddTicketingOfficer',
    components:{
        EventManagerNav
    },

    setup(){
        const emailAddress = ref(null);
        const password = ref(null);
        const fullName = ref(null);
        const isAdded = ref(false);
  

        const navigate = (page) => {

            switch (page) {
            
            case 'profile':
                router.push({ name: 'ProfileView' }); // Navigate to ProfileView.vue
                break;
            case 'stats':
                break;
            case 'create':
                window.scrollTo({ top: 0, behavior: 'smooth' });
                break;
            case 'manage':
                router.push({name: 'EventManagerView'})
                break;
            case 'addTicketOfficer':
                router.push({name:'AddTicketingOfficer'})
                break;
            default:
                break;
            }
        }
    

        const submitForm = async () => {
                const registerUserDto = {
                    fullName: fullName.value,
                    emailAddress: emailAddress.value,
                    password: password.value
                };

                try {
                    const response = await fetch("http://localhost:8080/api/auth/create_ticketing_officers", {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json',
                            // Add any necessary authorization headers here
                        },
                        body: JSON.stringify(registerUserDto)
                    });
                    
                    if (response.ok) {
                        isAdded.value = true;
                    } else {
                        // Handle error response
                    }
                } catch (error) {
                    console.error('Error:', error);
                    // Handle network error
                }
        };
        const resetAdded = () => {
            isAdded.value=false;
        }
        const addTicketingOfficer = () => {
            isAdded.value=true;
        }
        const generatePw = () => {
            const chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
            let newPassword = '';
            for (let i = 0; i < 12; i++) {
                newPassword += chars.charAt(Math.floor(Math.random() * chars.length));
            }
            password.value = newPassword;
        };

        return{
            navigate,
            submitForm,
            isAdded,
            emailAddress,
            resetAdded,
            fullName,
            addTicketingOfficer,
            password,
            generatePw,

        }
    }
}

</script>


<style>
.addTO{
    margin-top:90px;
}
.addTO h2{
    margin-left:10px;
    margin-bottom:20px;
}
.addTO button.submitted{
    background-color: lightgreen;
}
.addTO button.generate {
    border: 3px solid black; /* Add border */
    background-color:white;
    border-color: orange;
}
.addTO button.generate:hover {
    background-color: darkorange;
}




</style>