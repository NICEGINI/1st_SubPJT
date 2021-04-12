<template>
  <div id="container">
    <br />
    <br />
    <h1>수정 해주세요!!</h1>
    <br />
    <br />
    <div>
      <v-text-field
        style="text-align: center"
        class="update centered-input"
        :value="productName"
        v-model="productName"
      />
    </div>
    <br />
    <div>
      <v-text-field
        style="text-align: center"
        class="update centered-input"
        type="number"
        :value="productPrice"
        v-model="productPrice"
      />
    </div>
    <br />
    <div>
      <v-btn text outlined style="margin: 2%" @click="update">수정하기</v-btn>
      <v-btn text outlined @click="cancel">취소하기</v-btn>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    updateForm: String,
    itemName: String,
    itemPrice: String,
  },
  data() {
    return {
      productName: this.itemName,
      productPrice: this.itemPrice,
    };
  },
  methods: {
    update() {
      var updated = {
        productName: this.productName,
        productPrice: this.productPrice,
      };
      this.$attrs.updated(updated);
      this.$modal.hide('UpdateForm-modal');
    },
    cancel() {
      this.$modal.hide('UpdateForm-modal');
    },
  },
};
</script>

<style scoped>
#container {
  text-align: center;
}
.update {
  text-align: center;
  font-size: 1.2em;
  padding: 0px 20%;
}
.centered-input >>> input {
  text-align: center;
}
</style>
