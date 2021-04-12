<template>
  <div id="container">
    <div
      style="border-radius: 40px 40px; width: 33%; height: 130px; float: left"
      class="ma-3"
    >
      <img
        :src="require('@/assets/static/productImg/' + getProductNo + '.png')"
        alt="사진이 없습니다"
        width="134px"
        height="140px"
        style="border-radius: 40px 40px"
      />
      <v-btn @click="addShoppingList">쇼핑리스트 추가</v-btn>
    </div>
    <div
      style="width: 60%; height: 200px; border: 1px solid white; float: left"
      class="mt-3 pa-13"
    >
      <div>{{ getProductName }}</div>
      <div style="color: blue">{{ getProductPrice }}원</div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex';
import http from '@/util/http-common.js';
export default {
  computed: {
    ...mapGetters(['getProductName', 'getProductPrice', 'getProductNo']),
  },
  created() {
    console.log('여기왜 안바뀌지..?');
    console.log(this.$store.getters.getProductName);
  },
  methods: {
    addShoppingList() {
      let formData = new FormData();
      formData.append('checked', false);
      formData.append('memberId', sessionStorage.getItem('userId'));
      formData.append('productName', this.getProductName);
      formData.append('productPrice', this.getProductPrice);
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
  },
  data() {
    return {
      productName: this.$store.getters.getProductName,
      productPrice: this.$store.getters.getProductPrice,
    };
  },
};
</script>
