<template>
    <v-content>
      <v-container fluid="true" grid-list-xl text-xs-center class="mt-1 pt-5">
        <v-layout row wrap>
          <v-flex xs6 offset-xs3>
            <v-card color="white" class="ma-5 pa-1 card">
                <img src="@/assets/logo.png" alt="" class="company-logo px-4 align-center" >
                <h1 class="primary--text">Log in</h1>
                <p class="primary--text">Welcome back, please login to your account</p>
              <v-form ref="form" v-model="valid" lazy-validation @submit.prevent="login" class="ma-4 pa-2">
                <v-text-field outline v-model="username" name="username" :rules="[rules.required]" placeholder="" label="Username" required></v-text-field>
                <v-text-field
                    outline
                    v-model="password"
                    name="password" :rules="[rules.required]"
                    placeholder="" label="Password"
                    required
                    :append-icon="show ? 'visibility' : 'visibility_off'" :type="show ? 'text' : 'password'"
                    class="input-group--focused"
                    @click:append="show = !show">
                </v-text-field>
                <template v-if="isWrongPassword">
                    <p class="error--text">Username or password is incorrect</p>
                </template>
                <v-btn type="submit" color="info" large block class="mt-2" @click.native="login"> Login</v-btn>
              </v-form>
            </v-card>
          </v-flex>
        </v-layout>
      </v-container>
    </v-content>

</template>

<script>
import { mapGetters } from 'vuex';

export default {
    name : 'login',

    data() {
        return {
            username: '',
            password: '',
            valid: true,
            show: false,
            rules: {
                required: value => !!value || "Field required",
                min: value => value.length >= 4 || "Minimum 4 characters"
            }
        }
    },

    computed: {
        ...mapGetters([
            'getAuthUser',
            'isWrongPassword'
        ])
    },

    methods: {
        login() {
            this.$store.dispatch('login', { username: this.username, password: this.password })
        }
    }
}

</script>
<style>
    .login-form {
        max-width: 200px
    }
    .form {
        border: 1px solid rgba(0,0,0,.10);
    }
    .theme--light.v-text-field--outline>.v-input__control>.v-input__slot {
    border: 1px solid rgba(0,0,0,.20);
    }
    .card {
        box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
    }
</style>


