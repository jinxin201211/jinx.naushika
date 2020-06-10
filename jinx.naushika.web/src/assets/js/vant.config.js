import { Button, Toast, Image, Loading, Popup } from "vant";

export default {
  install(V) {
    V.use(Button)
      .use(Toast)
      .use(Image)
      .use(Loading)
      .use(Popup);
    V.prototype.$toast = Toast;
  }
};
