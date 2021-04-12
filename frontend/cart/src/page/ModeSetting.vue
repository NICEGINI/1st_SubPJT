<template>
  <div id="container">
    <h1>장보기 모드를<br />선택해주세요.</h1>
    <br />
    <br />
    <br />
    <br />
    <v-btn rounded color="#7986CB" dark large class="btn" @click="blindMode">
      <label class="mode">시각장애인 모드</label>
    </v-btn>
    <br />
    <br />
    <v-btn rounded color="#7CB342" dark large class="btn" @click="notblindMode">
      <label class="mode">비장애인 모드</label>
    </v-btn>
  </div>
</template>

<script>
import http from '@/util/http-common.js';
export default {
  methods: {
    blindMode() {
      // 시각장애인 모드
      // 랜덤 memberId 생성
      let max = 10;
      let arr = [
        '@',
        'a',
        'b',
        'c',
        'd',
        'e',
        'f',
        'g',
        'h',
        'i',
        'j',
        'k',
        'l',
        'm',
        'n',
        'o',
        'p',
        'q',
        'r',
        's',
        't',
        'u',
        'v',
        'w',
        'x',
        'y',
        'z',
        'A',
        'B',
        'C',
        'D',
        'E',
        'F',
        'G',
        'H',
        'I',
        'J',
        'K',
        'L',
        'M',
        'N',
        'O',
        'P',
        'Q',
        'R',
        'S',
        'T',
        'U',
        'V',
        'W',
        'X',
        'Y',
        'Z',
      ];
      let str = '';
      let length = arr.length;

      for (let i = 0; i < 5; i++) {
        str +=
          Math.floor(Math.random() * max) +
          arr[Math.floor(Math.random() * length)];
      }
      sessionStorage.setItem('userId', str);

      localStorage.setItem('isBlind', 1);
      sessionStorage.setItem('isLogin', false);
      this.$store.commit('TOGGLE_LOGIN_STATE');
      this.$router.push('/blindSearchProduct');
    },
    async notblindMode() {
      // // 비장애인 모드
      if (
        localStorage.getItem('userId') == '' ||
        localStorage.getItem('userId') == null
      ) {
        this.$router.push('/login');
      } else {
        localStorage.setItem('isBlind', 2);
        sessionStorage.setItem('isLogin', true);
        sessionStorage.setItem('userId', localStorage.getItem('userId'));
        sessionStorage.setItem('nickName', localStorage.getItem('nickName'));
        sessionStorage.setItem('userPw', localStorage.getItem('userPw'));
        sessionStorage.setItem(
          'loginPlatform',
          localStorage.getItem('loginPlatform')
        );

        let formData = {
          memberId: '',
          memberName: '',
          memberPassword: '',
        };

        formData.memberPassword = sessionStorage.getItem('userPw');
        formData.memberId = sessionStorage.getItem('userId');
        formData.memberName = sessionStorage.getItem('nickName');

        await http
          .post('/user/login/naver', formData, { withCredentials: true })
          .then((response) => {
            console.log(response);
          })
          .catch((error) => {
            console.log(error);
          });

        this.$store.commit('TOGGLE_LOGIN_STATE');
        this.$router.push('/shoppingList');
      }
      // this.$router.push('/login');
      // 임시 로그인 사용
    },
  },
};
</script>

<style scoped>
#container {
  margin: auto;
  margin-top: 50%;
}
.mode {
  font-size: 1.5rem;
  font: bolder;
}
.btn {
  width: 300px;
}
</style>
