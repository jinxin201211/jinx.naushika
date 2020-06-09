import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";

Vue.config.productionTip = false;

import axios from "axios";
import VueAxios from "vue-axios";
Vue.use(VueAxios, axios);
import "./request.config.js";

import VueCookies from "vue-cookies";
Vue.use(VueCookies);

window.$ServiceRootPath = "http://118.24.64.59:8001";
window.$ContentRootPath = "http://118.24.64.59:8002";

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount("#app");
