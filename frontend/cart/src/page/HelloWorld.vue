<template>
     <v-container @click="containerClick" id="container">
          <div>
               <hr />
               <p style="font-size: 30px">본 프로젝트는</p>
               <p style="font-size: 30px">
                    <strong style="color: #feca57">모바일</strong>에 최적화 되어있습니다
               </p>
               <v-btn text outlined width="80%" class="mb-2" color="#feca57" @click.stop="intro"
                    >안내</v-btn
               >
               <hr />
          </div>
          <br />
          <br />
          <div style="margin: auto">
               <img src="@/assets/logo.png" alt="logo" style="width: 62%" />
          </div>
          <div>
               <img src="../assets/cart.gif" style="width: 80%; height: 60%" />
          </div>
          <div>
               <h1>현명한 장보기의 시작</h1>
               <v-btn text color="#feca57"><p style="font-size: 20px">화면을 눌러주세요~</p></v-btn>
          </div>
     </v-container>
</template>

<script>
import http from "@/util/http-common.js";
import Swal from "sweetalert2";
export default {
     name: "HelloWorld",

     created() {
          sessionStorage.setItem("isLogin", false);
          this.$store.commit("TOGGLE_LOGIN_STATE");
     },
     data: () => ({
          mode: localStorage.getItem("isBlind"),
     }),
     methods: {
          intro() {
               console.log("안내 시작");
               Swal.fire({
                    title: "안내!",
                    text:
                         "본 프로젝트는 모바일 환경입니다. F12를 눌러 모바일 환경으로 바꾸어 주세요!",
                    icon: "success",
                    showCancelButton: false,
                    confirmButtonColor: "#3085d6",
                    confirmButtonText: "다음!",
               }).then((result) => {
                    if (result.isConfirmed) {
                         Swal.fire({
                              title: "전환!",
                              text:
                                   "F12로 개발자모드에 들어간 후 Ctrl + Shift + M 을 동시에 눌러  모바일로 전환이 가능합니다!",
                              icon: "success",
                              confirmButtonColor: "#3085d6",
                              confirmButtonText: "다음!",
                         }).then((result) => {
                              if (result.isConfirmed) {
                                   Swal.fire({
                                        title: "가장 좋은 것은!",
                                        text:
                                             "PC 환경에서는 카메라가 정상 작동 하지 않습니다! 스마트폰으로 들어가는 것을 권장합니다!",
                                        icon: "warning",
                                        confirmButtonColor: "#3085d6",
                                        confirmButtonText: "시작!",
                                   });
                              }
                         });
                    }
               });
          },
          async containerClick() {
               if (this.mode == null || this.mode == 0) {
                    // 처음실행 또는 로그아웃
                    this.$router.push("/modesetting");
               } else if (this.mode == 1) {
                    // 시각장애인
                    // 랜덤 memberId 생성
                    let max = 10;
                    let arr = [
                         "@",
                         "a",
                         "b",
                         "c",
                         "d",
                         "e",
                         "f",
                         "g",
                         "h",
                         "i",
                         "j",
                         "k",
                         "l",
                         "m",
                         "n",
                         "o",
                         "p",
                         "q",
                         "r",
                         "s",
                         "t",
                         "u",
                         "v",
                         "w",
                         "x",
                         "y",
                         "z",
                         "A",
                         "B",
                         "C",
                         "D",
                         "E",
                         "F",
                         "G",
                         "H",
                         "I",
                         "J",
                         "K",
                         "L",
                         "M",
                         "N",
                         "O",
                         "P",
                         "Q",
                         "R",
                         "S",
                         "T",
                         "U",
                         "V",
                         "W",
                         "X",
                         "Y",
                         "Z",
                    ];
                    let str = "";
                    let length = arr.length;

                    for (let i = 0; i < 5; i++) {
                         str +=
                              Math.floor(Math.random() * max) +
                              arr[Math.floor(Math.random() * length)];
                    }

                    sessionStorage.setItem("userId", str);
                    this.$router.push("/blindSearchProduct");
               } else {
                    // 비장애인
                    if (
                         localStorage.getItem("userId") == "" ||
                         localStorage.getItem("userId") == null
                    ) {
                         this.$router.push("/login");
                    } else {
                         sessionStorage.setItem("isLogin", true);
                         sessionStorage.setItem("userId", localStorage.getItem("userId"));
                         sessionStorage.setItem("nickName", localStorage.getItem("nickName"));
                         sessionStorage.setItem("userPw", localStorage.getItem("userPw"));
                         sessionStorage.setItem(
                              "loginPlatform",
                              localStorage.getItem("loginPlatform")
                         );

                         let formData = {
                              memberId: "",
                              memberName: "",
                              memberPassword: "",
                         };

                         formData.memberPassword = sessionStorage.getItem("userPw");
                         formData.memberId = sessionStorage.getItem("userId");
                         formData.memberName = sessionStorage.getItem("nickName");
                         await http
                              .post("/user/login/naver", formData, { withCredentials: true })
                              .then((response) => {
                                   console.log(response);
                              })
                              .catch((error) => {
                                   console.log(error);
                              });

                         this.$store.commit("TOGGLE_LOGIN_STATE");
                         this.$router.push("/shoppingList");
                    }
                    // 임시 로그인
                    // this.$store.commit("TOGGLE_LOGIN_STATE");
                    // this.$router.push("/shoppingList");
               }
          },
     },
};
</script>

<style scoped></style>
