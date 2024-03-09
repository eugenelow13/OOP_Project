// src/router.js
import { createRouter, createWebHistory } from 'vue-router';
import HomepageView from '../pages/HomepageView.vue';
import LoginPageView from '../pages/LoginPageView.vue';
import RegisterView from '../pages/RegisterView.vue'; // Import the RegisterView.vue component
import LoadingView from '../pages/LoadingView.vue';

const routes = [
  {
    path: '/',
    name: 'HomepageView',
    component: HomepageView
  },
  {
    path: '/login',
    name: 'LoginPageView',
    component: LoginPageView
  },
  {
    path: '/register', // Define the path for the register page
    name: 'RegisterView', // Name the route
    component: RegisterView // Specify the RegisterView component
  },
  {
    path: '/loading', // Define the path for the register page
    name: 'LoadingView', // Name the route
    component: LoadingView // Specify the RegisterView component
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;
