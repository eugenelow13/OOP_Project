<template>
  <div>
    <section>
      <div class="form-box">
        <div class="form-value">
          <form action="">
            <h2>Register</h2>
            <div class="inputbox">
              <input v-model="fullName" required>
              <label for="">Enter Your Fullname</label>
            </div>
            <div class="inputbox">
              <ion-icon name="mail-outline"></ion-icon>
              <input v-model="email" required>
              <label for="">Enter Your Email</label>
            </div>
            <div class="inputbox">
              <ion-icon name="mail-outline"></ion-icon>
              <input v-model="cfmEmail" required>
              <label for="">Re-Enter Your Email</label>
            </div>
            <div class="inputbox">
              <ion-icon name="lock-closed-outline"></ion-icon>
              <input v-model="password" type="password" required>
              <label for="">Enter Your Password</label>
            </div>
            <div class="inputbox">
              <ion-icon name="lock-closed-outline"></ion-icon>
              <input v-model="cfmPassword" type="password" required>
              <label for="">Re-Enter Your Password</label>
            </div>
            <!--<button><router-link to="/loading">Register</router-link></button>-->
            <button @click.prevent="register">Register</button>
            <div class="login">
              <p>Already have an account? <router-link to="/login">Login</router-link></p>
            </div>
          </form>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import { useRouter } from 'vue-router';
//import { onMounted, ref } from 'vue';
import { ref } from 'vue';

export default {
  setup() {
    const router = useRouter(); // Access the router

    // Define reactive properties for email and password
    const email = ref('');
    const cfmEmail = ref('');
    const password = ref('');
    const cfmPassword = ref('');
    const fullName = ref('');

    // Function to perform login
    const register = () => {

      if (!email.value || !cfmEmail.value || !password.value || !cfmPassword.value || !fullName.value ) {
        alert('Please fill in all the fields.');
        return; // Exit the function if any field is blank
      }

      if (email.value !== cfmEmail.value ) {
        alert('Email addresses do not match!');
        return; // Exit the function if any field is blank
      }

      if (password.value !== cfmPassword.value ) {
        alert('Passwords do not match!');
        return; // Exit the function if any field is blank
      }


      console.log('Registering...');
      const requestData = {
        email: email.value,
        password: password.value,
        fullName: fullName.value
      };

      const regURL = 'http://localhost:8080/api/auth/signup';

      
      const options = {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(requestData)
      };

      fetch(regURL, options)
        .then(response => {
          if (!response.ok) {
            if (response.status === 400) {
              // Handle 400 Bad Request error - Duplicate 
              alert('Account already exist with this email!');
            } else {
              // Handle other errors
              alert('Register Failed');
            }
            throw new Error('Network response was not ok');
          }
          return response.json();
        })
        .then(data => {
          // Handle the response data here
          console.log(data);
          console.log('Here');
          alert('Registration successful!');
          router.push({ name: 'LoginPageView' });
        })
        .catch(error => {
          // Handle errors here
          console.error('There was a problem with the fetch operation:', error);
        });
    };

    // Function to retrieve email and password from session storage
    /*
    const retrieveData = () => {
      email.value = sessionStorage.getItem('email');
      password.value = sessionStorage.getItem('password');

    };
    */

    // Call retrieveData function when the component is mounted
    /*
    onMounted(() => {
      retrieveData();
    });
    */

    return {
      email,
      cfmEmail,
      password,
      cfmPassword,
      fullName,
      register
    };
  }
};
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Poppins:wght@500&display=swap');
*{
    margin: 0;
    padding: 0;
    font-family: 'Poppins', sans-serif;
}
section{
    display: flex;
    justify-content: center;
    align-items: center;
    min-height: 100vh;
    width: 100%;
    background: url('../assets/concertbackground.jpg') no-repeat;
    background-position: center;
    background-size: cover;
}
.form-box{
    position: relative;
    width: 400px;
    height: 600px;
    background: transparent;
    border: 2px solid rgba(255,255,255,0.5);
    border-radius: 20px;
    backdrop-filter: blur(15px);
    display: flex;
    justify-content: center;
    align-items: center;
}
h2{
    font-size: 2em;
    color: #000; /* Changed color to black */
    text-align: center;
}
.inputbox{
    position: relative;
    margin: 30px 0;
    width: 310px;
    border-bottom: 2px solid #000; /* Changed color to black */
}
.inputbox label{
    position: absolute;
    top: 50%;
    left: 5px;
    transform: translateY(-50%);
    color: #000; /* Changed color to black */
    font-size: 1em;
    pointer-events: none;
    transition: .5s;
}
input:focus ~ label,
input:valid ~ label{
    top: -5px;
}
.inputbox input {
    width: 100%;
    height: 50px;
    background: transparent;
    border: none;
    outline: none;
    font-size: 1em;
    padding:0 35px 0 5px;
    color: #000; /* Changed color to black */
}
.inputbox ion-icon{
    position: absolute;
    right: 8px;
    color: #000; /* Changed color to black */
    font-size: 1.2em;
    top: 20px;
}
.forget{
    margin: -15px 0 15px ;
    font-size: .9em;
    color: #000; /* Changed color to black */
    display: flex;
    justify-content: space-between;  
}
.forget label input{
    margin-right: 3px;
}
.forget label a{
    color: #000; /* Changed color to black */
    text-decoration: none;
}
.forget label a:hover{
    text-decoration: underline;
}
button{
    width: 100%;
    height: 40px;
    border-radius: 40px;
    background: #000; /* Changed color to black */
    border: none;
    outline: none;
    cursor: pointer;
    font-size: 1em;
    font-weight: 600;
    color: #fff; /* Changed color to white */
}
.login{
    font-size: .9em;
    color: #fff; /* Changed color to white */
    text-align: center;
    margin: 25px 0 10px;
}
.login p a{
    text-decoration: none;
    color: #fff; /* Changed color to white */
    font-weight: 600;
}
.login p a:hover{
    text-decoration: underline;
}

</style>
