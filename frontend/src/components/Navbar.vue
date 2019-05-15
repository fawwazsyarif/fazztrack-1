<template>
  <v-toolbar app fluid flat fixed class="bg">
    <v-layout align-content-center align-center>
      <v-toolbar-items>
        <feather type="align-left" stroke-width=1 @click="$emit('changeSidebarState')" style="cursor: pointer"></feather>
      </v-toolbar-items>
      <v-toolbar-items>
        <img src="@/assets/logo.png" alt="" class="company-logo">
      </v-toolbar-items>
      <v-spacer></v-spacer>
      <v-toolbar-items class="hidden-sm-and-down">
        <!-- <img src= "https://ui-avatars.com/api/?background=333333&rounded=true&size=40" > -->
        <img :src ="imgUrl" class="pr-2">
        <!-- <v-btn flat color="primary"></v-btn> -->
        <h2 class="primary--text font-weight-regular">
          {{getAuthUser.username}}
        </h2>
      </v-toolbar-items>
    </v-layout>
  </v-toolbar>
</template>
<script>
import { mapGetters } from 'vuex'

export default {
    name: 'Navbar',

    // props: ['changeSidebarState'],
    computed: {
      ...mapGetters([
          'getAuthUser',
          'getAuthRole',
          'getAuthName',
          
      ]),
      imgUrl() {
        return `https://ui-avatars.com/api/?background=48ACF0&rounded=true&size=34&color=ffffff&bold=true&name=${this.getAuthName}`
      }
    },


}
</script>

<style lang="scss" scoped>
    .bg {
        background-color: white;
       border: 1px solid #DBE0E4;
       box-shadow: 0px 10px 10px rgba(0, 0, 0, 0.05);

    }
    .company-logo {
      padding-top: 20px;
    }
</style>
