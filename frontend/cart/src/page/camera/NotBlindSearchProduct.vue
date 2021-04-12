<template>
     <div id="container">
          <Camera></Camera>
          <NotBlindMode></NotBlindMode>
          <!-- <mode-choice></mode-choice> -->
     </div>
</template>

<script>
import Camera from "@/components/camera/Camera.vue";
import NotBlindMode from "@/components/camera/NotBlindMode.vue";
// import ModeChoice from "../components/camera/ModeChoice.vue"
export default {
     components: {
          Camera,
          NotBlindMode,
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

<style></style>
