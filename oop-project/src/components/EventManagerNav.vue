<template>
      <nav class="nav">
          <a ref="manage" class="nav-item" :class="{ 'is-active': activeButton === 'manage' }" active-color="orange" @click="handleNavigation('manage')">Manage Events</a>
          <a ref="create" class="nav-item" :class="{ 'is-active': activeButton === 'create' }" @click="handleNavigation('create')" active-color="orange">Create Event</a>
          <div class = "nav-item-with-dropdown">
            <a ref="ticketOfficer" class="nav-item" :class="{ 'is-active': activeButton === 'newTicketOfficer' }" active-color="orange">Ticketing Officers</a>
            <div class="dropdown-content">
              <a ref="viewTicketOfficer" class = "drop-item" @click="handleNavigation('viewTicketOfficer')">View All</a>
              <a ref="newTicketOfficer" class="drop-item" @click="handleNavigation('newTicketOfficer')">Add Ticketing Officer</a>
            </div>
          </div>
          <a ref="stats" class="nav-item" :class="{ 'is-active': activeButton === 'stats' }" @click="handleNavigation('stats')" active-color="orange">Statistics</a>
          <router-link to="/profile" class="nav-item nav-item-right">Profile</router-link>
          <a ref="contact" class="nav-item" :class="{ 'is-active': activeButton === 'contact' }" @click="logout" active-color="navy">Logout</a>
          <span class="nav-indicator" ref="indicator"></span>
      </nav>
  </template>
  
  <script>
  // import {ref} from 'vue';

  export default {
    name: 'EventManagerNav',
    props: {
      activeButton: String, // Prop to indicate the active button
    },
    

    data() {
 
      return {
        items: [], // Array to store nav items

      };
    },
    methods: {
      logout() {
      sessionStorage.clear();
      this.$router.push({ name: 'LogoutView' });
      },
      handleNavigation(page) {
        this.$emit('navigate', page); // Emit a custom event with the page name
      },
      handleIndicator(el) {
        if (!el || !el.getAttribute) return; // Check if el is undefined or null, or if getAttribute method is not available
  
        const activeColor = el.getAttribute('active-color');
        this.items.forEach(item => {
          item.classList.remove('is-active');
        });
        if (this.$refs.indicator) { // Check if this.$refs.indicator is not null
          this.$refs.indicator.style.width = `${el.offsetWidth}px`;
          this.$refs.indicator.style.left = `${el.offsetLeft}px`;
          this.$refs.indicator.style.backgroundColor = activeColor;
        }
        el.classList.add('is-active');
        el.style.color = activeColor;
      },
      handleLogout() {
        // Add logout functionality here
        console.log('Logout clicked');
      },

    },
    mounted() {
      this.items = this.$el.querySelectorAll('.nav-item');
      const manageButton = this.$refs.manage; // Get the reference to the "Home" button
      this.handleIndicator(manageButton); // Automatically select the "Home" button
      this.items.forEach(item => {
        item.addEventListener('click', e => {
          this.handleIndicator(e.target);
        });
      });
    }
  
  };
  </script>
  
  <style>
  .dropdown-content {
  display: none;
  position: absolute;
  background-color: #f9f9f9;
  min-width: 200px;
  box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
  z-index: 1;
  margin-top:15px;
  }

  .nav-item-with-dropdown:hover .dropdown-content {
    display: block;
  }

  .dropdown-content a {
    color: black;
    padding: 12px 16px;
    text-decoration: none;
    display: block;
  }

  .dropdown-content a:hover {
    background-color: orange;
  }

  .nav-item-with-dropdown {
    position: relative;
    display: inline-block;
  }

  /* Add your styles here */
  </style>
  