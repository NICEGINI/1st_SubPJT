<template>
  <div id="container">
    <div style="margin-bottom: 2%; text-align: center">
      <strong>인기 {{ this.popular.ranking }}위!!</strong>
    </div>
    <v-row no-gutters class="px-5">
      <v-col class="product">
        <img
          :src="
            require('@/assets/static/productImg/' +
              this.popular.productNo +
              '.png')
          "
          alt="dd"
          width="134px"
          height="140px"
          style="border-radius: 40px 40px"
        />
      </v-col>
    </v-row>
    <v-row>
      <v-col>
        <v-row no-gutters>
          <v-col>
            <div class="pro_name" style="text-align: center">
              <strong>{{ this.popular.productName }}</strong>
            </div>
            <div style="text-align: center">
              <strong style="color: blue"
                >{{ this.popular.productPrice }} 원</strong
              >
            </div>
          </v-col>
        </v-row>
        <v-row no-gutters>
          <v-col style="text-align: right; padding-left: 1%">
            <v-btn text outlined @click="addShoppingList" style="width: 130px"
              >쇼핑리스트 추가
            </v-btn>
          </v-col>
          <v-col style="text-align: left; padding-left: 3%" md-4 offset-md="4">
            <v-btn
              style="margin-left: 2%; width: 130px"
              text
              outlined
              @click="detail"
              >상세 보기
            </v-btn>
          </v-col>
        </v-row>
      </v-col>
    </v-row>
    <hr style="margin: 2% 0px" />
  </div>
</template>

<script>
import VueStar from 'vue-star';
import http from '@/util/http-common.js';
export default {
  components: {
    VueStar,
  },
  props: {
    popular: Object,
  },
  methods: {
    addShoppingList() {
      console.log('멤버아이디:' + sessionStorage.getItem('userId'));
      let formData = new FormData();
      formData.append('checked', false);
      formData.append('memberId', sessionStorage.getItem('userId'));
      formData.append('productName', this.popular.productName);
      formData.append('productPrice', parseInt(this.popular.productPrice));
      http
        .post('/shoppingList', formData, {
          withCredentials: true,
        })
        .then((response) => {
          console.log(response);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    async detail() {
      this.$store.commit('SET_LOADER_TRUE');
      let name = this.popular.productName;
      await http
        .get('/product/name', {
          params: {
            name: name,
          },
          withCredentials: true,
        })
        .then((response) => {
          console.log('Asdfasdfasdfas1');
          let productInfo = {
            productName: this.popular.productName,
            productNo: this.popular.productNo,
            productPrice: this.popular.productPrice,
            reviewLink: response.data[0].reviewLink,
          };
          this.$store.commit('SET_PRODUCT_INFO', { productInfo });
          console.log('ddddddddasdfasdfdddddddd');
        })
        .catch((error) => {
          console.log(error);
        });

      this.$router.push('/detailProduct');
    },
  },
};
</script>

<style scoped>
.product {
  text-align: center;
}

.image {
  border-radius: 8px;
  width: 90%;
}

.pro_name {
  width: 100%;
  display: block;
  white-space: nowrap;
  margin-bottom: -2px;
  overflow: hidden;
  text-overflow: ellipsis;
}

p {
  text-align: left;
  padding-left: 8px;
  padding-right: 8px;
}
</style>
