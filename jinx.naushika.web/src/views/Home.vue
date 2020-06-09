<template>
  <div>
    <van-button type="primary" @click="handleClear">Clear</van-button>
    <div>
      <van-button type="primary" @click="handleSectionClick(index)" v-for="(item, index) in Contents.Children" :key="'contents' + index" v-text="item.Name">{{ item.Name }}</van-button>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      Contents: this.$store.state.contents
    };
  },
  mounted() {
    let contents = this.$store.state.contents;
    console.log(contents);
  },
  methods: {
    handleClear() {
      this.$store.commit("clearContents");
      this.$toast("已清空");
    },
    handleSectionClick(index) {
      this.$router.push({
        path: "/view",
        query: { section: index, page: 0 }
      });
    }
  }
};
</script>
