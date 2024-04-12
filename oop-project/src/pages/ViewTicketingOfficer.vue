<template>
    <div>
        <div class="eventManagerNav">
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
            <EventManagerNav @navigate="navigate"/> <!-- Include the AfterLoginNav.vue component here -->
        </div>
    
        <div class="content">
            <h1>View All</h1>
            <table class="table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Full Name</th>
                        <th>Email Address</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="TO in allTO" :key="TO.id">
                        <td>{{ TO.id }}</td>
                        <td>{{ TO.fullName }}</td>
                        <td>{{ TO.email }}</td>
                        <td>
                            <button class="delete" type="button" @click="deleteTO(TO)">Delete</button>
                        </td>
                    </tr>
                </tbody>
            </table>
            
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
        const allTO = ref({});
        const deletingTO = ref(null);
        onMounted (async() => {
            try{
                const response = await fetch("http://localhost:8080/api/ticketing_officers/all",{
                    method: 'GET',
                    headers:{
                        'Authorization': `Bearer ${sessionStorage.getItem('token')}`,
                    }
                });
                const data = await response.json();
                console.log('data',data);
                allTO.value = data;
                
                console.log('allTO',allTO.value);
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
        
        const deleteTO = (TO) =>{
            deletingTO.value = TO;
            console.log(deletingTO.value);
            fetch(`http://localhost:8080/api/ticketing_officers/delete_ticketing_officer?email=${deletingTO.value.email}`,{
                method: "DELETE",
                headers: {
                Authorization: "Bearer " + sessionStorage.getItem("token")
                },
            })
            .then(async(response)=>{
                try{
                if (response.ok){
                    window.confirm("Ticketing Officer Successfully Deleted!");
                    const responseData = await response.json();
                    console.log(responseData);
                    allTO.value = responseData;
                    window.location.reload();
                
                }else{
                    console.error("Error");
                }
            }catch(error){
                console.log(error);
                }
            })
        }
        

        return{
            allTO,
            navigate,
            deleteTO,
        }

    }

}







</script>




<style>
.content{
    margin-top:70px;
    margin-left:45px;
}
.table button{
  padding: 5px 10px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
  margin-bottom: 10px;
  background-color: red;
  margin-right: 10px;
  color: white;
}

.table button:hover{
    background-color:darkred;
}



</style>