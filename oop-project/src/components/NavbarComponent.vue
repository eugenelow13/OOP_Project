<template>
  <div>
    <nav class="nav">
      <a href="#" class="nav-item is-active" active-color="navy">Home</a>
      <a href="#" class="nav-item" active-color="navy">Events</a>
      <a href="#" class="nav-item" active-color="navy">Contact Us</a>
      <input type="text" placeholder="Search" class="nav-search-bar">
      <a href="#" class="nav-item nav-item-right" active-color="navy">Login/Sign Up</a>
      <span class="nav-indicator"></span>
    </nav>
  </div>
</template>


<script>
export default {
  name: 'NavbarComponent',
  mounted() {
    const indicator = document.querySelector('.nav-indicator');
    const items = document.querySelectorAll('.nav-item');

    function handleIndicator(el) {
      const activeColor = el.getAttribute('active-color');

      items.forEach(item => {
        item.classList.remove('is-active');
        item.removeAttribute('style');
      });

      indicator.style.width = `${el.offsetWidth}px`;
      indicator.style.left = `${el.offsetLeft}px`;
      indicator.style.backgroundColor = activeColor;

      el.classList.add('is-active');
      el.style.color = activeColor;
    }

    items.forEach(item => {
      item.addEventListener('click', e => {
        handleIndicator(e.target);
      });
      if (item.classList.contains('is-active')) {
        handleIndicator(item);
      }
    });
  }
};
</script>

