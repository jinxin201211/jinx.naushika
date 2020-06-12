<template>
  <div style="padding: 30px 20px; width: 100%; height: 100%; position: relative; background: #ffffff; color: #000000;" v-tap="handleBodyTap">
    <div style="width: 100%; height: 100%; display: flex; justify-content: center; align-items: center; overflow: scroll;" v-swipeleft="handleSwipeLeft" v-swiperight="handleSwipeRight">
      <van-image :src="Image" :class="{ zoomout: image_zoom_out }">
        <template v-slot:loading>
          <van-loading type="spinner" size="20" />
        </template>
        <template v-slot:error>加载失败</template>
      </van-image>
    </div>
    <div style="position: fixed; top: 0; left: 0; width: 100%; line-height: 30px; padding: 0 20px;"><span v-text="SectionName"></span></div>
    <div style="position: fixed; bottom: 0; left: 0; width: 100%; text-align: center; line-height: 30px; padding: 0 20px;">
      <span v-text="PageName"></span>
      <span v-text="Summary.Current + '/' + Summary.Sum" style="float: right;"></span>
    </div>
    <div class="jinx-tools-panel" :class="{ show: show_tools }" v-tap.stop="handleNone">
      <div style="width: 100%; box-sizing: border-box; position: relative; padding: 0 55px; height: 30px; padding-top: 15px; font-size: 12px;">
        <span v-tap="handlePrevSection" style="position: absolute; top: 0; left: 0; line-height: 30px;">上一卷</span>
        <van-slider v-model="Page.Current" :max="Page.Max" :min="Page.Min" active-color="#e1e1e1" inactive-color="#e1e1e1" bar-height="2">
          <template #button>
            <div class="jinx-slider-button">
              {{ Page.Current }}
            </div>
          </template>
        </van-slider>
        <span v-tap="handleNextSection" style="position: absolute; top: 0; right: 0; line-height: 30px;">下一卷</span>
      </div>
      <div style="margin-top: 20px;">
        <span v-tap="handleViewContent">查看目录</span>
        <span v-tap="handleClear" style="margin-left: 20px;">清空目录</span>
      </div>
    </div>
    <van-popup v-model="show_content" position="left" :style="{ height: '100%', width: '80%' }" v-tap.stop="handleNone">
      <div style="color: #000000; font-size: 14px;">
        <div style="height: 50px; line-height: 50px; background: #f1f1f1; text-align: center;">目录</div>
        <div style="padding: 10px;">
          <div style="height: 50px; line-height: 50px; border-bottom: 1px solid #999999;" @click="handleContentClick(index)" v-for="(item, index) in Contents.Children" :key="'content_' + index"><span v-text="item.Name"></span></div>
        </div>
      </div>
    </van-popup>
  </div>
</template>
<script>
import "@/assets/js/vue-touch.js";

export default {
  data() {
    return {
      Contents: {},
      Section: {
        Min: 0,
        Max: 0,
        Current: 0
      },
      Page: {
        Min: 0,
        Max: 0,
        Current: 0
      },
      show_content: false,
      show_tools: false,
      click_event: false,
      image_zoom_out: false,
      touch_event: {
        start: {
          x: 0,
          y: 0,
          timestamp: 0
        },
        end: {
          x: 0,
          y: 0,
          timestamp: 0
        }
      }
    };
  },
  computed: {
    Image: function() {
      return `${this.$ContentRootPath}/${this.Contents.Children[this.Section.Current].Name}/${this.Contents.Children[this.Section.Current].Children[this.Page.Current].Name}`;
    },
    Summary: function() {
      let sum = 0;
      let current = 0;
      for (let i = 0; i < this.Contents.Children.length; i++) {
        sum += this.Contents.Children[i].Children.length;
        if (i < this.Section.Current) {
          current += this.Contents.Children[i].Children.length;
        }
      }
      current += this.Page.Current + 1;
      return {
        Current: current,
        Sum: sum
      };
    },
    SectionName: function() {
      return this.Contents.Children[this.Section.Current].Name;
    },
    PageName: function() {
      return this.Contents.Children[this.Section.Current].Children[this.Page.Current].Name;
    }
  },
  created() {
    this.Contents = this.$store.state.contents;
    this.Section.Max = this.Contents.Children.length - 1;
    this.Section.Current = this.$store.state.section;
    this.Page.Max = this.Contents.Children[this.Section.Current].Children.length - 1;
    this.Page.Current = this.$store.state.page;
  },
  methods: {
    handleNone() {},
    handleClear: function() {
      this.$store.commit("clearContents");
      this.$store.commit("clearSection");
      this.$store.commit("clearPage");
      this.$toast("已清空");
    },
    handleBodyTap(event) {
      const that = this;
      if (that.click_event) {
        clearTimeout(that.click_event);
        that.click_event = false;
        that.handleDoubleClick();
      } else {
        that.click_event = setTimeout(() => {
          that.click_event = false;
          that.handleSingleClick(event);
        }, 300);
      }
    },
    handleSwipeLeft() {
      if (this.image_zoom_out) {
        return;
      }
      this.handlePrevPage();
    },
    handleSwipeRight() {
      if (this.image_zoom_out) {
        return;
      }
      this.handleNextPage();
    },
    handleSingleClick(event) {
      if (event.target.className.indexOf("van-overlay") != -1) {
        return;
      }
      const top = event.changedTouches[0].clientY;
      const left = event.changedTouches[0].clientX;
      const clientHeight = document.body.clientHeight || document.documentElement.clientHeight;
      const clientWidth = document.body.clientWidth || document.documentElement.clientWidth;
      //判断是否在中心区域
      if (top > clientHeight / 3 && top < (clientHeight * 2) / 3 && left > clientWidth / 3 && left < (clientWidth * 2) / 3) {
        this.handleCenterClick();
      }
      //判断是否在右边
      else if (left > clientWidth / 2) {
        this.handleNextPage();
      }
      //在左边
      else {
        this.handlePrevPage();
      }
    },
    handleDoubleClick() {
      this.image_zoom_out = !this.image_zoom_out;
    },
    handleCenterClick() {
      this.show_tools = !this.show_tools;
    },
    handleContentClick(index) {
      this.Section.Current = index;
      this.Page.Current = 0;
      this.$store.commit("changeSection", index);
      this.$store.commit("changePage", 0);
      this.show_content = false;
      this.image_zoom_out = false;
    },
    handlePrevPage() {
      if (this.Page.Current === 0 && this.Section.Current === 0) {
        return;
      }
      this.Page.Current--;
      if (this.Page.Current === -1) {
        this.Section.Current--;
        this.Page.Current = this.Contents.Children[this.Section.Current].Children.length - 1;
        this.Page.Max = this.Contents.Children[this.Section.Current].Children.length - 1;
      }
      this.$store.commit("changeSection", this.Section.Current);
      this.$store.commit("changePage", this.Page.Current);
      this.image_zoom_out = false;
    },
    handleNextPage() {
      if (this.Page.Current === this.Page.Max && this.Section.Current === this.Section.Max) {
        return;
      }
      this.Page.Current++;
      if (this.Page.Current === this.Contents.Children[this.Section.Current].Children.length) {
        this.Page.Current = 0;
        this.Section.Current++;
        this.Page.Max = this.Contents.Children[this.Section.Current].Children.length - 1;
      }
      this.$store.commit("changeSection", this.Section.Current);
      this.$store.commit("changePage", this.Page.Current);
      this.image_zoom_out = false;
    },
    handlePrevSection() {
      if (this.Section.Current === 0) {
        return;
      }
      this.Section.Current--;
      this.Page.Current = 0;
      this.Page.Max = this.Contents.Children[this.Section.Current].Children.length - 1;
      this.$store.commit("changeSection", this.Section.Current);
      this.$store.commit("changePage", this.Page.Current);
      this.image_zoom_out = false;
    },
    handleNextSection() {
      if (this.Section.Current === this.Section.Max) {
        return;
      }
      this.Section.Current++;
      this.Page.Current = 0;
      this.Page.Max = this.Contents.Children[this.Section.Current].Children.length - 1;
      this.$store.commit("changeSection", this.Section.Current);
      this.$store.commit("changePage", this.Page.Current);
      this.image_zoom_out = false;
    },
    handleViewContent() {
      setTimeout(() => {
        this.show_content = true;
      }, 100);
    }
  }
};
</script>
<style lang="less">
.jinx-tools-panel {
  padding: 25px 15px;
  position: fixed;
  bottom: -100%;
  left: 0;
  width: 100%;
  background: rgba(0, 0, 0, 0.8);
  color: #e1e1e1;
  transition: bottom 0.3s;
}
.jinx-tools-panel.show {
  bottom: 0;
}
.jinx-slider-button {
  width: 26px;
  color: #000000;
  font-size: 12px;
  line-height: 18px;
  text-align: center;
  background-color: #f1f1f1;
  border-radius: 100px;
  border: 1px solid #000000;
}

.zoomout {
  max-height: 100%;
}

.zoomout > img {
  width: 150%;
  // width: 150%;
}
</style>
