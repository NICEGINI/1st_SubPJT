<template>
  <div id="container" class="header">
    <v-toolbar class="nav" style="height: 45px">
      <v-toolbar-title
        class="grey--text lastObject"
        style="margin-left: -2%; margin-top: -3%"
      >
        <label style="color: #f9a825">SEE 장</label>
      </v-toolbar-title>
      <v-btn
        text
        @click="changeMode"
        class="mode"
        style="padding: 0; margin-left: 3%; margin-top: -3%"
      >
        모드변경
        <i class="fas fa-blind"></i>
      </v-btn>
      <v-btn text @click="likeproduct" style="margin: 0 5%; margin-top: -3%">
        {{ nickName }} 님
        <i class="fas fa-user-circle fa-lg"></i>
      </v-btn>
      <v-btn text @click="logout" style="padding: 0; margin-top: -3%">
        <span>로그아웃</span>
        <v-icon right>exit_to_app</v-icon>
      </v-btn>
    </v-toolbar>

    <v-text-field
      label="통합검색"
      placeholder="상품을 검색해주세요."
      outlined
      dense
      @keyup="searchState"
      @keyup.enter="goDetail"
      ref="searchBar"
      v-model="serachName"
      style="width: 80%; margin: 1% auto; margin-bottom: -6%"
      class="text"
    ></v-text-field>
    <div class="searchedArea" v-if="searchedState">
      <hr style="width: 100%; margin: auto" />
      <SuggestSearch
        v-for="product in searchedProducts"
        :name="product.productName"
        :key="product.productNo"
        @sel="sel"
      >
      </SuggestSearch>
      <hr style="width: 60%; margin: auto" />
    </div>
    <div class="gridDiv">
      <v-row no-gutters>
        <v-col @click="update('/shoppingList')">
          <img
            v-if="!isshopping"
            src="@/assets/shoppinglist_unclick.png"
            alt=""
            width="60px"
            class="mr-4"
          />
          <img
            v-else-if="isshopping"
            src="@/assets/shoppinglist_click3.png"
            alt=""
            width="60px"
            class="mr-4"
          />
        </v-col>

        <v-col @click="update('/popularproduct')"
          ><img
            v-if="!ispopular"
            src="@/assets/populer_unclick.png/"
            alt=""
            width="60px"
            class="ml-4 mr-4"
          />
          <img
            v-else-if="ispopular"
            src="@/assets/populer_click3.png/"
            alt=""
            width="60px"
            class="ml-4 mr-4"
          />
        </v-col>
        <v-col @click="update('/notBlindSearchProduct')"
          ><img
            v-if="!iscamera"
            src="@/assets/search_unclick.png/"
            alt=""
            width="60px"
            class="ml-4"
          />
          <img
            v-else-if="iscamera"
            src="@/assets/search_click3.png/"
            alt=""
            width="60px"
            class="ml-4"
          />
        </v-col>
      </v-row>
    </div>
    <div class="gridDiv mb-1" style="margin-top: -16px" :class="active">
      <v-row no-gutters>
        <v-col :class="{ active: isshopping }" @click="update('/shoppingList')">
          <span style="font-size: medium" class="mr-3">쇼핑리스트</span></v-col
        >
        <v-col @click="update('/popularproduct')" :class="{ active: ispopular }"
          ><span style="font-size: medium" class="ml-3 mr-3"
            >상품목록</span
          ></v-col
        >
        <v-col
          @click="update('/notBlindSearchProduct')"
          :class="{ active: iscamera }"
          ><span style="font-size: medium" class="ml-6 mr-3"
            >상품찾기</span
          ></v-col
        >
      </v-row>
    </div>
    <hr />
  </div>
</template>

<script>
import http from '@/util/http-common.js';
import SuggestSearch from '@/components/shopping/SuggestSearch.vue';
export default {
  name: 'Header',
  props: {},
  components: {
    SuggestSearch,
  },
  data() {
    return {
      serachName: '',
      platform: sessionStorage.getItem('loginPlatform'),
      nickName: sessionStorage.getItem('nickName'),
      active: '',
      isshopping: false,
      ispopular: false,
      iscamera: false,
      searchedProducts: [],
      searchedState: false,
    };
  },
  computed: {},
  created() {
    this.update(this.$router.currentRoute.path);
  },
  watch: {
    //해당 라우트에서 주소가 바꼈을시 호출됨
    /*
     * 헤더의 쇼핑리스트 / 이번주상품 / 카메라만 색이 켜졌다 꺼졌다 해야하므로
     * 3개의 정보만 update함수에 넣어서 true / false 조절
     * 근데 생각해보니까 이렇게 하면 2중 호출 아닌가 ?ㅁ?
     */
    $route(to, from) {
      console.log(to.path);
      if (
        to.path == '/shoppingList' ||
        to.path == '/popularproduct' ||
        to.path == '/notBlindSearchProduct'
      ) {
        this.update(to.path);
      } else if (to.path == '/' || to.path == '/modesetting') {
        console.log('route To');
        console.log(to.path);
        sessionStorage.setItem('isLogin', false);
        this.$store.commit('TOGGLE_LOGIN_STATE');
      } else {
        // 나머지는 모두 false로 색을 끄고 원래 가려던 길로 보낸다.
        this.isshopping = false;
        this.ispopular = false;
        this.iscamera = false;
      }
    },
  },
  methods: {
    update(path) {
      if (this.$router.currentRoute.path != this.active) {
        if (path == '/shoppingList') {
          this.isshopping = true;
          this.ispopular = false;
          this.iscamera = false;
          this.shopping_list();
        } else if (path == '/popularproduct') {
          this.isshopping = false;
          this.ispopular = true;
          this.iscamera = false;
          this.popular_product();
        } else if (path == '/notBlindSearchProduct') {
          this.isshopping = false;
          this.ispopular = false;
          this.iscamera = true;
          this.camera();
        }
      }
    },
    goDetail() {
      let searchName = this.serachName;
      this.$store.commit('SET_SERACH_NAME', { searchName });
      http
        .get('/product/name', {
          params: {
            name: this.serachName,
          },
          withCredentials: true,
        })
        .then((response) => {
          this.$store.commit('SET_INTEGRATED_SEARCH', { response }); //검색한 상품들의 정보를 셋팅
          this.searchedState = false;
          this.serachName = '';
          this.$refs.searchBar.focus();
        })
        .catch((error) => {
          console.log(error);
        });
      if (this.$router.currentRoute.path == '/integratedSearch') {
      } else {
        this.$router.push('/integratedSearch');
      }
    },
    sel(item) {
      this.serachName = item.name;
      this.goDetail();
    },
    camera() {
      if (this.$router.currentRoute.path != '/notBlindSearchProduct') {
        this.$router.push('/notBlindSearchProduct');
      }
    },
    shopping_list() {
      if (this.$router.currentRoute.path != '/shoppingList') {
        this.$router.push('/shoppingList');
      }
    },
    popular_product() {
      if (this.$router.currentRoute.path == '/popularproduct') {
        this.$store.commit('SET_CATEGORY_STATE', false);
      } else {
        this.$router.push('/popularproduct');
      }
    },
    changeMode() {
      // 비쟁애인 - 시각장애인 모드 변경
      localStorage.setItem('isBlind', 1);
      sessionStorage.setItem('isLogin', false);
      this.$store.commit('TOGGLE_LOGIN_STATE');
      this.$router.push('/modesetting');
    },
    logout() {
      // kakao 로그아웃
      if (this.platform == 'kakao') {
        // window.Kakao.Auth.logout(function () {
        //   console.log(window.Kakao.Auth.getAccessToken());
        // });
        // window.Kakao.API.request({
        //      url: '/v1/user/unlink',
        //      success: function (response) {
        //           console.log(response);
        //           console.log('kakao 로그아웃');
        //      },
        //      fail: function (error) {
        //           console.log(error);
        //      },
        // });
        console.log('kakao 로그아웃');
      } else if (this.platform == 'naver') {
        console.log('naver 로그아웃');
      } else if(this.paltform == 'guest'){
        console.log('게스트 로그아웃')
      }

      http
        .get('/user/logout', { withCredentials: true })
        .then((response) => {
          console.log("로그아웃 성공")
          console.log(response);
        })
        .catch((error) => {
          console.log("로그아웃 도중 에러")
          console.log(error);
        });

      sessionStorage.setItem('isLogin', false);
      sessionStorage.removeItem('userId');
      sessionStorage.removeItem('userPw');
      sessionStorage.removeItem('nickName');
      sessionStorage.removeItem('loginPlatform');

      localStorage.removeItem('userId');
      localStorage.removeItem('userPw');
      localStorage.removeItem('nickName');
      localStorage.removeItem('loginPlatform');
      localStorage.setItem('isBlind', 0);
      this.$store.commit('TOGGLE_LOGIN_STATE');
      this.$router.push('/');
    },
    likeproduct() {
      if (this.$router.currentRoute.path != '/likeproduct') {
        this.$router.push('/likeproduct');
      }
    },
    async searchState() {
      if (this.serachName != '') {
        await http
          .get('/product/name', {
            params: {
              name: this.serachName,
            },
            withCredentials: true,
          })
          .then((response) => {
            this.searchedProducts.splice(0);
            this.searchedState = true;
            this.searchedProducts = response.data;
          })
          .catch((error) => {
            console.log(error);
          });
      } else {
        this.searchedState = false;
        this.searchedProducts.splice(0);
      }
    },
  },
};
</script>

<style scoped>
.first {
  background-color: grey;
  height: 1.5px;
  border: 2px;
  width: 80%;
  margin: auto;
}
.navbar {
  width: 100%;
  background-color: #192a56;
  bottom: 0;
  position: fixed;
  box-shadow: 0.1px 0.1px 15px 0.1px #273c75;
  border-radius: 10px;
  display: flex;
}
#home {
  margin-left: 0;
  margin-top: 2px;
  color: #ffff;
  transition: width 0.5s, background-color 0.5s, border-radius 0.5s;
}
#check {
  margin-left: 0;
  margin-top: 2px;
  color: #ffff;
  transition: width 0.5s, background-color 0.5s, border-radius 0.5s;
}
#coin {
  margin-left: 0;
  margin-top: 2px;
  color: #ffff;
  transition: width 0.5s, background-color 0.5s, border-radius 0.5s;
}
#user {
  margin-left: 0;
  color: #ffff;
  transition: width 0.5s, background-color 0.5s, border-radius 0.5s;
}
.colum {
  display: flex;
  width: 95%;
  height: 40px;
  margin-top: 10px;
  margin-left: 10px;
}
.home {
  width: 80px;
  height: 34px;
  margin-left: 20px;
  display: flex;
}
.check {
  width: 80px;
  height: 34px;
  margin-left: 20px;
  display: flex;
}
.coin {
  width: 80px;
  height: 34px;
  margin-left: 20px;
  display: flex;
}
.leftObject {
  margin-right: 20%;
}
.clicked {
  width: 100px;
  height: 34px;
  background-color: #feca57;
  border-radius: 10px;
  transition: background-color 0.3s, border-radius 0.5s, width 0.5s;
}
.mode {
  margin-right: -12px;
}
.nav {
  margin-bottom: 10px;
}
.selected span.select {
  color: green;
}
.active {
  color: #ffab00;
  -webkit-text-stroke: 0.2px rgb(255, 145, 0);
}
.searchedArea {
  position: absolute;
  left: 0;
  right: 0;
  z-index: 10;
  background-color: white;
  max-height: 103px;
  overflow-y: scroll;
  text-align: left;
  width: 78%;
  margin: auto;
}
</style>
