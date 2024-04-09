<template>
  <div>
    <div class="container-xl px-4 mt-4">
      <hr class="mt-0 mb-4">
      <!-- Back button -->
      <router-link to="/afterlogin" class="btn btn-secondary mb-3">Back</router-link>
      <!-- Profile cards -->
      <div class="row">
          <div class="col-xl-4">
              <!-- Profile picture card-->
              <div class="card mb-4 mb-xl-0">
                  <div class="card-header">Profile Picture</div>
                  <div class="card-body text-center">
                      <!-- Profile picture image-->
                      <img class="img-account-profile rounded-circle mb-2" src="http://bootdey.com/img/Content/avatar/avatar1.png" alt="">
                  </div>
              </div>
          </div>
          <div class="col-xl-8">
              <!-- Account details card-->
              <div class="card mb-4">
                  <div class="card-header">Account Details</div>
                  <div class="card-body">
                      <!-- Display profile information -->
                      <div class="row gx-3 mb-3">
                          <div class="col-md-6">
                              <p class="small mb-1"><strong>Full name:</strong> {{ profile.fullName }}</p>
                          </div>
                          <!--
                          <div class="col-md-6">
                              <p class="small mb-1"><strong>Last name:</strong> {{ profile.lastName }}</p>
                          </div>
                          -->
                      </div>
                      <div class="mb-3">
                          <p class="small mb-1"><strong>Email address:</strong> {{ profile.email }}</p>
                      </div>
                      <div class="row gx-3 mb-3">
                          <div class="col-md-6">
                              <p class="small mb-1"><strong>Credit Balance: $</strong>{{ profile.creditBalance }}</p>
                          </div>
                      </div>
                      <!--
                      <div class="row gx-3 mb-3">
                          <div class="col-md-6">
                              <p class="small mb-1"><strong>Phone number:</strong> {{ profile.phoneNumber }}</p>
                          </div>
                          <div class="col-md-6">
                              <p class="small mb-1"><strong>Birthday:</strong> {{ profile.birthday }}</p>
                          </div>
                      </div>
                      -->
                      <!-- Save changes button-->
                      <button @click="logout" class="btn btn-primary" type="button">Log Out</button>
                      
                  </div>
              </div>
          </div>
      </div>
    </div>
    <div class="container-xl px-4 mt-4">
        <div class="card mb-4 mb-xl-0">
            <div class="card-header">Your Ticket Orders</div>
            <div class="card-body">
              <template v-if="bookings.length === 0">
               <p class="text-center " style="font-weight: bold;">You have no bookings at the moment</p>
              </template>
            
              <template v-else>
               <table class="table">
                <thead>
                     <tr>
                        <th scope="col">Booking ID</th>
                        <th scope="col">Event Name</th>
                        <th scope="col">Venue</th>
                        <th scope="col">Date</th>
                        <th scope="col">Booking Price</th>
                        <th scope="col">Tickets</th>
                        <th scope="col">Total Guests</th>
                        <th scope="col"></th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="booking in bookings" :key="booking.id">
                        <td>{{ booking.id }}</td>
                        <td>{{ booking.event.name}}</td>
                        <td>{{ booking.event.venue }}</td>
                        <td>{{ formatDate(booking.event.date) }}</td>
                        <td>${{ booking.bookingPrice }}</td>
                        <td>
                            <ul style="list-style-type: none; padding: 0; margin: 0;">
                                <li v-for="ticket in booking.tickets" :key="ticket.id">
                                    {{ ticket.id }}
                                </li>
                            </ul>
                        </td>
                        <td>
                            <ul style="list-style-type: none; padding: 0; margin: 0;">
                                <li v-for="ticket in booking.tickets" :key="ticket.id">
                                    {{ ticket.noOfGuests }}
                                </li>
                            </ul>
                        </td>
                        <td>
                            <button class="btn btn-primary" v-if="!booking.cancelled" @click="cancelBooking(booking.id)">Cancel</button>
                            <button class="btn btn-danger" disabled v-else>Cancelled</button> <!-- Button for actions -->
                        </td>

                    </tr>
                </tbody>
            </table>
              <!-- Cancel Booking Component -->
              <CancelBookingComponent :show="showModal" :bookingId="selectedBookingId" @close="showModal = false"/>
            </template>
            </div>
        </div>
    </div>
  </div>
</template>


<script>
import axios from 'axios';
import CancelBookingComponent from '../components/ConfirmCancelBooking.vue'; 

export default {
  components: {
    CancelBookingComponent
  },
  data() {
    return {
      profile: {},
      bookings: {},
      totalGuests: 0,
      showModal: false,
      selectedBookingId: null 
    };
  },
  mounted() {

    const email = sessionStorage.getItem('email');
    const token = sessionStorage.getItem('token');
    console.log(token);
    const profileURL = `http://localhost:8080/api/customers/${email}?email=${email}`;
    const cusBookingURL = `http://localhost:8080/api/customers/bookings?email=${email}`;

    axios.get(profileURL, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    })
    .then(response => {
        const data = response.data;
        console.log('Data:', data);
        this.profile = data;
    })
    .catch(error => {
        console.error('Error fetching user data:', error);
    });

    axios.get(cusBookingURL, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    })
    .then(response => {
        const data = response.data;
        console.log('Data:', data);
        this.bookings = data;
    })
    .catch(error => {
        console.error('Error fetching user data:', error);
    });
  },
  methods: {
    logout() {
      sessionStorage.clear();
      this.$router.push({ name: 'LogoutView' });
    },
    formatDate(dateString) {
      const date = new Date(dateString);
      const options = {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
      };
      return date.toLocaleDateString('en-US', options);
    },
    cancelBooking(bookingId) {
      // Perform the cancellation logic here
      console.log("Booking cancelled:", bookingId);
      this.showModal = true;
      this.selectedBookingId = bookingId; 
    }
  }
};
</script>


<style>
body {
  margin-top: 20px;
  background-color: #f2f6fc;
  color: #69707a;
}
.img-account-profile {
  height: 10rem;
}
.rounded-circle {
  border-radius: 50% !important;
}
.card {
  box-shadow: 0 0.15rem 1.75rem 0 rgb(33 40 50 / 15%);
}
.card .card-header {
  font-weight: 500;
}
.card-header:first-child {
  border-radius: 0.35rem 0.35rem 0 0;
}
.card-header {
  padding: 1rem 1.35rem;
  margin-bottom: 0;
  background-color: rgba(33, 40, 50, 0.03);
  border-bottom: 1px solid rgba(33, 40, 50, 0.125);
}
.form-control, .dataTable-input {
  display: block;
  width: 100%;
  padding: 0.875rem 1.125rem;
  font-size: 0.875rem;
  font-weight: 400;
  line-height: 1;
  color: #69707a;
  background-color: #fff;
  background-clip: padding-box;
  border: 1px solid #c5ccd6;
  -webkit-appearance: none;
  -moz-appearance: none;
  appearance: none;
  border-radius: 0.35rem;
  transition: border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
}

.nav-borders .nav-link.active {
  color: #0061f2;
  border-bottom-color: #0061f2;
}
.nav-borders .nav-link {
  color: #69707a;
  border-bottom-width: 0.125rem;
  border-bottom-style: solid;
  border-bottom-color: transparent;
  padding-top: 0.5rem;
  padding-bottom: 0.5rem;
  padding-left: 0;
  padding-right: 0;
  margin-left: 1rem;
  margin-right: 1rem;
}
</style>
