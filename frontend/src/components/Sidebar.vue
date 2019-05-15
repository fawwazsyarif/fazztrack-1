<template>
    <v-navigation-drawer
        app
        v-model="drawer"
        fixed
        temporary>

    <!-- employee's data -->
    <v-card height="120px" flat>
        <v-layout align-center justify-start row fill-height px-3>
            <v-flex shrink pr-2>
                <img :src ="imgUrl" >
            </v-flex>
            <v-flex shrink>
            <v-layout column align-start>
                <v-flex shrink>
                    {{getAuthName}}
                </v-flex>
                <v-flex>
                    <p color="primary lighten-1" class="caption secondary--text">
                        {{getAuthRole}}
                    </p>
                </v-flex>
            </v-layout>
            </v-flex>

        </v-layout>

    </v-card>
    <v-divider></v-divider>

    <!-- Kalo nge loop di vue harus pake key, kalo engga nanti muncul warning -->
    <!-- looping through navigationEmployee.js data -->
        <v-list>
            <v-list-tile v-if="getAuthRole==='Manager'" class="primary--text" :to="{ name: 'view-okr-recommendation', params: { id: getAuthUser.id }}" active-class="accent--text">
                <v-list-tile-action >
                    <feather type="thumbs-up" stroke-width=1></feather>
                </v-list-tile-action>
                <v-list-tile-title >My OKR recommendation</v-list-tile-title>
            </v-list-tile>

            <template v-for="(nav,i) in navigation" >
                
                <v-list-tile v-if="!nav.child" :to="nav.url" :key="i" exact class="primary--text" active-class="accent--text">
                    <v-list-tile-action>
                        <feather :type="nav.icon" stroke-width=1></feather>
                    </v-list-tile-action>
                    <v-list-tile-title >{{ nav.text }}</v-list-tile-title>
                </v-list-tile>


                <v-list-group v-else :key="i" class="primary--text" active-class="accent--text">
                    <v-list-tile slot="activator">
                        <v-list-tile-action>
                            <feather :type="nav.icon" stroke-width=1 ></feather>
                        </v-list-tile-action>
                        <v-list-tile-title >
                            {{ nav.text }}
                        </v-list-tile-title>
                    </v-list-tile>

                    <v-list-tile v-for="(child,j) in nav.child" :key="j" :to="child.url" active-class="accent--text">
                        <v-list-tile-action></v-list-tile-action>
                        <v-list-tile-title > {{ child.text }}</v-list-tile-title>
                    </v-list-tile>
                </v-list-group>
            </template>

            <v-list-tile class="primary--text" @click="logout" style="cursor: pointer">

                <v-list-tile-action >
                    <feather type="log-out" stroke-width=1></feather>
                </v-list-tile-action>
                <v-list-tile-title >Logout</v-list-tile-title>
            </v-list-tile>

            <!-- <v-list-tile v-if="getAuthRole==='Admin'" class="primary--text" @click="createSum" style="cursor: pointer"> -->

                <!-- <v-list-tile-action >
                    <feather type="log-out" stroke-width=1></feather>
                </v-list-tile-action> -->
                <!-- <v-list-tile-title >Create Summary</v-list-tile-title> -->
                <!-- <v-list-tile-title >{{dateSubmit[0].date}}</v-list-tile-title> -->
            <!-- </v-list-tile> -->
        </v-list>
    </v-navigation-drawer>
</template>
<script>
import navigationEmployee from './navigation/navigationEmployee';
import navigationManager from './navigation/navigationManager';
import navigationAdmin from './navigation/navigationAdmin';
import { mapGetters } from 'vuex';
import axios from 'axios';

export default {
    name : 'Sidebar',
    computed: {
        ...mapGetters([
            'getAuthUser',
            'getAuthRole',
            'getAuthName'
        ]),

        navigation() {
            if(this.getAuthRole === 'Employee') {
                return navigationEmployee
            } else if(this.getAuthRole === 'Manager') {
                return navigationManager
            }  else if(this.getAuthRole === 'Admin') {
                return navigationAdmin
            }
        },

        imgUrl() {
            return `https://ui-avatars.com/api/?background=48ACF0&rounded=true&size=36&color=ffffff&bold=true&name=${this.getAuthName}`
        },

    },
    data () {
        return {
            drawer: null,
            submitDate :[],
        }
    },

    methods: {
        changeState() {
           this.drawer = !this.drawer;
        },
        logout() {
            this.$store.dispatch('logout')
        },
    }

}
</script>

<style lang="scss" scoped>
</style>

