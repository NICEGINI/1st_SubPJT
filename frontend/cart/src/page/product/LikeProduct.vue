<template>
     <div id="app" class="columns mt-4">
          <div id="container">
               <br />
               <h3 class="highlight">나의 찜 목록</h3>
               <br />
               <div class="column">
                    <hr />
                    <like
                         v-for="(like, index) in likes"
                         :like="like"
                         :index="index + 1"
                         :key="index"
                         @del="del"
                    />
                    <hr />
               </div>
          </div>
     </div>
</template>

<script>
import Like from "@/components/shopping/Like.vue";
import http from "@/util/http-common.js";

export default {
     components: {
          Like,
     },
     data() {
          return {
               likes: [],
          };
     },
     created() {
          // 로컬에 로그인 정보가 있는 경우만 찜목록에 접근 가능
          if (localStorage.getItem("userId") != "" && localStorage.getItem("userId") != null) {
               sessionStorage.setItem("isLogin", true);
               this.$store.commit("TOGGLE_LOGIN_STATE");
          } else {
               this.$router.push("/login");
          }
          let member_id = sessionStorage.getItem("userId");
          console.log(member_id);
          http.get("/wishList", {
               params: {
                    memberId: member_id,
               },
               withCredentials: true,
          })
               .then((response) => {
                    this.likes = response.data;
                    console.log(response);
               })
               .catch((error) => {
                    console.log(error);
               });
     },
     methods: {
          async del() {
               let member_id = sessionStorage.getItem("userId");
               console.log(member_id);
               await setTimeout(() => {
                    this.likes.splice(0);
                    http.get("/wishList", {
                         params: {
                              memberId: member_id,
                         },
                         withCredentials: true,
                    })
                         .then((response) => {
                              this.likes = response.data;
                              console.log(res);
                         })
                         .catch((error) => {
                              console.log(error);
                         });
               }, 200);
          },
     },
};
</script>

<style scoped></style>
