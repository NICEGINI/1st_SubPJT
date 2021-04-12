<template>
  <div id="container">
    <div class="gridDiv">
      <v-row no-gutters>
        <v-col
          ><img v-if="!isFruit"
            src="@/assets/fruit_unclick.png/"
            alt=""
            width="50px"
            class="mr-3"
            :class="{active:isFruit }"
            @click="clickCategory('과일')"
        />
          <img v-else-if="isFruit"
            src="@/assets/fruit_click.png/"
            alt=""
            width="50px"
            class="mr-3"
            :class="{active:isFruit }"
            @click="clickCategory('과일')"
        />
        </v-col>
        <v-col
          ><img v-if="!isTea"
            src="@/assets/tea_unclick.png/"
            alt=""
            width="50px"
            class="ml-3 mr-3"
            :class="{active:isTea }"
            @click="clickCategory('커피/차')"
        />
          <img v-else-if="isTea"
            src="@/assets/tea_click.png/"
            alt=""
            width="50px"
            class="ml-3 mr-3"
            :class="{active:isTea }"
            @click="clickCategory('커피/차')"
        />
        </v-col>
        <v-col @click="clickCategory('냉장/냉동')" :class="{active:isRefri }"
          ><img v-if="!isRefri"
            src="@/assets/instant_unclick.png/"
            alt=""
            width="50px"
            class="ml-3 mr-3"
        />
        <img v-else-if="isRefri"
            src="@/assets/instant_click.png/"
            alt=""
            width="50px"
            class="ml-3 mr-3"
        />
        </v-col>
        <v-col
          ><img v-if="!isDrink"
            src="@/assets/drink_unclick.png/"
            alt=""
            width="50px"
            class="ml-3"
            :class="{active:isDrink }"
            @click="clickCategory('생수/음료')"
        />
          <img v-else-if="isDrink"
            src="@/assets/drink_click.png/"
            alt=""
            width="50px"
            class="ml-3"
            :class="{active:isDrink }"
            @click="clickCategory('생수/음료')"
        />
        </v-col>
      </v-row>
    </div>
    <div class="gridTextDiv">
      <v-row no-gutters>
        <v-col
          style="font-size: 95%"
          class="mr-2"
          :class="{active:isFruit }"
          @click="clickCategory('과일')"
          >과일</v-col
        >
        <v-col
          style="font-size: 95%"
          class="ml-2 mr-2"
          :class="{active:isTea }"
          @click="clickCategory('커피/차')"
          >커피/차</v-col
        >
        <v-col
          style="font-size: 95%"
          class="ml-2 mr-2"
          :class="{active:isRefri }"
          @click="clickCategory('냉장/냉동')"
          >냉장/냉동</v-col
        >
        <v-col
          style="font-size: 95%"
          class="ml-2"
          :class="{active:isDrink }"
          @click="clickCategory('생수/음료')"
          >생수/음료</v-col
        >
      </v-row>
    </div>
    <br />
    <div>
      <div class="gridDiv">
        <v-row no-gutters>
          <v-col @click="clickCategory('양념/오일')" :class="{active:isOil }"
            ><img v-if="!isOil" src="@/assets/oil_unclick.png/" alt="" width="50px" class="mr-3"
          />
            <img v-else-if="isOil" src="@/assets/oil_click.png/" alt="" width="50px" class="mr-3"
          />
          </v-col>
          <v-col @click="clickCategory('우유/유제품')" :class="{active:isMilk }"
            ><img v-if="!isMilk"
              src="@/assets/milk_unclick.png/"
              alt=""
              width="50px"
              class="ml-3 mr-3"
          />
            <img v-else-if="isMilk"
              src="@/assets/milk_click.png/"
              alt=""
              width="50px"
              class="ml-3 mr-3"
          />
          </v-col>
          <v-col @click="clickCategory('베이커리/잼')" :class="{active:isBread }"
            ><img v-if="!isBread"
              src="@/assets/bread_unclick.png/"
              alt=""
              width="50px"
              class="ml-3 mr-3"
          />
            <img v-else-if="isBread"
              src="@/assets/bread_click.png/"
              alt=""
              width="50px"
              class="ml-3 mr-3"
          />
          </v-col>
          <v-col @click="clickCategory('쌀/잡곡')" :class="{active:isRice }"
            ><img v-if="!isRice" src="@/assets/rice_unclick.png/" alt="" width="50px" class="ml-3"
          />
            <img v-else-if="isRice" src="@/assets/rice_click.png/" alt="" width="50px" class="ml-3"
          />
          </v-col>
        </v-row>
      </div>
      <div class="gridTextDiv">
        <v-row no-gutters>
          <v-col
            ><span
              style="font-size: 95%"
              class="mr-3"
              :class="{active:isOil }"
              @click="clickCategory('양념/오일')"
              >양념/오일</span
            ></v-col
          >
          <v-col
            ><span
              style="font-size: 95%"
              class="mr-0.5"
              :class="{active:isMilk }"
              @click="clickCategory('우유/유제품')"
              >우유/</span
            ></v-col
          >
          <v-col
            ><span
              style="font-size: 95%"
              class="ml-0.5"
              :class="{active:isBread }"
              @click="clickCategory('베이커리/잼')"
              >베이커리/</span
            ></v-col
          >
          <v-col
            ><span
              style="font-size: 95%"
              class="ml-3"
              :class="{active:isRice }"
              @click="clickCategory('쌀/잡곡')"
              >쌀/잡곡</span
            ></v-col
          >
        </v-row>
      </div>
      <div class="gridDiv2">
        <v-row no-gutters>
          <v-col><span style="font-size: small" class="mr-2"></span></v-col>
          <v-col
            ><span
              style="font-size: 95%"
              class="ml-4"
              :class="{active:isMilk }"
              @click="clickCategory('우유/유제품')"
              >유제품</span
            ></v-col
          >
          <v-col
            ><span
              style="font-size: 95%"
              class="mr-6"
              :class="{active:isBread }"
              @click="clickCategory('베이커리/잼')"
              >잼</span
            ></v-col
          >
          <v-col><span style="font-size: small" class="ml-2"></span></v-col>
        </v-row>
      </div>
    </div>
    <br />
    <hr class="first" />
  </div>
</template>

<script>
import http from '@/util/http-common.js';
export default {
  data() {
    return {
      isFruit:false,
      isTea:false,
      isRefri:false,
      isDrink:false,
      isOil:false,
      isMilk:false,
      isBread:false,
      isRice:false,
    };
  },
  methods: {
    clickCategory(categorizeItem) {
      // 배 열 초기화 -arr = [ isFruit, isTea, isRefri ... ]
      // 모두 false로 
      // for(let i = 0; i < arr.length; i++){
      //   arr[i] = false;
      this.isFruit = false;
      this.isTea = false;
      this.isRefri = false;
      this.isDrink = false;
      this.isOil = false;
      this.isMilk = false;
      this.isBread = false;
      this.isRice = false;

      // String의 문자랑 맞는거만 true로
      switch(categorizeItem){
        case "과일" : 
          this.isFruit = true;
          break;
        case "커피/차" : 
          this.isTea = true;
          break;
        case "냉장/냉동" : 
          this.isRefri = true;
          break;
        case "생수/음료" : 
          this.isDrink = true;
          break;
        case "양념/오일" : 
          this.isOil = true;
          break;
        case "우유/유제품" : 
          this.isMilk = true;
          break;
        case "베이커리/잼" : 
          this.isBread = true;
          break;
        case "쌀/잡곡" : 
          this.isRice = true;
          break;
      }
      http
        .get('/product/category', {
          params: {
            category: categorizeItem,
          },
          withCredentials: true,
        })
        .then((response) => {
          let categorizeItems = response.data;
          this.$store.commit('SET_CATEGORIZE_ITEM', { categorizeItem });
          this.$store.commit('SET_CATEGORIZE_ITEMS', { categorizeItems });
          this.$store.commit('SET_CATEGORY_STATE', true);
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
</script>

<style>
.gridDiv {
  margin: 1% 10%;
}
.gridTextDiv {
  margin: 1% 10%;
  margin-top: -2%;
}
.gridDiv2 {
  text-align: center;
  margin: -3% 0;
}
.active {
  color: #FFAB00;
  -webkit-text-stroke: 0.2px rgb(255, 115, 0);
}

</style>
