import axios from 'axios'
// import Router from 'vue-router'
// import router from './router'
import router from '@/router/router'
import { mapGetters } from 'vuex'


const state = {
    //nyimpen user yang lagi login
    authUser: {}
}

const getters = {
    isAuthenticated(state) {
        // return !!state.authUser
        return !!Object.keys(state.authUser).length

    },
    getAuthUser(state) {
        return state.authUser
    },
    getAuthName(state) {
        return state.authUser.name
    },
    getAuthRole(state) {
        return state.authUser.role
    },
    getAuthUserId(state) {
        return state.authUser.id
    },
    getAuthUserIdDepartment(state) {
        return state.authUser.idDepartment
    },
    getAuthUserDepartment(state) {
        return state.authUser.departmentName
    },
    getAuthUserManager(state){
        return state.authUser.idManager
    }

}

const actions = {
    //dispatch buat jalanin action, commit buat jalanin mutations, getters buat jalanin getter
    async login({ commit }, { username, password }) {
        try {
            const payload = {
                username: username,
                password: password
            }
            const response = await axios.post('/login', payload)
            // console.log(response)
            console.log("sini")
            commit('setAuthUser', response.data.result)
            router.push('/')
            console.log("hehe")
            console.log(this.isAuthenticated)

        } catch (error) {
            console.log(error)
            commit('setAuthUser', null)
        }
    },
    async logout({commit}) {
        router.push('/login')
        commit('setAuthUser', {})
        window.sessionStorage.removeItem('authentication');
        console.log(state.authUser)
    }

}

const mutations = {
    setAuthUser(state, data) {
        state.authUser = data
    },
}

export default {
    state,
    getters,
    actions,
    mutations,
}
