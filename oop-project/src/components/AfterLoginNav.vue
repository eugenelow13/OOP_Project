<template>
  <div>
    <nav class="nav">
      <a ref="home" class="nav-item" :class="{ 'is-active': activeButton === 'home' }" active-color="navy" @click="handleNavigation('home')">Home</a>
      <a ref="events" class="nav-item" :class="{ 'is-active': activeButton === 'events' }" @click="handleNavigation('events')" active-color="navy">Events</a>
      <a ref="contact"  class="nav-item" :class="{ 'is-active': activeButton === 'contact' }" @click="handleNavigation('contact')" active-color="navy">Contact Us</a>
      <router-link to="/profile" class="nav-item nav-item-right">Profile</router-link> <!-- Update this line -->
      <span class="nav-indicator" ref="indicator"></span>
    </nav>
  </div>
</template>

<script>
export default {
  name: 'AfterLoginNav',
  props: {
    activeButton: String, // Prop to indicate the active button
  },
  data() {
    return {
      items: [], // Array to store nav items
    };
  },
  methods: {
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
    }
  },
  mounted() {
    this.items = this.$el.querySelectorAll('.nav-item');
    const homeButton = this.$refs.home; // Get the reference to the "Home" button
    this.handleIndicator(homeButton); // Automatically select the "Home" button
    this.items.forEach(item => {
      item.addEventListener('click', e => {
        this.handleIndicator(e.target);
      });
    });
  }

};
</script>

<style>
/* Add your styles here */
</style>
