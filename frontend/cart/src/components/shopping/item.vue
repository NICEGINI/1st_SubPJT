<template>
  <div class="itemWrap" id="container">
    <v-container>
      <v-row class="mb-6" no-gutters>
        <v-col cols="1" sm>
          <input
            type="checkbox"
            v-model="ischecked"
            :checked="ischecked"
            @click="check"
          />
        </v-col>
        <v-col
          cols="6"
          sm
          style="text-align: left; margin-right: 2%"
          @click="updateForm"
        >
          {{ productName }}
        </v-col>
        <v-col style="text-align: left" @click="updateForm" cols="3">
          ₩ {{ productPrice | comma }}
        </v-col>
        <v-col style="text-align: right; max-width: 10px">
          <v-btn text @click="del" style="max-width: 10px">
            <v-img
              src="@/assets/static/shoppingList/minus.png"
              alt="DEL"
              style="max-width: 20px; margin-bottom: 13px"
            ></v-img> </v-btn
        ></v-col>
        <v-col></v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import http from '@/util/http-common.js';
import UpdateForm from '@/components/shopping/updateForm.vue';
export default {
  name: 'item',
  props: {
    item: Object,
    allCheck: Boolean,
  },
  data() {
    return {
      ischecked: this.item.checked,
      productName: this.item.productName,
      productPrice: this.item.productPrice,
      shoppingListNo: this.item.shoppingListNo,
    };
  },
  created() {
    if (this.ischecked) {
      var payload = this.item;
      let money = parseInt(this.productPrice);
      this.$store.commit('ADD_TOTAL_MONEY', { money });
      this.$store.commit('ADD_CHECK_ITEM', { payload });
    }
    if (this.allCheck) {
      this.update();
    }
  },
  filters: {
    comma(val) {
      return val.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
    },
  },
  methods: {
    check() {
      let money = parseInt(this.productPrice);
      var payload = this.item;
      var no = this.item.shoppingListNo;
      if (!this.ischecked) {
        this.$store.commit('ADD_TOTAL_MONEY', { money });
        this.$store.commit('ADD_CHECK_ITEM', { payload });
      } else {
        this.$store.commit('DEL_ITEM', { money });
        this.$store.commit('DEL_CHECK_ITEM', { no });
      }
      this.update();
    },
    updateForm() {
      this.$modal.show(
        UpdateForm,
        {
          itemName: this.productName,
          itemPrice: this.productPrice,
          updated: this.updateNamePrice,
          updateForm: 'data',
          modal: this.$modal,
        },
        {
          name: 'UpdateForm-modal',

          width: '70%',
          height: '90%',
          draggable: false,
          shiftY: 0.3,
        },
        {
          closeByName() {
            this.$modal.hide('UpdateForm-modal');
          },
          closeByEvent() {
            this.$emit('close');
          },
        }
      );
    },
    updateNamePrice(updatedItem) {
      let formData = new FormData();
      formData.append('checked', this.ischecked);
      formData.append('memberId', sessionStorage.getItem('userId'));
      formData.append('productName', updatedItem.productName);
      formData.append('productPrice', updatedItem.productPrice);
      formData.append('shoppingListNo', this.shoppingListNo);

      http
        .put('/shoppingList', formData, {
          withCredentials: true,
        })
        .then((response) => {
          console.log(response);
          this.productName = updatedItem.productName;
          this.productPrice = updatedItem.productPrice;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    update() {
      let formData = new FormData();
      formData.append('checked', !this.ischecked);
      formData.append('memberId', sessionStorage.getItem('userId'));
      formData.append('productName', this.productName);
      formData.append('productPrice', this.productPrice);
      formData.append('shoppingListNo', this.shoppingListNo);

      http
        .put('/shoppingList', formData, {
          withCredentials: true,
        })
        .then((response) => {
          console.log(response);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    async del() {
      let money = parseInt(this.productPrice);
      let no = this.shoppingListNo;
      await http
        .delete('/shoppingList', {
          params: {
            shoppingListNo: parseInt(this.shoppingListNo),
          },
          withCredentials: true,
        })
        .then((response) => {
          if (this.ischecked) {
            this.$store.commit('DEL_ITEM', { money });
            this.$store.commit('DEL_CHECK_ITEM', { no });
          }
          console.log(response);
          this.$emit('del', this.shoppingListNo);
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
</script>

<style scope>
.itemWrap {
  margin: 0px 10%;
  margin-bottom: -10%;
  text-align: left;
}
</style>
