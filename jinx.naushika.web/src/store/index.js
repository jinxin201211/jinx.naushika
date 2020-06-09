import Vue from "vue";
import Vuex from "vuex";
Vue.use(Vuex);

window.$VuexPrefix = "jinx.naushika.web.";

export default new Vuex.Store({
  state: {
    section: localStorage.getItem(window.$VuexPrefix + "section") ? localStorage.getItem(window.$VuexPrefix + "section") * 1 : 0,
    page: localStorage.getItem(window.$VuexPrefix + "page") ? localStorage.getItem(window.$VuexPrefix + "page") * 1 : 0,
    contents: localStorage.getItem(window.$VuexPrefix + "contents") ? JSON.parse(localStorage.getItem(window.$VuexPrefix + "contents")) : null
  },
  mutations: {
    changeSection(state, section) {
      state.section = section;
      localStorage.setItem(window.$VuexPrefix + "section", section);
    },
    changePage(state, page) {
      state.page = page;
      localStorage.setItem(window.$VuexPrefix + "page", page);
    },
    changeContents(state, contents) {
      state.contents = contents;
      localStorage.setItem(window.$VuexPrefix + "contents", JSON.stringify(contents));
    },
    resetSection(state) {
      state.section = 0;
      localStorage.setItem(window.$VuexPrefix + "section", 0);
    },
    resetPage(state) {
      state.page = 0;
      localStorage.setItem(window.$VuexPrefix + "page", 0);
    },
    clearContents(state) {
      state.contents = null;
      localStorage.setItem(window.$VuexPrefix + "contents", "");
    }
  },
  actions: {},
  modules: {}
});
