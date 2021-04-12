<template>
  <div id="container">
    <v-row no-gutters class="px-5">
      <v-col class="product">
        <img
          :src="
            require('@/assets/static/productImg/' +
              this.categoryItem.productNo +
              '.png')
          "
          alt="dd"
          width="134px"
          height="140px"
          style="border-radius: 40px 40px"
        />
      </v-col>
      <v-col>
        <v-row>
          <br />
        </v-row>
        <v-row no-gutters>
          <v-col>
            <div class="pro_name" style="text-align: center">
              <strong>{{ this.categoryItem.productName }}</strong>
            </div>
            <div style="text-align: center">
              <strong>{{ this.categoryItem.productPrice }} 원</strong>
            </div>
          </v-col>
        </v-row>
        <v-row no-gutters>
          <v-col style="text-align: center; padding-left: 6%">
            <v-btn text outlined style="width: 130px" @click="addShoppingList"
              >쇼핑리스트 추가
            </v-btn>
          </v-col>
          <v-col
            style="text-align: center; padding-left: 3%"
            md-4
            offset-md="4"
            class="mt-2"
          >
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
import http from '@/util/http-common.js';
export default {
  name: 'categoryinfo',
  props: {
    categoryItem: Object,
  },
  data() {
    return {};
  },
  methods: {
    addShoppingList() {
      let formData = new FormData();
      formData.append('checked', false);
      formData.append('memberId', sessionStorage.getItem('userId'));
      formData.append('productName', this.categoryItem.productName);
      formData.append('productPrice', this.categoryItem.productPrice);
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
      let name = this.categoryItem.productName;

      await http
        .get('/product/name', {
          params: {
            name,
          },
          withCredentials: true,
        })
        .then((response) => {
          let productInfo = {
            productName: response.data[0].productName,
            productNo: response.data[0].productNo,
            productPrice: response.data[0].productPrice,
            reviewLink: response.data[0].reviewLink,
          };
          this.$store.commit('SET_PRODUCT_INFO', { productInfo });
        })
        .catch((error) => {
          console.log(error);
        });

      this.$router.push('/detailProduct');
    },
  },
  data() {
    return {
      //productName: this.$store.getters.getProductName,
      //   productPrice: this.$store.getters.getProductPrice,
    };
  },
};
</script>
