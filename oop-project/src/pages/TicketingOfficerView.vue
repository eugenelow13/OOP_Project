<template>
  <div class="p-sm-5 p-3">
    <div class="logout-container">
      <button class="logout-button" @click="logout">Logout</button>
    </div>

    <h1>Ticket Admission</h1>
    <form>
      <div class="form-group p-0">
        <label for="ticketID">Enter Ticket ID:</label>
        <input type="text" v-model="ticketID" @input="resetValid">

        <button @click.prevent="verify" class="verify" :class="{ 'verified': isValid }"
          type="submit">{{ isValid ? 'Verified &#10004;' : 'Verify' }}</button>

        <button :disabled="!canAdmit" class="verify" @click.prevent="admit"
          type="submit">Admit</button>
      </div>
    </form>

    <div v-if="ticketInfo" class="card mb-3">
      <div class="card-body">
        Ticket: {{ ticketInfo.id }} ({{ ticketInfo.isAdmitted ? "Admitted": "Not Admitted" }})<br>
        Booker: {{ ticketInfo.bookingCustomerName }} ({{ ticketInfo.bookingCustomerEmail }})
      </div>
    </div>

    <div id="reader">
    </div>
  </div>


  <div class="booking">
    <h1>On-site Booking</h1>
    <div class="events">
      <FilterEvent v-if="EventsList.length" :events = "EventsList" @confirmFilter="handleFilter"/>
      <div class="event-grid">   
        <EventTile v-for="event in filteredEvents" :key="event.name" :event="event" @buyTicket="showBookHandler" />
      </div>  
      <div class="event-grid" v-if="!filteredEvents">
        <EventTile v-for="event in EventsList" :key="event.name" :event="event" @buyTicket="showBookHandler" />
      </div>
    </div>

    <div v-if="showBook" class="row" ref="bookingSection">
      <!-- Event Details -->
      <div class="col-md-12">
        <div class="container-xl px-4 mt-4">
          <div class="card mb-4 mb-xl-0" style="text-align:center"> 
            <div class="card-header">
              <h2>Event Details</h2>
            </div>

            <!-- Card Body -->
            <div class="card-body row">
              <!-- Left Side -->
              <div class="col-md-6" style="text-align:left;">
                <p><strong>Event ID:</strong> {{ eventToBook.id }}</p> 
                <p><strong>Event Title:</strong> {{ eventToBook.name }}</p>
                <p><strong>Event Description:</strong> {{ eventToBook.description }}</p>
                <p><strong>Event Date:</strong> {{ eventToBook.date }}</p>
              </div>

              <div class="col-md-6" style="text-align:right;">
                <button @click=seatingplan() class="btn btn-secondary mb-3" >View Seating Plan</button>
              </div>

              <!-- Map Modal Component -->
              <MapComponent :show="showModal" @close="showModal = false"/>
              <!-- Buy Modal Component -->
              <BuyComponent :show="showModalbuy" @close="showModalbuy = false" @confirm="book" v-model:currentPassword="currentPassword"/>
              <!-- Purchase Status Modal Component -->
              <PurchaseComponent :show="showModalpurchase" @close="showModalpurchase = false" :purchaseMessage="purchaseMessage" />


              <div class="card-header">
                <h2>Customer Details</h2>
              </div>

              <div class="col-md-6" style="text-align:left;">
                <form @submit.prevent="submitForm">
                  <div class="form-group">
                    <label for="selectedNumber">Number of tickets (maximum 5):</label>
                    <select v-model="selectedNumber">
                      <option v-for="number in numbers" :key="number" :value="number">{{ number }}</option>
                    </select>
                  </div>
                  <div class="form-group">
                    <label for="emailAddress">Customer's Email Address:</label>
                    <input type="text" id="emailAddress" v-model="emailAddress">
                  </div>
                  <button  class="btn btn-primary" type="submit">Buy Ticket</button>
                </form>    
                
              </div>

            </div>
          </div>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
import { computed, onMounted, ref } from 'vue';
import { Html5QrcodeScanner } from "html5-qrcode";
import FilterEvent from '@/components/FilterEvent.vue';
import EventTile from '@/components/EventTile.vue';
import BuyComponent from '@/components/BuyComponent.vue';
import MapComponent from '@/components/MapComponent.vue';
import PurchaseComponent from '@/components/PurchaseComponent.vue';

export default {
  name: 'TicketingOfficerView',
  components:{
    FilterEvent,
    EventTile,
    BuyComponent,
    MapComponent,
    PurchaseComponent,
  },

  setup() {
    const ticketInfo = ref(null);
    const ticketID = ref(null);
    const isValid = ref(false);
    const filteredEvents = ref(null);
    const EventsList = ref([]);

    const handleFilter = (events) => {
      filteredEvents.value=events;
    }
    onMounted(() => {
    
    const token = sessionStorage.getItem('token');

    const options = {
      method: 'GET',
      headers: {
        'Authorization': `Bearer ${token}`
      }
    };

    fetch("http://localhost:8080/api/events", options)
      .then(response => {
        if (!response.ok) {
          alert('Error fetching events');
          throw new Error('Network response was not ok');
        }
        return response.json();
      })
      .then(data => {
        console.log('Events:', data);
        data.data.forEach(event => {
          const originalDate = new Date(event.date);
          const formattedDate = `${(originalDate.getMonth() + 1).toString().padStart(2, '0')}/${originalDate.getDate().toString().padStart(2, '0')}/${originalDate.getFullYear()}, ${originalDate.toLocaleString('en-US', { hour: 'numeric', minute: '2-digit', hour12: true })}`;
          event.date = formattedDate;
        });
        EventsList.value = data.data;
        console.log("integration",EventsList)
      })
      .catch(error => {
        console.error('There was a problem with the fetch operation:', error);
      });

    });

    const canAdmit = computed(() => {
      return isValid.value && ticketInfo.value && !ticketInfo.value.isAdmitted;
    })

    const verify = () => {
      console.log(ticketID.value);
      // isValid.value = true;

      fetch("http://localhost:8080/api/tickets/" + ticketID.value, {
          headers: {
             Authorization: "Bearer " + sessionStorage.getItem("token")
          }
      })
      .then(async (response) => {
        if (response.ok) {
          console.log()
          isValid.value = true;
          const data = await response.json();
          ticketInfo.value = data.data;
        } else {
          isValid.value = false;
        }
      })
    }

    function admit() {
      fetch("http://localhost:8080/api/tickets/" + ticketID.value + "/admit", {
        method: "POST",
        headers: {
          Authorization: "Bearer " + sessionStorage.getItem("token")
        }
      })
      .then(async (response) => {
        if (response.ok) {
          const data = await response.json();
          ticketInfo.value = data.data;
        } else {
          console.log("Failed to admit");
        }
      })
    }

    const resetValid = () => {
      isValid.value = false;
    }

    onMounted(() => {
      function onScanSuccess(decodedText, decodedResult) {
        // handle the scanned code as you like, for example:
        ticketID.value = decodedText;
        console.log(decodedResult);
      }

      function onScanFailure(error) {
        // handle scan failure, usually better to ignore and keep scanning.
        // for example:
        console.warn(`Code scan error = ${error}`);
      }

      let html5QrcodeScanner = new Html5QrcodeScanner(
        "reader",
        { fps: 10, qrbox: { width: 250, height: 250 } },
        /* verbose= */ false);
      html5QrcodeScanner.render(onScanSuccess, onScanFailure);
    })




    // ON-SITE BOOKING
    const eventToBook = ref(null);
    const showModal = ref(false);
    const showModalbuy = ref(false);
    const purchaseMessage = ref('');
    const showModalpurchase = ref(false);
    const showBook = ref(false);
    const emailAddress = ref(null);
    const customer = ref(null);
    const customerId = ref(null);
    const currentPassword = ref(null);
    const selectedNumber = ref(1);
    const bookingSection = ref(null);
    

    const book = () => {
      console.log('booking')
      const requestData ={
        eventId:eventToBook.value.id,
        customerId:customerId.value,
        tickets: [{"noOfGuests":selectedNumber.value,"isAdmitted":false}],
        password: currentPassword.value,
      }
      const token = sessionStorage.getItem('token');
      const options={
        method:'POST',
        headers:{
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`,
        },
        body: JSON.stringify(requestData)
      };
      fetch("http://localhost:8080/api/bookings/placeBooking", options)
        .then(response => {
          if (!response.ok) {
            purchaseMessage.value = 'Something went wrong with the booking';
            showModalpurchase.value = true;
            throw new Error('Network response was not ok');
          }
          return response.json();
        })
        .then(data => {
          purchaseMessage.value = 'Email sent successfully';
          showModalpurchase.value = true;
          console.log(data);
        })
        .catch(error => {
          console.error('There was a problem with the fetch operation:', error);
        });
    }
    const showBookHandler = (event) =>{
      console.log('showBookhandler');
      showBook.value=true;
      eventToBook.value=event;
      if (bookingSection.value){
        bookingSection.value.scrollIntoView({behavior:'smooth',block:'start'});
      }
    }

    const numbers = Array.from({ length: 5 }, (_, i) => i + 1); // Array containing numbers from 1 to 5
    
    const submitForm = () =>{
      // const url = new URL("http://localhost:8080/api/customers/");
      // url.searchParams.append('email', emailAddress.value.toString());

      
      fetch(`http://localhost:8080/api/customers?email=${emailAddress.value}`,{
        method: "GET",
        headers: {
          Authorization: "Bearer " + sessionStorage.getItem("token")
        },
      })
      .then(async(response)=>{
        try{
          if (response.ok){
            const data = await response.json();
            customer.value = data;
            console.log(customer.value);
            customerId.value = customer.value.id;
            console.log(customerId.value);
            showModalbuy.value=true;
          
          }else{
            alert("User not found")
          }
      }catch(error){
          console.log(error);
        }
      })
    }

    return {
      ticketID,
      ticketInfo,
      canAdmit,
      verify,
      admit,
      isValid,
      resetValid,
      handleFilter,
      EventsList,
      filteredEvents,
      showBook,
      eventToBook,
      showModal,
      showModalbuy,
      showModalpurchase,
      purchaseMessage,
      book,
      showBookHandler,
      numbers,
      emailAddress,
      submitForm,
      currentPassword,
      bookingSection,
      customer,
      
    }
  
  },

  methods:{
    logout() {
      sessionStorage.clear();
      this.$router.push({ name: 'LogoutView' });
    },
    seatingplan() {
      // const imgSrc = this.eventImg;
      // console.log(imgSrc);
      // console.log(this.showModalpurchase);
      this.showModal = true;
    },
    
  }
}

</script>

<style>
.p-sm-5{
  background-color:#f9f9f9;
  text-align: center;
}
.form-group {
  margin-bottom: 20px;
  text-align: left;

}

.form-group input {
  padding: 10px;
  border: 1px solid;
  border-radius: 5px;
  margin-right: 10px;
  width: 50vw;

}

.form-group label {
  display: block;
  font-weight: bold;
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
  margin-right: 10px;
}

.form-group button:hover {
  background-color: rgb(172, 113, 2);
}

.form-group button.verified {
  background-color: rgb(1, 177, 1);
}
.booking{
  margin-top:20px;
  background-color:#f9f9f9;
  text-align: center;
  
}


.event-grid {
  display: flex;
  align-items: center; 
}

.logout-container {
  width: 100%;
  display: flex;
  justify-content: flex-end;
  padding: 10px;
  box-sizing: border-box;
}

.logout-button {
  margin-right: 10px;
  background-color: #ff9800; /* Warning color */
  border: none;
  padding: 8px 16px;
  border-radius: 4px;
  color: black;
  cursor: pointer;
  transition: background-color 0.3s;
}

.logout-button:hover {
  background-color: #f57c00; /* Darker shade of warning color on hover */
}
</style>