<template>
  <div>
    <section>
      <div class="form-box">
        <div class="form-value">
          <form action="">
            <h2>Login</h2>
            <div class="inputbox">
              <ion-icon name="lock-closed-outline"></ion-icon>
              <input v-model="email" required>
              <label for="">Email</label>
            </div>
            <div class="inputbox">
              <ion-icon name="lock-closed-outline"></ion-icon>
              <input type="password" v-model="password" required>
              <label for="">Password</label>
            </div>
            <div class="forget">
              <label for=""><input type="checkbox">Remember Me</label>
            </div>
            <button @click.prevent="login">Log in</button>
            <div class="register">
              <p>Don't have an account <router-link to="/register">Register</router-link></p>
            </div>
            <div class="register">
              <p>To: <router-link to="/">Home</router-link></p>
            </div>
             <RegisterErrorComponent :show="showError" :errorMessage="errorMessage" @close="showError = false" />
          </form>
        </div>
      </div>
    </section>
    <!-- Include the ErrorMessageComponent here -->
    <!-- <ErrorMessageComponent :show="showError" :errorMessage="errorMessage" @close="showError = false" /> -->
   
  </div>
</template>

<script>
import { useRouter } from 'vue-router';
import { onMounted, ref } from 'vue';
//import ErrorMessageComponent from '../components/ErrorMessageComponent.vue'; // Adjust the path based on your project structure
import RegisterErrorComponent from '../components/RegisterErrorComponent.vue';

export default {
  components: {
    //ErrorMessageComponent,
    RegisterErrorComponent,

  },
  setup() {
    const router = useRouter(); // Access the router

    // Define reactive properties for email and password
    const email = ref('');
    const password = ref('');
    const showError = ref(false);
    const errorMessage = ref('');

    // Clear sessionStorage
    sessionStorage.clear();

    // Function to perform login
    const login = () => {
      console.log('Logging in...');
      const requestData = {
        email: email.value,
        password: password.value,
      };

      const options = {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(requestData)
      };

      fetch("http://localhost:8080/api/auth/login", options)
        .then(response => {
          if (!response.ok) {
            showError.value = true;
            errorMessage.value = 'Enter a valid email or password';
            throw new Error('Network response was not ok');
          }
          return response.json();
        })
        .then(data => {
          console.log(data);
          console.log('Here');
          sessionStorage.setItem('email', email.value);
          sessionStorage.setItem('password', password.value);
          sessionStorage.setItem('token', data.token);
          sessionStorage.setItem('fullName',data.fullName);
          sessionStorage.setItem('userId',data.id);
          sessionStorage.setItem('roleId',data.roles[0].id);
          router.push({ name: 'LoadingView' });
        })
        .catch(error => {
          console.error('There was a problem with the fetch operation:', error);
        });
    };

    // Function to retrieve email and password from session storage
    const retrieveData = () => {
      email.value = sessionStorage.getItem('email');
      password.value = sessionStorage.getItem('password');
    };

    // Call retrieveData function when the component is mounted
    onMounted(() => {
      retrieveData();
    });

    return {
      email,
      password,
      login,
      showError,
      errorMessage
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
    height: 450px;
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
.inputbox label {
    position: absolute;
    top: 50%;
    left: 5px;
    transform: translateY(-50%);
    color: #000; /* Changed color to black */
    font-size: 1em;
    pointer-events: none;
    /* Remove transition property */
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
.register{
    font-size: .9em;
    color: #000; /* Changed color to black */
    text-align: center;
    margin: 25px 0 10px;
}
.register p a{
    text-decoration: none;
    color: #000; /* Changed color to black */
    font-weight: 600;
}
.register p a:hover{
    text-decoration: underline;
}
</style>
