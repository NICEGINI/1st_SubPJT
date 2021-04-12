<template>
     <div id="container" class="columns mt-4">
          <categorize />
          <div class="column" v-if="state">
               <br />
               <h3 class="highlight mb-5">{{ category }}</h3>
               <hr />
               <br />
               <CategoryInfo
                    v-for="item in this.items"
                    :categoryItem="item"
                    :key="item.productNo"
               ></CategoryInfo>
          </div>
          <div class="column" v-if="!state">
               <br />
               <h3 class="highlight mb-5" style="width: 50%">이번 주 인기상품</h3>
               <hr />
               <br />
               <popular v-for="popular in populars" :popular="popular" :key="popular.productNo" />
          </div>
     </div>
</template>

<script>
import Popular from "@/components/popular/Popular.vue";
import Categorize from "@/components/popular/Categorize.vue";
import CategoryInfo from "@/components/popular/categoryInfo.vue";
import { mapGetters } from "vuex";
import http from "@/util/http-common.js";

export default {
     components: {
          Popular,
          Categorize,
          CategoryInfo,
     },
     created() {
          // 로컬에 로그인 정보가 있는 경우만 이번주 상품에 접근 가능
          if (localStorage.getItem("userId") != "" && localStorage.getItem("userId") != null) {
               sessionStorage.setItem("isLogin", true);
               this.$store.commit("TOGGLE_LOGIN_STATE");
          } else {
               this.$router.push("/login");
          }
          http.get("/bestproduct", {
               withCredentials: true,
          })
               .then((response) => {
                    this.populars = response.data;
               })
               .catch((error) => {
                    console.log(error);
               });
     },
     computed: {
          ...mapGetters({
               items: "getCategorizeItems",
               state: "getCategorizeState",
               category: "getCategorizeItem",
          }),
     },
     data() {
          return {
               noCategorize: true,
               populars: [],
          };
     },
     methods: {},
};
</script>

<style scoped></style>
