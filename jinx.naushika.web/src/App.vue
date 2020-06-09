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
          }
        })
        .catch(function(err) {
          console.log(err);
        });
    }
  }
};
</script>
