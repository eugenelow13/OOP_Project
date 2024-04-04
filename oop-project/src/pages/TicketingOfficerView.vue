<template>
  <div class="p-sm-5 p-3">
    <h1>Hello Ticketing Officer</h1>
    <form>
      <div class="form-group p-0">
        <label for="ticketID">Enter Booking ID:</label>
        <input type="text" v-model="ticketID" @input="resetValid">

        <button @click.prevent="verify" class="verify" :class="{ 'verified': isValid }"
          type="submit">{{ isValid ? 'Verified &#10004;' : 'Verify' }}</button>

        <button :disabled="!canAdmit" class="verify" @click.prevent="admit"
          type="submit">Admit</button>
      </div>
    </form>

    <div v-if="ticketInfo" class="card mb-3">
      <div class="card-body">
        Ticket: {{ ticketInfo.id }} ({{ ticketInfo.isAdmitted ? "Admitted": "Not Admitted" }})<br>
        Booker: {{ ticketInfo.bookingCustomerName }} ({{ ticketInfo.bookingCustomerEmail }})
      </div>
    </div>

    <div id="reader">
    </div>
  </div>
</template>

<script>
import { computed, onMounted, ref } from 'vue';
import { Html5QrcodeScanner } from "html5-qrcode";

export default {
  name: 'TicketingOfficerView',

  setup() {
    const ticketInfo = ref(null);
    const ticketID = ref(null);
    const isValid = ref(false);

    const canAdmit = computed(() => {
      return isValid.value && ticketInfo.value && !ticketInfo.value.isAdmitted;
    })

    const verify = () => {
      console.log(ticketID.value);
      // isValid.value = true;

      fetch("http://localhost:8080/api/tickets/" + ticketID.value, {
          headers: {
             Authorization: "Bearer " + sessionStorage.getItem("token")
          }
      })
      .then(async (response) => {
        if (response.ok) {
          console.log()
          isValid.value = true;
          const data = await response.json();
          ticketInfo.value = data.data;
        } else {
          isValid.value = false;
        }
      })
    }

    function admit() {
      fetch("http://localhost:8080/api/tickets/" + ticketID.value + "/admit", {
        method: "POST",
        headers: {
          Authorization: "Bearer " + sessionStorage.getItem("token")
        }
      })
      .then(async (response) => {
        if (response.ok) {
          const data = await response.json();
          ticketInfo.value = data.data;
        } else {
          console.log("Failed to admit");
        }
      })
    }

    const resetValid = () => {
      isValid.value = false;
    }

    onMounted(() => {
      function onScanSuccess(decodedText, decodedResult) {
        // handle the scanned code as you like, for example:
        ticketID.value = decodedText;
        console.log(decodedResult);
      }

      function onScanFailure(error) {
        // handle scan failure, usually better to ignore and keep scanning.
        // for example:
        console.warn(`Code scan error = ${error}`);
      }

      let html5QrcodeScanner = new Html5QrcodeScanner(
        "reader",
        { fps: 10, qrbox: { width: 250, height: 250 } },
        /* verbose= */ false);
      html5QrcodeScanner.render(onScanSuccess, onScanFailure);
    })


    return {
      ticketID,
      ticketInfo,
      canAdmit,
      verify,
      admit,
      isValid,
      resetValid,
    }
  }
}

</script>

<style>
.form-group {
  margin-bottom: 20px;
  text-align: left;
  margin-left: 10px;

}

.form-group input {
  padding: 10px;
  border: 1px solid;
  border-radius: 5px;
  margin-right: 10px;
  width: 50vw;

}

.form-group label {
  display: block;
  font-weight: bold;
  margin-bottom: 5px;
}

.form-group button {
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
  margin-bottom: 10px;
  background-color: orange;
  margin-right: 10px;
}

.form-group button:hover {
  background-color: rgb(172, 113, 2);
}

.form-group button.verified {
  background-color: rgb(1, 177, 1);
}
</style>