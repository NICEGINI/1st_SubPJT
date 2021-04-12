<template>
  <div id="container">
    <h3 class="text-h7 mt-4 pr-1" style="border: 1px solid white">
      최저가 링크
    </h3>
    <div v-for="(item, i) in datas" :key="i">
      <hr />
      <div style="text-align: left" class="pl-4 my-1 target">
        <a target="_blank" :href="item.link"> {{ item.siteName }}</a> :
        {{ item.productName }}
        <div class="productPrice" style="text-align: right">
          {{ item.price }}원
          <v-btn
            text
            outlined
            @click="like(item)"
            style="margin-left: 7px; width: 3%"
            >_
            <vue-star
              style="margin-left: "
              animate="animated bounce"
              color="#F05654"
            >
              <i slot="icon" class="fas fa-shopping-basket"></i>
            </vue-star>
          </v-btn>
        </div>
      </div>
      <hr />
    </div>
    <v-row>
      <v-col>
        <v-btn text @click="writeReview">
          <v-img
            src="@/assets/static/reviewscore/review.png"
            max-width="20px"
            class="mb-1"
          ></v-img>
          리뷰쓰기
        </v-btn>
      </v-col>
      <v-col> <h3 class="text-h7 pr-3">상 품 평</h3></v-col>
      <v-col style="text-align: right; padding-right: 30px">
        <a target="_blank" :href="$store.getters.getReviewLink"
          >이마트몰리뷰</a
        ></v-col
      >
    </v-row>
    <hr />
    <div v-if="reviewNum == 0">
      <strong>상품평이 존재하지 않습니다.</strong>
    </div>
    <div v-else id="commentArea">
      <div v-for="(item, i) in reviews" :key="i">
        <div class="pl-4" style="text-align: left">
          <strong>{{ item.memberName }}</strong>
          <img
            :src="
              require('@/assets/static/reviewscore/' + item.score + 'star.png')
            "
            alt="dd"
            width="60px"
          />
        </div>
        <div class="pl-4" style="text-align: left">
          <v-row no-gutters>
            <v-col cols="8"> {{ item.comment }}</v-col>
            <v-row
              v-if="userId == reviews[i].memberId"
              no-gutters
              style="margin-top: -3px; margin-bottom: 10px; text-align: right"
            >
              <v-col style="margin-top: -15px">
                <v-btn text color="blue" @click="updateReview(i)">수정</v-btn>
              </v-col>
              <v-col style="margin-top: -15px">
                <v-btn text color="error" @click="delReivew(i)">삭제</v-btn>
              </v-col>
            </v-row>
          </v-row>
        </div>
        <hr />
      </div>
    </div>
  </div>
</template>

<script>
import WriteReview from '@/components/detail/WriteReview.vue';
import ReviewUpdate from '@/components/detail/ReviewUpdate.vue';
import http from '@/util/http-common.js';
import { mapGetters } from 'vuex';

export default {
  components: {
    WriteReview,
  },
  created() {
    console.log('created');
    console.log(this.$store.getters.getProductName);
    let name = this.$store.getters.getProductName;
    http
      .get('/product/detail', {
        params: {
          name,
        },
        withCredentials: true,
      })
      .then((response) => {
        // this.datas = resopnse.data.bestPrice;
        console.log(response);
        console.log(response.data.bestPrice.length);

        for (let i = 0; i < response.data.bestPrice.length; i++) {
          console.log(response.data.bestPrice[i]);
          this.datas.push(response.data.bestPrice[i]);
          console.log(this.datas[i].link);
          console.log(this.datas[i].price);
          console.log(this.datas[i].productName);
          console.log(this.datas[i].siteName);
        }
        this.reviewNum = response.data.review.length;
        for (let i = 0; i < response.data.review.length; i++) {
          this.reviews.push(response.data.review[i]);
          console.log(this.reviews[i].comment);
        }

        this.$store.commit('SET_LOADER_FALSE');
      })
      .catch((error) => {
        console.log(error);
        this.$store.commit('SET_LOADER_FALSE');
      });
  },

  data() {
    return {
      datas: [],
      reviews: [],
      reviewNum: '',
      reviewArea: document.getElementById('contents'),
      userId: sessionStorage.getItem('userId'),
    };
  },
  computed: {
    ...mapGetters(['getProductName', 'getProductPrice']),
  },
  updated() {
    // let name = this.$store.getters.getProductName;
  },
  methods: {
    like(item) {
      console.log(item);
      let formData = new FormData();
      formData.append('link', item.link);
      formData.append('memberId', sessionStorage.getItem('userId'));
      formData.append('price', item.price);
      formData.append('productName', item.productName);
      formData.append('siteName', item.siteName);
      http
        .post('/wishList', formData, {
          withCredentials: true,
        })
        .then((response) => {
          console.log(response);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    writeReview() {
      this.$modal.show(
        WriteReview,
        {
          add: this.add,
          reviewData: 'data',
          modal: this.$modal,
        },
        {
          name: 'WriteReview-modal',

          width: '85%',
          height: '75%',
          draggable: false,
          shiftY: 0.5,
        },
        {
          closeByName() {
            this.$modal.hide('WriteReview-modal');
          },
          closeByEvent() {
            this.$emit('close');
          },
        }
      );
    },
    async add() {
      console.log('리뷰작성성공');
      await http
        .get('/product/review', {
          params: {
            productNo: this.$store.getters.getProductNo,
          },
          withCredentials: true,
        })
        .then((response) => {
          this.reviews.splice(0);
          this.reviews = response.data;
          this.reviewNum = this.reviews.length;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    updateReview(idx) {
      this.$modal.show(
        ReviewUpdate,
        {
          updateComplete: this.updateComplete,
          ReviewUpdate: 'data',
          review: this.reviews[idx],
          modal: this.$modal,
        },
        {
          name: 'ReviewUpdate-modal',

          width: '85%',
          height: '75%',
          draggable: false,
          shiftY: 0.5,
        },
        {
          closeByName() {
            this.$modal.hide('ReviewUpdate-modal');
          },
          closeByEvent() {
            this.$emit('close');
          },
        }
      );
    },
    async updateComplete() {
      await http
        .get('/product/review', {
          params: {
            productNo: this.$store.getters.getProductNo,
          },
          withCredentials: true,
        })
        .then((response) => {
          this.reviews.splice(0);
          this.reviews = response.data;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    async delReivew(idx) {
      await http
        .delete('/product/review', {
          params: {
            commentNo: this.reviews[idx].commentNo,
          },
          withCredentials: true,
        })
        .then((response) => {
          console.log(response);
          http
            .get('/product/review', {
              params: {
                productNo: this.$store.getters.getProductNo,
              },
              withCredentials: true,
            })
            .then((response) => {
              this.reviews.splice(0);
              this.reviews = response.data;
              this.reviewNum = this.reviews.length;
            })
            .catch((error) => {
              console.log(error);
            });
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
</script>

<style scoped>
.target {
  display: block;
  width: 92%;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
.productPrice {
  color: blue;
}

hr {
  background-color: rgb(209, 202, 202);
  border: 1px;
  height: 1px;
}
</style>
