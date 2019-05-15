import Vue from 'vue'
import Vuex from 'vuex';
import VuexPersistance from 'vuex-persist'

import ui from './modules/ui'
import authentication from './modules/authentication'

Vue.use(Vuex);

const persistedModules = [
    'authentication',
]

const vuexPersist = new VuexPersistance({
    key: 'payfazz',
    //buat nyimpen usernya terus
    storage: window.sessionStorage,
    // reducer: (state) => ({ authUser: state.authUser}),
    modules: persistedModules
})

const store = () => new Vuex.Store({
    namespaced: true,

    plugins: [
        vuexPersist.plugin,
    ],

    modules: {
        ui,
        authentication,
    }
})

// const express = require("express");
// const multer = require("multer");
// const sharp = require("sharp");
// const fs =require("fs");
// const path = require("path");
// const aws = require("aws-sdk");

// aws.config.update({
//   accessKeyId : "",
//   secretAccessKey : ""


// });

// const app = express()
// app.use("/static",express.static(path.join(__dirname,"static")));

// const fileFilter = function(req, file,cb){
//   const allowedTypes = ["file/pdf","file/docx", "file/doc", "file/"]
// }
// const MAX_SIZE = 10000000;
// const upload = multer({
//   dest : "./uploads/", fileFilter
// })

export default store;
