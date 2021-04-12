<template>
    <div id="container">
        <v-row no-gutters>
            <v-col cols="1" sm>
                <input type="checkbox" @click="check" v-model="checked" />
            </v-col>
            <v-col cols="6" sm style="text-align: left;"> 상품이름 </v-col>
            <v-col style="text-align: left"> 가격 </v-col>
        </v-row>
    </div>
</template>

<script>
export default {
    data(){
        return{
            checked: false,
        }
    },
    methods: {
        check() {
            this.$emit("check",!this.checked)
        },
    },
};
</script>

<style scoped>
#container {
    margin: 5px 3.5%;
}
</style>
