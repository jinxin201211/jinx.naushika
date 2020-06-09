<template>
  <div>
    <van-button type="primary" @click="handleClear">Clear</van-button>
  </div>
</template>
<script>
export default {
  mounted() {
    let contents = this.$store.state.contents;
    console.log(contents);
  },
  methods: {
    handleClear: function() {
      this.$store.commit("clearContents");
      this.$toast("已清空");
    }
  }
};
</script>
