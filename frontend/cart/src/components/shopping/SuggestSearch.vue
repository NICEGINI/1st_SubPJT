<template>
    <div>
        <label @click="sel">{{ name }}</label>
        <hr>
    </div>
</template>

<script>
export default {
    props: {
        name: String,
        price: String,
    },
    methods: {
        sel() {
            let selectedProduct = {
                name: this.name,
                price: this.price,
            };
            this.$emit("sel", selectedProduct);
        },
    },
};
</script>

<style></style>
