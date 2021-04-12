`
<template>
     <div id="container">
          <div style="text-align: left" class="pl-4 my-1 target ml-3 mr-3 mb-3">
               <a target="_blank" :href="like.link"> {{ like.siteName }}</a> :
               {{ like.productName }}
               <div class="productPrice" style="text-align: right">
                    {{ like.price }}원
                    <v-btn text outlined @click="del" style="margin-left: 7px; width: 3%"
                         >_
                         <vue-star style="z-index: 5" animate="animated bounce" color="#F05654">
                              <v-icon slot="icon">fas fa-trash-alt</v-icon>
                         </vue-star>
                    </v-btn>
               </div>
          </div>
     </div>
</template>

<script>
import http from '@/util/http-common.js';
export default {
     props: {
          like: Object,
          index: Number,
     },
     created() {
          console.log(this.index);
          console.log(this.like);
     },
     methods: {
          async del() {
               let wishNo = this.like.wishNo;
               await http
                    .delete('/wishList', {
                         params: {
                              wishNo: wishNo,
                         },
                         withCredentials: true,
                    })
                    .then((response) => {
                         console.log(response);
                         this.$emit('del');
                    })
                    .catch((error) => {
                         console.log(error);
                    });
          },
     },
};
</script>

<style scoped>
.productPrice {
     color: blue;
}
.target {
     text-overflow: ellipsis;
     overflow: hidden;
     display: block;
     width: 92%;
     white-space: nowrap;
}
</style>
`
