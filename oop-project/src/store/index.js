// store/index.js
import { createStore } from 'vuex';

export default createStore({
  state: {
    isLoggedIn: false,
  },
  mutations: {
    setEmail(state, email) {
      state.email = email;
    },
    setPassword(state, password) {
      state.password = password;
    },
  },  
  actions: {
    login({ commit }) {
      // Perform login logic here
      // For demonstration purposes, I'm setting isLoggedIn to true
      commit('setLoggedIn', true);
    },
  },
});
