<template>
  <div id="container">
    <br />
    <br />
    <h1>추가할 물품을</h1>
    <h1>작성해주세요</h1>
    <br />
    <br />
    <div class="textArea">
      <v-text-field
        v-model="productName"
        class="addform centered-input"
        placeholder="물품이름"
        @keyup="search"
      />
      <div class="searchedArea" v-if="searchedState">
        <label @click="selSelf">{{ productName }}</label>
        <hr style="width: 100%; margin: auto" />
        <SuggestSearch
          v-for="product in searchedProducts"
          :name="product.productName"
          :price="product.productPrice"
          :key="product.productNo"
          @sel="sel"
        >
        </SuggestSearch>
        <hr style="width: 60%; margin: auto" />
      </div>
      <v-text-field
        v-model="productPrice"
        type="number"
        class="addform centered-input"
        placeholder="가격"
      />
    </div>
    <br />
    <br />
    <br />
    <div>
      <v-btn
        text
        outlined
        style="margin: 2% 1%; width: 59%"
        @click="add"
        @add="add"
        >쇼핑리스트 추가하기</v-btn
      >
    </div>
    <v-btn
      text
      outlined
      @click="favorite"
      class="mt-3"
      style="margin-left: 3px; width: 59%"
      >즐겨찾기 추가하기</v-btn
    >
    <v-card class="footer">
      <v-footer
        style="background-color: black; height: 70px"
        absolute
        class="font-weight-medium"
      >
        <v-col @click="cancel" class="text-center" cols="12">
          <h2 style="color: white">닫 기</h2>
        </v-col>
      </v-footer>
    </v-card>
  </div>
</template>

<script>
import http from '@/util/http-common.js';
import SuggestSearch from '@/components/shopping/SuggestSearch.vue';
export default {
  props: ['addData'],
  components: {
    SuggestSearch,
  },
  data() {
    return {
      productName: '',
      productPrice: '',
      searchedProducts: [],
      searchedState: false,
    };
  },
  methods: {
    add() {
      if (this.productName.length != 0 && this.productPrice.length != 0) {
        let formData = new FormData();
        formData.append('checked', false);
        formData.append('memberId', sessionStorage.getItem('userId'));
        formData.append('productName', this.productName);
        formData.append('productPrice', this.productPrice);
        http
          .post('/shoppingList', formData, {
            withCredentials: true,
          })
          .then((response) => {
            console.log(response);
            let item = {
              checked: false,
              memberId: sessionStorage.getItem('userId'),
              productName: this.productName,
              productPrice: this.productPrice,
              shoppingListNo: response.data, // 백엔드에서 받아온 No 값으로 변경
            };
            this.$attrs.add(item);
            this.$modal.hide('AddShoppingList-modal');
          })
          .catch((error) => {
            console.log(error);
          });
      }
    },
    cancel() {
      this.$modal.hide('AddShoppingList-modal');
    },
    search() {
      if (this.productName != '') {
        http
          .get('/product/name', {
            params: {
              name: this.productName,
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
    selSelf() {
      this.searchedState = false;
    },
    sel(selecteditem) {
      console.log(selecteditem);
      for (let i = 0; i < this.searchedProducts.length; i++) {
        if (this.searchedProducts[i].productName == selecteditem.name) {
          this.productName = this.searchedProducts[i].productName;
          this.productPrice = this.searchedProducts[i].productPrice;
          break;
        }
      }
      this.searchedState = false;
    },
    favorite() {
      if (this.productName.length != 0 && this.productPrice.length != 0) {
        let formData = new FormData();
        formData.append('memberId', sessionStorage.getItem('userId'));
        formData.append('productName', this.productName);
        formData.append('productPrice', this.productPrice);
        http
          .post('/myProduct', formData, {
            withCredentials: true,
          })
          .then((response) => {
            console.log(response);
          })
          .catch((error) => {
            console.log(error);
          });
      }
    },
  },
};
</script>

<style scoped>
#container {
  text-align: center;
}
.textArea {
  text-align: center;
}
.addform {
  font-size: 1.2em;
  padding: 0px 20%;
}
.centered-input >>> input {
  text-align: center;
}
.searchedArea {
  position: absolute;
  left: 0;
  right: 0;
  z-index: 10;
  background-color: white;
  max-height: 150px;
  overflow-y: scroll;
  text-align: left;
  width: 60%;
  margin: auto;
}
.footer {
  width: 100%;
  position: absolute;
  bottom: 0%;
  background-color: white;
}
</style>
