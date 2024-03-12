// store/index.js
import { createStore } from 'vuex';

export default createStore({
  state: {
    isLoggedIn: false,
  },
  mutations: {
    setLoggedIn(state, value) {
      state.isLoggedIn = value;
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
