<template>
  <div>
    <van-button type="primary" @click="handlePrevPage" :disabled="Page === 0" size="small">上一页</van-button>
    <van-button type="primary" @click="handleNextPage" :disabled="Page === Contents.Children[Section].Children[Page].length - 1" size="small">下一页</van-button>
    <div v-text="Contents.Children[Section].Name"></div>
    <div v-text="Contents.Children[Section].Children[Page].Name"></div>
    <van-image :src="Image">
      <template v-slot:loading>
        <van-loading type="spinner" size="20" />
      </template>
      <template v-slot:error>加载失败</template>
    </van-image>
    <van-image :src="ImageNext" v-show="false">
      <template v-slot:loading>
        <van-loading type="spinner" size="20" />
      </template>
      <template v-slot:error>加载失败</template>
    </van-image>
    <div>
      <van-button type="primary" @click="handleViewContent" size="small">查看目录</van-button>
      <van-button type="primary" @click="handleClear" size="small">清空目录</van-button>
    </div>
    <van-popup v-model="show_content" position="left">
      <div>
        <van-button type="primary" @click="handleContentClick(index)" v-for="(item, index) in Contents.Children" :key="'content_' + index"><span v-text="item.Name"></span></van-button>
      </div>
    </van-popup>
  </div>
</template>
<script>
export default {
  data() {
    return {
      Contents: this.$store.state.contents,
      Section: this.$store.state.section,
      Page: this.$store.state.page,
      show_content: false
    };
  },
  computed: {
    Image: function() {
      return `${this.$ContentRootPath}/${this.Contents.Children[this.Section].Name}/${this.Contents.Children[this.Section].Children[this.Page].Name}`;
    },
    ImageNext: function() {
      let page = this.Page == this.Contents.Children[this.Section].Children.length - 1 ? this.Page : this.Page + 1;
      return `${this.$ContentRootPath}/${this.Contents.Children[this.Section].Name}/${this.Contents.Children[this.Section].Children[page].Name}`;
    }
  },
  mounted() {
    let contents = this.$store.state.contents;
    console.log(this.Contents);
    console.log(this.Section);
    console.log(this.Page);
    // this.$store.commit("changeSection", 0);
    // this.$store.commit("changePage", 0);
    // this.handleClear();
    // this.Section = 0;
    this.Page = this.Contents.Children[this.Section].Children.length - 1;
    this.$store.commit("changePage", this.Page);
  },
  methods: {
    handleClear: function() {
      this.$store.commit("clearContents");
      this.$toast("已清空");
    },
    handleViewContent() {
      this.show_content = true;
    },
    handleContentClick(index) {
      this.Section = index;
      this.Page = 0;
      this.$store.commit("changeSection", index);
      this.$store.commit("changePage", 0);
      this.show_content = false;
    },
    handlePrevPage() {
      this.Page--;
      if (this.Page === -1) {
        this.Section--;
        this.Page = this.Contents.Children[this.Section].Children.length - 1;
      }
      this.$store.commit("changeSection", this.Section);
      this.$store.commit("changePage", this.Page);
    },
    handleNextPage() {
      this.Page++;
      if (this.Page === this.Contents.Children[this.Section].Children.length) {
        this.Page = 0;
        this.Section++;
      }
      this.$store.commit("changeSection", this.Section);
      this.$store.commit("changePage", this.Page);
    }
  }
};
</script>
