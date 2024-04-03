<template>
    <div>
      <h2>Enter your payment details</h2>
      <form id="payment-form" @submit.prevent="handleSubmit">
        <label for="card-holder-name">Cardholder Name</label>
        <input type="text" id="card-holder-name" v-model="cardHolderName" required>
        <label for="card-element">Credit or debit card</label>
        <div id="card-element">
          <!-- A Stripe Element will be inserted here. -->
        </div>
        <!-- Used to display form errors. -->
        <div id="card-errors" role="alert">{{ errorMessage }}</div>
        <button type="submit">Submit Payment</button>
      </form>
    </div>
  </template>
  
  <script>
  import Stripe from 'stripe';

  export default {
    data() {
      return {
        cardHolderName: '',
        errorMessage: ''
      };
    },
    mounted() {
      this.setupStripeElements();
    },
    methods: {
      setupStripeElements() {
        // Set your publishable key
        const stripe = Stripe('YOUR_PUBLISHABLE_KEY');
        const elements = stripe.elements();
        const cardElement = elements.create('card');
  
        cardElement.mount('#card-element');
  
        // Handle real-time validation errors from the card Element.
        cardElement.on('change', (event) => {
          if (event.error) {
            this.errorMessage = event.error.message;
          } else {
            this.errorMessage = '';
          }
        });
  
        this.cardElement = cardElement;
      },
      handleSubmit() {
        this.errorMessage = ''; // Reset error message
        // Create a token when the form is submitted.
        stripe.createToken(this.cardElement).then((result) => {
          if (result.error) {
            this.errorMessage = result.error.message;
          } else {
            // Send the token to your server.
            this.stripeTokenHandler(result.token);
          }
        });
      },
      stripeTokenHandler(token) {
        // In a real-world application, you would send the token to your server.
        // For demonstration purposes, we'll just log it to the console.
        console.log(token);
        // Optionally, you can redirect the user or perform other actions here after successful payment.
      }
    }
  };
  </script>
  
  <style>
  /* Add your custom styles here */
  </style>
  