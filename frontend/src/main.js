// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue';
import Vuex from 'vuex';
import Router from 'vue-router'
import store from './store/index';
import axios from 'axios';
import Vuetify from 'vuetify/lib';
import 'vuetify/dist/vuetify.min.css';
import MultiFiltersPlugin from './plugins/MultiFilters'
import App from './App';
import router from './router/router';
import moment from 'moment'
import VueFeather from 'vue-feather';

Vue.use(Vuex)
Vue.use(VueFeather);
Vue.use(moment);
Vue.use(MultiFiltersPlugin);
Vue.use(Vuetify, {
  theme: {
    primary: {
      //warna item buat text biasa
      base: '#4B4A56',
      //warna abu buat stroke
      lighten1: '#D8D8D8',
      //buat progress bar yg inactive
      lighten2: '#A1CD43',
      //buat status under review
      lighten3: '#757575',
      //buat text abu muda
      lighten4: '#A09EB5',
      lighten5: '#273043'

    },
    //buat text warna abu-abu
    secondary: '#7A7E84',
    //biru
    accent: '#48ACF0',
    //merah
    error: '#EC1E24',
    // info: '#2196F3',
    //ijo
    success: '#A1CD43',
    //kuning
    warning: '#FDCB24'
  }
});

// Vue.use(axios);
Vue.prototype.$axios = axios;

// Vue.use(Router);
// Vue.prototype.$router = Router;

// axios.defaults.baseURL = "http://localhost:8080"
Vue.config.productionTip = false;

Vue.filter('formatDate', function(value) {
  if (value) {
    return moment(String(value)).format('MM/DD/YYYY')
  }
});
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  axios,
  moment,
  components: { App },
  template: '<App/>',
});
