<template>
    <div class="manageEvent">
        <h1>Manage Event</h1>
        <h3>{{ managedEvent.name }}</h3>
        <p>{{ managedEvent.date }}</p>
        <!-- <button @click="buyTicket">Buy Ticket</button> -->
    </div>
</template>

<script>
export default {
  props: ['managedEvent'],
  name: 'ManagedEvent',
}
</script>




<style scoped>

</style>
