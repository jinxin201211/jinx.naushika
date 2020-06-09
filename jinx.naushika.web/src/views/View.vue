<template>
  <div>
    <van-button type="primary" @click="Page--" :disabled="Page === 0">上一页</van-button>
    <van-button type="primary" @click="Page++" :disabled="Page === Contents.Children[Section].Children[Page].length - 1">下一页</van-button>
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
  </div>
</template>
<script>
export default {
  data() {
    return {
      Contents: this.$store.state.contents,
      Section: this.$route.query.section,
      Page: this.$route.query.page
    };
  },
  computed: {
    Image: function() {
      return `${this.$ContentRootPath}/${this.Contents.Children[this.Section].Name}/${this.Contents.Children[this.Section].Children[this.Page].Name}`;
    },
    ImageNext: function() {
      let page = this.Page == this.Contents.Children[this.Section].Children[this.Page].length - 1 ? this.Page : this.Page + 1;
      return `${this.$ContentRootPath}/${this.Contents.Children[this.Section].Name}/${this.Contents.Children[this.Section].Children[page].Name}`;
    }
  },
  mounted() {
    let contents = this.$store.state.contents;
    this.console.log(contents);
  },
  methods: {
    handleClear: function() {
      this.$store.commit("clearContents");
      this.$toast("已清空");
    }
  }
};
</script>
