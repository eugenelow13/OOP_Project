<template>
  <div>
    <div class="modal" v-if="show">
      <div v-if="!purchaseSuccessful" class="modal-content">
        <h2>Enter Password to Proceed</h2>
        <input type="password" :value="currentPassword" @input="updatePassword($event.target.value)" placeholder="Enter password" required>
        <button @click="confirmPassword">Confirm</button>
        <button @click="closeModal">Back</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'BuyComponent',
  props: {
    show: Boolean,
    currentPassword: String,
  },
  methods: {
    closeModal() {
      this.$emit('close');
    },
    confirmPassword() {
      console.log("Emitted password:", this.currentPassword);
      this.$emit('confirm');
      this.$emit('close');
    },
    updatePassword(password) {
      this.$emit('update:currentPassword', password);
    }
  }
}
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

input {
  margin-bottom: 10px;
}

button {
  margin-top: 10px;
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
