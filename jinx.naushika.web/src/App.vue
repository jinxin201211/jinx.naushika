<template>
  <div>
    <router-view />
  </div>
</template>
<script>
export default {
  mounted() {
    let contents = this.$store.state.contents;
    if (contents == null) {
      this.$toast("请求获取");
      this.getContents();
    } else {
      this.$toast("读取存储");
      this.$router.replace("/home");
    }
  },
  methods: {
    getContents: function() {
      const that = this;
      this.axios
        .post("/api/comic/contents")
        .then(function(response) {
          if (response && response.data.code === 0) {
            that.$store.commit("changeContents", response.data.data);
            that.$router.replace("/home");
          } else {
            that.$toast(response.data.msg);
          }
        })
        .catch(function(err) {
          console.log(err);
          that.$toast("请求失败");
        });
    }
  }
};
</script>
