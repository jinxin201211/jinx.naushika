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
      this.getContents();
    } else {
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
          }
        })
        .catch(function(err) {
          console.log(err);
        });
    }
  }
};
</script>
