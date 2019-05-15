const state = {
    isSidebarOpen: true,
}

const getters = {
    getSidebarState(state) {
        return state.isSidebarOpen;
    }
}

const actions = { // kalo mau manggil mutation, harus pake commit . kalo mau manggil actions, harus pake dispatch
    changeSidebarState({commit, state}) {
        console.log(state.isSidebarOpen)
        commit('setSidebarState', !state.isSidebarOpen);
    }
}

const mutations = {
    setSidebarState(state, data) {
        state.isSidebarOpen = data;
    }
} // setter

export default {
    state,
    getters,
    actions,
    mutations,
}