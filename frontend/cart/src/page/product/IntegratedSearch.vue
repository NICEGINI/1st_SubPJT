<template>
  <div id="container">
    <hr />
    <h3 class="ma-3">
      <div v-if="productsNum == 0">
        '{{ $store.getters.getSearchName }}'에 대한 검색결과가 없습니다.
      </div>
      <div v-else>
        <strong
          >'{{ $store.getters.getSearchName }}'에 대한
          {{ this.products.length }}개의 검색결과</strong
        >
      </div>
    </h3>
    <hr />
    <div class="pt-3">
      <div style="text-align: center" v-for="(product, i) in products" :key="i">
        <img
          :src="
            require('@/assets/static/productImg/' + product.productNo + '.png')
          "
          alt="dd"
          width="134px"
          height="140px"
          style="border-radius: 40px 40px"
        />
        <div style="text-align: center; width: 100%" class="ml-30">
          <strong class="target2">{{ product.productName }}</strong>
        </div>
        <div>
          <strong style="color: blue">{{ product.productPrice }} 원</strong>
        </div>
        <v-row no-gutters class="my-2">
          <v-col style="text-align: right; padding-left: 6%">
            <v-btn text outlined @click="addShoppingList(i)"
              >쇼핑리스트 추가
            </v-btn>
          </v-col>
          <v-col style="text-align: left; padding-left: 3%" md-4 offset-md="4">
            <v-btn style="margin-left: 2%" text outlined @click="detail(i)"
              >상세 보기
            </v-btn>
          </v-col>
        </v-row>
        <hr style="margin: 2% 0px" />
      </div>
    </div>
  </div>
</template>

<script>
import http from '@/util/http-common.js';
export default {
  data() {
    return {
      products: [],
      productsNum: '',
    };
  },
  created() {
    this.products = this.$store.getters.getintegratedSearch;
    this.productsNum = this.products.length;
  },
  updated() {
    this.products = this.$store.getters.getintegratedSearch;
    this.productsNum = this.products.length;
  },
  methods: {
    addShoppingList(idx) {
      let formData = new FormData();
      formData.append('checked', false);
      formData.append('memberId', sessionStorage.getItem('userId'));
      formData.append('productName', this.products[idx].productName);
      formData.append(
        'productPrice',
        parseInt(this.products[idx].productPrice)
      );
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
    async detail(idx) {
      this.$store.commit('SET_LOADER_TRUE');
      let name = this.products[idx].productName;
      console.log('선택' + name);
      await http
        .get('/product/name', {
          params: {
            name: name,
          },
          withCredentials: true,
        })
        .then((response) => {
          console.log(response);
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
      console.log('디테일로 넘긴다');
      this.$router.push('/detailProduct');
    },
  },
};
</script>

<style scope>
.target {
  display: block;
  width: 100px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
</style>
