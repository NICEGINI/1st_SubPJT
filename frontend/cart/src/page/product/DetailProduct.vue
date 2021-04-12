<template>
     <div>
          <Info></Info>
          <CheapPriceLink></CheapPriceLink>
     </div>
</template>

<script>
import Info from "@/components/detail/Info.vue";
import CheapPriceLink from "@/components/detail/CheapPriceLink.vue";
export default {
     components: {
          Info,
          CheapPriceLink,
     },
     created() {
          if (localStorage.getItem("userId") != "" && localStorage.getItem("userId") != null) {
               sessionStorage.setItem("isLogin", true);
               this.$store.commit("TOGGLE_LOGIN_STATE");
          } else {
               this.$router.push("/login");
          }
     },
};
</script>

<style scoped></style>
