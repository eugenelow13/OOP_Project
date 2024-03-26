// src/router.js
import { createRouter, createWebHistory } from 'vue-router';
import Homepage from '../pages/HomepageView.vue';
import EventPage from '../pages/EventPage.vue';
import HomepageView from '../pages/HomepageView.vue';
import LoginPageView from '../pages/LoginPageView.vue';
import RegisterView from '../pages/RegisterView.vue';
import LoadingView from '../pages/LoadingView.vue';
import AfterLoginView from '../pages/AfterLoginView.vue';
import ProfileView from '../pages/ProfileView.vue';
import IntoEventView from '../pages/IntoEventView.vue';
import EventManagerView from '@/pages/EventManagerView.vue';

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
  {
  // You can add more routes here for other pages if needed
    component: HomepageView
  },
  {
    path: '/login',
    name: 'LoginPageView',
    component: LoginPageView
  },
  {
    path: '/register',
    name: 'RegisterView',
    component: RegisterView
  },
  {
    path: '/loading',
    name: 'LoadingView',
    component: LoadingView
  },
  {
    path: '/afterlogin',
    name: 'AfterLoginView',
    component: AfterLoginView
  },
  {
    path: '/profile',
    name: 'ProfileView',
    component: ProfileView
  },
  {
    path: '/intoeventview/:eventId/:eventTitle/:eventDesc/:eventDate/:eventImg',
    name: 'IntoEventView',
    component: IntoEventView 
  },
  {
    path: '/eventmanager',
    name: 'EventManagerView',
    component: EventManagerView
  }  
];

const router = createRouter({
  history: createWebHistory(),
  routes
});
export default router;
