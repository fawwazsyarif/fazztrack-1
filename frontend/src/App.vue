<template>
  <v-app id="app">
    <template v-if="isAuthenticated">
      <Navbar @changeSidebarState="changeSidebarState"></Navbar>
    </template>
    <Sidebar ref="sidebar"></Sidebar>
    <v-content>
      <v-container fluid fill-height>
        <router-view/>
      </v-container>
    </v-content>
  </v-app>
</template>

<script>
import Sidebar from '@/components/Sidebar';
import Navbar from '@/components/Navbar';
import { mapGetters } from 'vuex';


export default {
  components: {
    Sidebar,
    Navbar,
  },

  methods: {
    changeSidebarState() {
      this.$refs.sidebar.changeState();
    }
  },
  computed: {
    ...mapGetters([
        'getAuthUser',
        'getAuthRole',
        'isAuthenticated',
    ]),
  }
};
</script>

<style scoped>
#app {
  background-color: #F4F5F9;
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", "Roboto", "Oxygen", "Ubuntu", "Cantarell", "Fira Sans", "Droid Sans", "Helvetica Neue", sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #4B4A56;
}
</style>
