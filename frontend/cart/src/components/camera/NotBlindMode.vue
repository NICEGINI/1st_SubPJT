<template>
  <div>
    <v-btn class="btn" rounded color="primary" @click="toggle"
      >최저가 확인하기</v-btn
    >
  </div>
</template>

<script>
export default {
  methods: {
    toggle() {
      this.$store.commit('SET_LOADER_TRUE');
      let mode = '1';
      console.log('toggle함수 들어옴');
      // console.log('item:' + item);
      console.log('카메라 사진찍고, 카메라모드, 아이템 insert');
      this.$store.commit('TOGGLE_CAMERA_CANVAS'); //카메라 사진찍기
      this.$store.commit('SET_CAMERA_MODE', { mode }); //카메라 모드 넣어주고
      // this.$store.commit('SET_CAMERA_ITEM', { item }); //아이템 넣어주고
    },
  },
};
</script>

<style scoped>
.btn {
  width: 300px;
  min-height: 80px;
  font-size: large;
}
</style>
