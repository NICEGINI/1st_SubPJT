<template>
     <v-layout column> </v-layout>
</template>

<script>
import http from '@/util/http-common.js';
export default {
     async mounted() {
          const that = this;
          const naverLogin = new window.naver.LoginWithNaverId({
               clientId: '9zJZugyWUILEG91zo8TK',
               isPopup: false,
          });
          let formData = {
               memberId: '',
               memberPassword: '',
               memberName: '',
          };
          naverLogin.init();
          await naverLogin.getLoginStatus((status) => {
               if (status) {
                    console.log('로그인 들어옴');
                    formData.memberPassword = naverLogin.user.getId();
                    formData.memberId = naverLogin.user.getEmail();
                    formData.memberName = naverLogin.user.getNickName();

                    console.log('formdata');
                    console.log(formData);

                    http.post('/user/login/naver', formData, { withCredentials: true })
                         .then((response) => {
                              console.log(response);
                              //localStorage.setItem('naverlogin', true);
                              // opener.document.location.reload();
                              opener.sessionStorage.setItem('isLogin', true);
                              opener.localStorage.setItem('isBlind', 2);
                              opener.localStorage.setItem('nickName', formData.memberName);
                              opener.localStorage.setItem('userId', formData.memberId);
                              opener.localStorage.setItem('loginPlatform', 'naver');
                              // 진작 로컬로 할걸 그랬지
                              opener.sessionStorage.setItem('nickName', formData.memberName);
                              opener.sessionStorage.setItem('userId', formData.memberId);
                              opener.sessionStorage.setItem('userPw', formData.memberPassword);
                              opener.sessionStorage.setItem('loginPlatform', 'naver');
                              opener.document.location.replace(
                                   `${window.location.origin}/shoppingList`
                              );
                              window.close();
                              // this.$router.replace('/shoppingList');
                         })
                         .catch((error) => {
                              console.log(error);
                         });
               } else {
                    console.log('AccessToken이 올바르지 않습니다.');
                    alert('문제가 발생했습니다! 다시 로그인 해주세요!');
                    opener.document.location.replace(`${window.location.origin}/login`);
               }
          });
     },
};
</script>
