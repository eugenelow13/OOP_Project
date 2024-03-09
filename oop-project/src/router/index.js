// src/router.js
import { createRouter, createWebHistory } from 'vue-router';
import Homepage from '../pages/HomepageView.vue';
import EventPage from '../pages/EventPage.vue';

const routes = [
  {
    path: '/',
    name: 'HomepageView',
    component: Homepage
  },
  
  {
    path: '/event/:eventId',
    name: 'EventPage',
    component: EventPage,
    props: true,
  },
  // You can add more routes here for other pages if needed
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;
