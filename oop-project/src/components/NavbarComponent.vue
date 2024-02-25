<template>
  <div>
    <nav class="nav">
      <a href="#" class="nav-item is-active" active-color="navy" @click="handleNavigation('home')">Home</a>
      <a href="#" class="nav-item" @click="handleNavigation('events')" active-color="navy">Events</a>
      <a href="#" class="nav-item" @click="handleNavigation('contact')" active-color="navy">Contact Us</a>
      <input type="text" placeholder="Search" class="nav-search-bar">
      <a href="#" class="nav-item nav-item-right" @click="handleNavigation('login')" active-color="navy">Login/Sign Up</a>
      <span class="nav-indicator" ref="indicator"></span>
    </nav>
  </div>
</template>

<script>
export default {
  name: 'NavbarComponent',
  methods: {
    handleNavigation(page) {
      this.$emit('navigate', page); // Emit a custom event with the page name
      if (page === 'events') {
        this.$emit('show-events'); // Emit a custom event to show events
      } else {
        this.$emit('hide-events'); // Emit a custom event to hide events
      }
    },
    handleIndicator(el) {
      const activeColor = el.getAttribute('active-color');
      this.items.forEach(item => {
        item.classList.remove('is-active');
      });
      this.$refs.indicator.style.width = `${el.offsetWidth}px`;
      this.$refs.indicator.style.left = `${el.offsetLeft}px`;
      this.$refs.indicator.style.backgroundColor = activeColor;
      el.classList.add('is-active');
      el.style.color = activeColor;
    }
  },
  mounted() {
    this.items = this.$el.querySelectorAll('.nav-item');
    this.items.forEach(item => {
      item.addEventListener('click', e => {
        this.handleIndicator(e.target);
      });
    });
  }
};
</script>
