import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";

Vue.config.productionTip = false;

import axios from "axios";
import VueAxios from "vue-axios";
Vue.use(VueAxios, axios);
import "@/assets/js/request.config.js";

import VueCookies from "vue-cookies";
Vue.use(VueCookies);

import Vant from "@/assets/js/vant.config.js";
import "vant/lib/index.less";
Vue.use(Vant);

window.$ServiceRootPath = "http://118.24.64.59:8001";
// window.$ServiceRootPath = "http://localhost:8001";
window.$ContentRootPath = "http://118.24.64.59:8002";
Vue.prototype.$ContentRootPath = window.$ContentRootPath;

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount("#app");
