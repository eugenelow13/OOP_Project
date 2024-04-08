<template>
    <div>
        <div class="eventManagerNav">
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
            <EventManagerNav @navigate="navigate"/> <!-- Include the AfterLoginNav.vue component here -->
        </div>
    
        <div class="content">
            <h1>View All</h1>
        </div>
    

    </div>


</template>





<script>
import EventManagerNav from '../components/EventManagerNav.vue';
import {onMounted,ref} from 'vue';
import router from '../router';

export default{
    name:'ViewTicketingOfficer',
    components:{
        EventManagerNav,
    },

    setup(){
        const allTO = ref(null);
        onMounted (async() => {
            try{
                const response = await fetch("http://localhost:8080/api/ticketing_officers/all",{
                    method: 'GET',
                });
                allTO.value = await response.json();
                console.log(allTO.value);
            }catch(error){
                console.log('Error',error)

            }
        });
        const navigate = (page) => {

            switch (page) {
            
            case 'profile':
                router.push({ name: 'ProfileView' }); // Navigate to ProfileView.vue
                break;
            case 'stats':
                router.push({name:'StatisticsView'});
                break;
            case 'create':
                router.push({ name: 'CreateEventView'}); //Navigate to CreateEvent.vue
                break;
            case 'manage':
                window.scrollTo({ top: 0, behavior: 'smooth' });
                break;
            case 'newTicketOfficer':
                router.push({name:'AddTicketingOfficer'});
                break;
            case 'viewTicketOfficer':
                router.push({name:'ViewTicketingOfficer'});
                break;
            default:
                break;
            }
        }
        

        return{
            allTO,
            navigate,
        }

    }

}







</script>




<style>
.content{
    margin-top:70px;
}



</style>