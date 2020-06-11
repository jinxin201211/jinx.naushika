import { Button, Toast, Image, Loading, Popup, Slider } from "vant";

export default {
  install(V) {
    V.use(Button)
      .use(Toast)
      .use(Image)
      .use(Loading)
      .use(Popup)
      .use(Slider);
    V.prototype.$toast = Toast;
  }
};
