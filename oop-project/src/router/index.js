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
import CreateEventView from '@/pages/CreateEventView.vue';
import TicketingOfficerView from '@/pages/TicketingOfficerView.vue';
import AddTicketingOfficer from '@/pages/AddTicketingOfficer.vue';
import StatisticsView from '@/pages/StatisticsView.vue';
import ViewTicketingOfficer from '@/pages/ViewTicketingOfficer.vue';
import LogoutView from '@/pages/LogoutView.vue';


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
    path: '/logout',
    name:'LogoutView',
    component: LogoutView
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
    path: '/intoeventview/:eventId/:eventTitle/:eventDesc/:eventDate/:eventImg/:eventPrice',
    name: 'IntoEventView',
    component: IntoEventView 
  },
  {
    path: '/eventmanager',
    name: 'EventManagerView',
    component: EventManagerView
  },
  {
    path: '/eventmanager/createevent',
    name: 'CreateEventView',
    component: CreateEventView
  },
  {
    path:'/ticketingofficer',
    name:'TicketingOfficerView',
    component: TicketingOfficerView
  },
  {
    path: '/eventmanager/addTicketingOfficer',
    name: 'AddTicketingOfficer',
    component: AddTicketingOfficer,
  },
  {
    path: '/eventmanager/statistics',
    name: 'StatisticsView',
    component: StatisticsView,
  },
  {
    path: '/eventmanager/viewticketingofficer',
    name: 'ViewTicketingOfficer',
    component: ViewTicketingOfficer,
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;
