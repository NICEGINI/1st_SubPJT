<template>
     <v-layout column> </v-layout>
</template>

<script>
import axios from 'axios';
import http from '@/util/http-common.js';
export default {
     async mounted() {
          const that = this;
          const qs = require('qs');
          const parameter = {
               grant_type: 'authorization_code',
               client_id: 'b3bdce092bdd7e37d7b394bb8c363098',
               code: this.$route.query.code,
          };
          await axios
               .post('https://kauth.kakao.com/oauth/token', qs.stringify(parameter), {
                    headers: {
                         'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8',
                    },
               })
               .then((res) => {
                    let formData = {
                         memberId: '',
                         memberName: '',
                         memberPassword: '',
                    };

                    window.Kakao.Auth.setAccessToken(res.data.access_token);
                    window.Kakao.API.request({
                         url: '/v2/user/me',
                         data: {
                              property_keys: ['properties.nickname', 'kakao_account.email'],
                         },
                         success: (response) => {
                              formData.memberPassword = response.id;
                              formData.memberId = response.kakao_account.email;
                              formData.memberName = response.properties.nickname;

                              console.log('kakaoCallback');
                              sessionStorage.setItem('isLogin', true);
                              sessionStorage.setItem('nickName', formData.memberName);
                              sessionStorage.setItem('userId', formData.memberId);
                              sessionStorage.setItem('userPw', formData.memberPassword);
                              sessionStorage.setItem('loginPlatform', 'kakao');

                              localStorage.setItem('isBlind', 2);
                              localStorage.setItem('nickName', formData.memberName);
                              localStorage.setItem('userId', formData.memberId);
                              localStorage.setItem('userPw', formData.memberPassword);
                              localStorage.setItem('loginPlatform', 'kakao');
                              this.$store.commit('TOGGLE_LOGIN_STATE');

                              http.post('/user/login/naver', formData, { withCredentials: true })
                                   .then((response) => {
                                        console.log(response);
                                        this.$router.push('/shoppingList');
                                   })
                                   .catch((error) => {
                                        console.log(error);
                                   });
                         },
                         fail: function (error) {
                              console.log(error);
                              alert('문제가 발생했습니다! 다시 로그인 해주세요!');
                              this.$router.push('/login');
                         },
                    });
               })
               .catch(() => {
                    console.log('error_pre');
               });
     },
};
</script>
