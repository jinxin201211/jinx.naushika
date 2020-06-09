<template>
  <div>
    <a @click="handleClear">Clear</a>
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
      console.log("已清空");
    }
  }
};
</script>
