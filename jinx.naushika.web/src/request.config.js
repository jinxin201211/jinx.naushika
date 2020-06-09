import axios from "axios";

// 添加请求拦截器，在请求头中加token
axios.interceptors.request.use(
  config => {
    // config.headers["lw-token"] = sessionStorage.getItem(window.$VuexPrefix + "Token");
    if (!config.url.startsWith("/")) {
      config.url = "/" + config.url;
    }
    config.url = config.url.replace("/api", window.$ServiceRootPath);
    return config;
  },
  error => {
    return Promise.reject(error);
  }
);
