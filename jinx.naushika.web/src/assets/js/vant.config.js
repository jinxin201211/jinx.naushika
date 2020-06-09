import { Button, Toast } from "vant";

export default {
  install(V) {
    V.use(Button).use(Toast);
    V.prototype.$toast = Toast;
  }
};
