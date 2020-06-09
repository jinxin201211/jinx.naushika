import { Button, Toast, Image, Loading } from "vant";

export default {
  install(V) {
    V.use(Button)
      .use(Toast)
      .use(Image)
      .use(Loading);
    V.prototype.$toast = Toast;
  }
};
