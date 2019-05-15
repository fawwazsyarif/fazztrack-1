
import Vue from 'vue'
import Vuetify from 'vuetify'
import Router from 'vue-router'
import login from '@/pages/login'
import myOKR from '@/pages/my-okr'
import tableOKR from '@/pages/table-okr'
import okrForApproval from '@/pages/okr-for-approval'
import detailOKR from '@/pages/detail-okr'
import sumComp from '@/pages/summary-company'
import sumDept from '@/pages/summary-department'
// import authentication from '@/store/modules/authentication.js'
import store from '@/store/index.js'
import addnewOKR from '@/pages/add-new-okr'
import updateKRprogress from '@/pages/update-key-result'
import departmentOKR from '@/pages/department-okr';
import usersList from '@/pages/user-list';
import userDetail from '@/pages/user-detail';
import feedbackList from '@/pages/feedback-list'
import feedbackForm from '@/pages/feedback-form'
import updateOKR from '@/pages/update-okr'
import changePhase from '@/pages/change-phase';
import selfReview from '@/pages/self-review';
import assignPeer from '@/pages/assign-peer';
import peerReviewList from '@/pages/peer-review-list';
import assignPeerList from '@/pages/assign-peer-list';
import peerReview from '@/pages/peer-review';
import managerReviewList from '@/pages/manager-review-list';
import managerReview from '@/pages/manager-review';
import reviewResult from '@/pages/review-result';
import selfReviewResult from '@/pages/self-review-result';
import managerReviewResult from '@/pages/manager-review-result';
import peerReviewResult from '@/pages/peer-review-result';
import viewOKRrecommendation from '@/pages/view-okr-recommendation';
import addOKRrecommendation from '@/pages/add-okr-recommendation';


Vue.use(Vuetify);
Vue.use(Router);


// function guard(to, from, next){
//   if(authentication.state.isAuthenticated) {
//     console.log(authentication.state.isAuthenticated)
//       // or however you store your logged in state
//       next(); // allow to enter route
//   } else{
//       next('/login'); // go to '/login';
//   }
// }

const routes = [
  {
    path: '/login',
    component: login,
    name: 'login',
    meta: { notRequiresAuth: true}
  },
  {
    path: '/',
    component: myOKR,
    // beforeEnter: guard,
    name: 'my-okr',
  },
  {
    path: '/okr/list',
    component: tableOKR,
    name: 'table-okr',
  },
  {
    path: '/okr/approval',
    component: okrForApproval,
    name: 'okr-for-approval',
    meta: {
      authorizedRoles: [ 'Manager', 'Admin' ]
    }
  },
  {
    path: '/detailOKR/:id',
    component: detailOKR,
    name: 'detail-okr',
  },
  {
    path: '/feedback',
    component: feedbackList,
    name: 'feedback-list',
  },
  {
    path: '/feedback/add',
    component: feedbackForm,
    name: 'feedback-form',

  },
  {
    path: '/sumComp',
    component: sumComp,
    name: 'summary-company',
    meta: {
      authorizedRoles: [ 'Admin' ]
    }
  },

  {
    path: '/sumDept',
    component: sumDept,
    name: 'summary-department',
    meta: {
      authorizedRoles: [ 'Manager', 'Admin' ]
    }

  },

  {
    path: '/add-new-OKR',
    component: addnewOKR,
    name: 'add-new-okr',
  },

  {
    path: '/update-key-result/:id',
    component: updateKRprogress,
    name: 'update-key-result',
  },
  { path: '/user',
    component: usersList,
    name: 'user-list',
  },

  { path: '/user/:id',
    component: userDetail,
    name: 'user-detail',
    props: true,
  },
  {
    path: '/update-okr/:id',
    component: updateOKR,
    name: 'update-okr'
  },
  { path: '/change-phase',
    component: changePhase,
    name: 'change-phase',
  },
  {
    path:'/self-review',
    component: selfReview,
    name: 'self-review'
  },
  { path: '/assign-peer',
    component: assignPeer,
    name: 'assign-peer',
  },
  {
    path: '/peer-review-list',
    component: peerReviewList,
    name: 'peer-review-list',
  },
  {
    path: '/assign-peer-list',
    component: assignPeerList,
    name: 'assign-peer-list',
  },
  {
    path:'/peer-review/:idReviewer/:idReviewee',
    component: peerReview,
    name: 'peer-review'
  },
  {
    path: '/manager-review-list',
    component: managerReviewList,
    name: 'manager-review-list',
  },
  {
    path:'/manager-review/:idReviewer/:idReviewee',
    component: managerReview,
    name: 'manager-review'
  },
  { path: '/get-result',
    component: reviewResult,
    name: 'review-result',
  },

  { path: '/get-result/self/:id',
    component: selfReviewResult,
    name: 'self-review-result',
  },

  { path: '/get-result/manager/:id',
    component: managerReviewResult,
    name: 'manager-review-result',
  },
  { path: '/get-result/peer/:id',
    component: peerReviewResult,
    name: 'peer-review-result',
  },
  {
    path: '/view-okr-recommendation/:id',
    component: viewOKRrecommendation,
    name: 'view-okr-recommendation'
  },
  {
    path: '/add-okr-recommendation',
    component: addOKRrecommendation,
    name: 'add-okr-recommendation'
  }
];


const appRouter = new Router({
  mode: 'history',
  base: '/',
  routes,
});

// const isAuthenticated = store.getters.isAuthenticated

appRouter.beforeEach((to, from, next) => {
  //kalo di komponennya ada meta not requiresAuth
  if (to.matched.some(record => !record.meta.notRequiresAuth)) { //yg butuh login
    if (!store().getters.isAuthenticated) {   //yg butuh login terus not authenticated
      next({ path: '/login' })
    } else { //kalo udah authenticated
      if (to.matched.some(record => record.meta.authorizedRoles)) {
        if(to.meta.authorizedRoles.indexOf(store().getters.getAuthRole) > -1) { //if role yang lagi login sekarang ada di meta authorizedRoles
          console.log('didi')
          next()
        } else {
          console.log('dada')
        next('/')
        }

      } else{ //kalo gaada authorizedroles di meta berarti boleh semua
        next()
      }
    }
  } else { // yg ga butuh login
    next()
  }
})




export default appRouter;
