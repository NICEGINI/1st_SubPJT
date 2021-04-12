<template>
  <div id="container">
    <br />
    <h1>리뷰를 수정해주세요</h1>
    <br />
    <br />
    <div id="starArea">
      <star-rating
        style="margin-left: 42px"
        id="setstar"
        :star-size="40"
        v-model="rating"
        :border-width="5"
        border-color="#d8d8d8"
        :rounded-corners="true"
        :star-points="[
          23,
          2,
          14,
          17,
          0,
          19,
          10,
          34,
          7,
          50,
          23,
          43,
          38,
          50,
          36,
          34,
          46,
          19,
          31,
          17,
        ]"
      ></star-rating>
    </div>
    <div style="width: 90%; margin-left: 5%; margin-top: 8%">
      <v-text-field
        :label="setStarComment"
        outlined
        solo
        disabled
      ></v-text-field>
    </div>
    <br />
    <div style="max-width: 90%; margin-left: 5%; margin-top: 3%">
      <label>댓글</label>
      <v-textarea
        style="overflow: scroll"
        rows="3"
        row-height="30"
        shaped
        outlined
        :value="comment"
        v-model="comment"
      ></v-textarea>
    </div>
    <div>
      <v-row no-gutters>
        <v-col></v-col>
        <v-col>
          <v-btn text outlined @click="update">댓글수정</v-btn>
        </v-col>
        <v-col>
          <v-btn text outlined @click="cancel">취소하기</v-btn>
        </v-col>
        <v-col></v-col>
      </v-row>
    </div>
  </div>
</template>

<script>
import http from '@/util/http-common.js';
import StarRating from 'vue-star-rating';
export default {
  props: {
    review: Object,
  },
  created() {
    this.setStarComment;
  },
  components: {
    StarRating,
  },
  data() {
    return {
      rating: parseInt(this.review.score),
      comment: this.review.comment,
    };
  },
  computed: {
    setStarComment: function () {
      let score = this.rating;
      let starComment = '평가해주세요~';
      switch (score) {
        case 1:
          starComment = '최악이에요!! 다시는 안시킬거에요!!';
          break;
        case 2:
          starComment = '별로에요!! 추천 안해요!';
          break;
        case 3:
          starComment = '보통이에요!! 쓸만해요!';
          break;
        case 4:
          starComment = '좋아요!! 추천해요!!';
          break;
        case 5:
          starComment = '최고에요!! 또 주문할거에요!!!';
          break;
      }
      return starComment;
    },
  },
  methods: {
    update() {
      console.log('@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@');
      console.log(this.rating);
      console.log(typeof this.rating);
      console.log(this.comment.length);
      console.log(this.review.commentNo);
      console.log(this.$store.getters.getProductNo);

      if (this.comment.length != 0 && this.rating != 0) {
        let formData = new FormData();
        formData.append('comment', this.comment);
        formData.append('commentNo', this.review.commentNo);
        formData.append('memberId', sessionStorage.getItem('userId'));
        formData.append('memberName', sessionStorage.getItem('nickName'));
        formData.append('productNo', this.$store.getters.getProductNo);
        formData.append('score', this.rating.toString());
        http
          .put('/product/review', formData, {
            withCredentials: true,
          })
          .then((response) => {
            console.log(response);
            this.$attrs.updateComplete();
            this.$modal.hide('ReviewUpdate-modal');
          })
          .catch((error) => {
            console.log(error);
          });
      }
    },
    cancel() {
      this.$modal.hide('ReviewUpdate-modal');
    },
  },
};
</script>

<style scoped>
#container {
  text-align: center;
}
#starArea {
  text-align: center;
}
</style>
