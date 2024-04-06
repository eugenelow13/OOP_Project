<template>
    <div class="addTicketingOfficerPage">
        <div class="eventManagerNav">
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
            <EventManagerNav @navigate="navigate"/> <!-- Include the AfterLoginNav.vue component here -->
        </div>
        <div class="addTO">
            <form @submit.prevent="searchForm">
            <div class="form-group">
                <label for="emailAddress">Enter New Ticketing Officer's Email Address:</label>
                <input type="text" v-model="emailAddress" @input="resetSearch">
                <button class="search" :class="{'searching':isSearching}" type="submit">{{ isSearching ? 'Searching...':'Search' }}</button>
            </div>
            </form>
            <div v-if="fullName!=null" class="name-group">
                <p>{{ fullName }}</p>
                <button class="add" :class="{ 'added': isAdded }" type="submit" @click="addTicketingOfficer">{{ isAdded ? 'Added &#10004;' : 'Add' }}</button>
            </div>

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
        const isAdded = ref(false);
        const isSearching = ref(false);
        const fullName = ref(null);

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
        const searchForm = () => {
            isSearching.value=true;
            // fullName.value = sessionStorage.getItem('fullName'); retrieve fullName according to email
            fullName.value = 'John Doe';
            console.log(fullName);
            isAdded.value=false;
        }
        const resetSearch = () => {
            isSearching.value=false;
        }
        const addTicketingOfficer = () => {
            isAdded.value=true;
        }

        return{
            navigate,
            searchForm,
            isAdded,
            emailAddress,
            resetSearch,
            isSearching,
            fullName,
            addTicketingOfficer,
        }
    }
}

</script>


<style>
.addTO{
    margin-top:90px;
}

.form-group button.searching {
    background-color: lightgrey;
}
.name-group{
    margin-left:10px;
    display:flex;
    flex-direction: row;
}
.name-group p{
    font-size: large;
    width:50vw;
}
.name-group button{
    margin-left:10px;
    padding: 10px 20px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    transition: background-color 0.3s;
    margin-bottom: 10px;
    background-color: orange;
}
.name-group button.added{
    background-color: lightgreen;
}


</style>