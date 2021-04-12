<template>
     <v-container id="container" class="mx-auto my-auto">
          <div style="margin-top: 20%">
               <v-toolbar
                    class="nav"
                    style="height: 50px; margin: auto; text-align: center; width: 120px"
               >
                    <v-btn
                         text
                         @click="changeMode"
                         class="mode"
                         style="padding: 0; margin-left: 15%; margin-top: -3%"
                    >
                         모드변경
                         <i class="fas fa-blind"></i>
                    </v-btn>
               </v-toolbar>
          </div>
          <br />
          <br />
          <div class="upper">
               <div style="margin: auto">
                    <img src="@/assets/logo.png" alt="logo" style="width: 62%" />
               </div>
          </div>
          <div>
               <br />
               <br />
               <br />
               <br />
          </div>
          <div class="LinkAreaDiv">
               <div id="naverIdLogin" class="LinkArea"></div>
               <div class="LinkArea">
                    <v-btn text style="width: 278px">
                         <v-img
                              src="@/assets/kakao_login_large_narrow.png"
                              alt="KAKAO_IMAGE"
                              @click="kakaoLogin"
                              width="278px"
                              height="55px"
                         />
                    </v-btn>
               </div>
               <div style="margin-top: 10%">
                    <v-btn text style="width: 278px">
                         <v-img
                              src="@/assets/guestLogin.png"
                              alt="GUEST_IMAGE"
                              @click="guestLogin"
                              width="278px"
                              height="55px"
                         />
                    </v-btn>
               </div>
          </div>
     </v-container>
</template>
<script>
import http from "@/util/http-common.js";
export default {
     name: "login",
     components: {},
     created() {
          sessionStorage.setItem("isLogin", false);
          this.$store.commit("TOGGLE_LOGIN_STATE");
          if (localStorage.getItem("userId") != "" && localStorage.getItem("userId") != null) {
               this.$router.push("/shoppingList");
          }
     },
     data() {
          return {};
     },
     mounted() {
          const naverLogin = new window.naver.LoginWithNaverId({
               clientId: "9zJZugyWUILEG91zo8TK",
               callbackUrl: `${window.location.origin}/login_callback`,
               isPopup: true,
               loginButton: {
                    color: "green",
                    type: 3,
                    height: 60,
               },
          });
          naverLogin.init();
     },
     methods: {
          kakaoLogin() {
               window.Kakao.Auth.authorize({
                    redirectUri: `${window.location.origin}/kakaologin`,
               });
          },
          changeMode() {
               // 비쟁애인 - 시각장애인 모드 변경
               this.$router.push("/modesetting");
          },
          async guestLogin() {
               // 게스트 로그인
               console.log("게스트");
               let userId = "USER_";
               let cnt = 0;

               await http
                    .get("/user/size", {
                         withCredentials: true,
                    })
                    .then((response) => {
                         console.log("게스트 번호");
                         cnt = response.data.data;
                         console.log(cnt);
                         userId += cnt;

                         console.log(userId);

                         let formData = {
                              memberId: "",
                              memberPassword: "",
                              memberName: "",
                         };

                         formData.memberPassword = userId;
                         formData.memberId = userId;
                         formData.memberName = userId;

                         console.log("게스트로그인");
                         sessionStorage.setItem("isLogin", true);
                         sessionStorage.setItem("nickName", formData.memberName);
                         sessionStorage.setItem("userId", formData.memberId);
                         sessionStorage.setItem("userPw", formData.memberPassword);
                         sessionStorage.setItem("loginPlatform", "guest");

                         localStorage.setItem("isBlind", 2);
                         localStorage.setItem("nickName", formData.memberName);
                         localStorage.setItem("userId", formData.memberId);
                         localStorage.setItem("userPw", formData.memberPassword);
                         localStorage.setItem("loginPlatform", "guest");
                         this.$store.commit("TOGGLE_LOGIN_STATE");

                         http.post("/user/login/naver", formData, { withCredentials: true })
                              .then((response) => {
                                   console.log(response);
                                   this.$router.push("/shoppingList");
                              })
                              .catch((error) => {
                                   console.log("게스트 로그인 도중 에러");
                                   console.log(error);
                              });
                    })
                    .catch((error) => {
                         console.log("게스트 번호 받는 도중 에러");
                         console.log(error);
                    });
          },
     },
};
</script>
>

<style scoped>
/* .v-application--wrap{
        min-height: 90vh;   
    } */
#contents {
     margin-top: -200px;
}
.upper {
     margin-top: 10%;
}
.LinkAreaDiv {
     margin-top: 10%;
}
.LinkArea {
     margin: auto;
     margin: 5% 0;
}
</style>
