<template>
  <div class="loading-page">
    <!-- Your loading animation or message goes here -->
    <img src="../assets/tick.gif" alt="Loading GIF">
  </div>
</template>

<script>
export default {
  name: 'LoadingView',
  mounted() {
    setTimeout(() => {
      this.$router.push({ name: 'AfterLoginView' }); // Navigate to the home page
    }, 2500); // 3000 milliseconds = 3 seconds
  }
};
</script>   

<style scoped>
.loading-page {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(255, 255, 255, 0.8);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 9999;
}

.loading-page img {
  width: 100vh;
  height: 100vh;
}
</style>
