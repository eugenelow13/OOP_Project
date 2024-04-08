<template>
  <div>
    <div class="modal" v-if="show">
      <div class="modal-content">
        <img src="../assets/seating map.png" alt="Event Image">
        <button @click="closeModal">Back</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'MapComponent', // Ensure the component name matches the filename
  props: {
    show: Boolean,
  },
  methods: {
    closeModal() {
      // Emit an event to parent component to close the modal
      this.$emit('close');
    }
  }
};
</script>

<style scoped>
/* Modal styles */
.modal {
  display: block;
  position: fixed;
  z-index: 999;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0,0,0,0.5);
}

.modal-content {
  background-color: #fefefe;
  margin: 15% auto;
  padding: 20px;
  border: 1px solid #888;
  width: 80%;
  max-width: 600px;
  text-align: center;
}

button {
  margin-top: 20px;
  padding: 10px 20px;
  background-color: #3498db;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  background-color: #2980b9;
}
</style>
