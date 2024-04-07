<template>
  <div>
    <section>
      <div class="form-box">
        <div class="form-value">
          <form action="">
            <h2>Register</h2>
            <div class="inputbox">
              <ion-icon name="mail-outline"></ion-icon>
              <input required>
              <label for="">Enter Your Email</label>
            </div>
            <div class="inputbox">
              <ion-icon name="mail-outline"></ion-icon>
              <input required>
              <label for="">Re-Enter Your Email</label>
            </div>
            <div class="inputbox">
              <ion-icon name="lock-closed-outline"></ion-icon>
              <input type="password" required>
              <label for="">Enter Your Password</label>
            </div>
            <div class="inputbox">
              <ion-icon name="lock-closed-outline"></ion-icon>
              <input type="password" required>
              <label for="">Re-Enter Your Password</label>
            </div>
            <button><router-link to="/loading">Register</router-link></button>
          </form>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
export default {
  data() {
    return {
      isLoading: false,
    };
  },
  methods: {
    register() {
      this.isLoading = true;
      // Perform registration logic here
      setTimeout(() => {
        // Simulate registration process completion after 3 seconds
        this.isLoading = false;
      }, 3000);
    },
  },
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
    height: 550px;
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
