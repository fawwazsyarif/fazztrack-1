<template>
    <div>
        <template v-if= " idStatus === 1 ">
            <v-chip color="primary lighten-1" small text-color="primary">{{ status }}</v-chip>
        </template>
        <template v-if= " idStatus === 2 ">
            <v-chip color="primary" small text-color="white">{{ status }}</v-chip>
        </template>
        <template v-if= " idStatus === 3 ">
            <v-chip color="success" small text-color="white">{{ status }}</v-chip>
        </template>
        <template v-if= " idStatus === 4 ">
            <v-chip color="error" small text-color="white">{{ status }}</v-chip>
        </template>
        <template v-if= " idStatus === 5 ">
            <v-chip color="accent" small text-color="white">{{ status }}</v-chip>
        </template>
        <template v-if= " idStatus === 6 ">
            <v-chip color="warning" small text-color="primary">{{ status }}</v-chip>
        </template>
    </div>
</template>
<script>
export default {
    name: 'Chip',
    props: {
        idStatus: {
            type: Number,
        },
        status: {
            type: String,
            default: '',
        }
    }
}
</script>