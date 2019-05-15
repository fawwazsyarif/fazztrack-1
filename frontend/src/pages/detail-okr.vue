<template>
  <v-container fluid grid-list-lg>
    <template v-if="isLoading">
      <p>loading ...</p>
    </template>
    <template v-else>
      <Modal
        ref="modal"
        :title="modalTitle"
        :text="modalText"
        :modalType="modalType"
        greenButtonText="Approve"
        redButtonText="Reject"
        @onModalGreenSubmit="onModalGreenSubmit"
        @onModalRedSubmit="onModalRedSubmit">
        >
      </Modal>
      <template v-if="(getAuthRole === 'Manager' || getAuthRole === 'Admin') && displayChangeStatus && (okrDetail.approvalStatus.id === 1 || okrDetail.approvalStatus.id === 2) " >
      <Modal 
        ref="modal2" 
        :title="modalTitle" 
        :text="modalText"
        :modalType="modalType">
        >
      </Modal>
      <template v-if="displayChangeStatus && ((okrDetail.approvalStatus.id === 1 && getAuthRole === 'Manager') || (okrDetail.approvalStatus.id === 2 && getAuthRole === 'Admin'))" >
        <v-layout align-start justify-end> 
          <v-btn 
              color="accent"
              class="white--text"
                @click="changeState"
              >
              <feather type="edit-3" stroke-width=1 class="pr-3"></feather>
              Change status
          </v-btn>
        </v-layout>
      </template>
      </template>

      <v-layout row wrap>
        <!-- PROFIL -->
        <v-flex d-flex md3>  
              <v-card flat class="card">
                <v-toolbar flat class="transparent">
                  <v-list class="pa-0">
                    <v-list-tile avatar>
                        <v-list-tile-avatar>
                          <img :src ="imgUrl">
                        </v-list-tile-avatar>
                        <v-list-tile-content>
                          <v-list-tile-title>{{ okrDetail.userObjective.name }}</v-list-tile-title>
                        </v-list-tile-content>
                    </v-list-tile>
                  </v-list>
                </v-toolbar>
                <v-divider></v-divider>
                <v-list dense>
                  <v-list-tile>
                    <v-list-tile-content class="font-weight-medium">Username:</v-list-tile-content>
                    <v-list-tile-content class="align-end">{{ okrDetail.userObjective.username }}</v-list-tile-content>
                  </v-list-tile>
                  <v-list-tile>
                    <v-list-tile-content class="font-weight-medium">NIK:</v-list-tile-content>
                    <v-list-tile-content class="align-end">{{ okrDetail.userObjective.nik }}</v-list-tile-content>
                  </v-list-tile>
                  <v-list-tile>
                    <v-list-tile-content class="font-weight-medium">Department:</v-list-tile-content>
                    <v-list-tile-content class="align-end">{{ okrDetail.userObjective.userDepartment.name }}</v-list-tile-content>
                  </v-list-tile>
                  <v-list-tile>
                    <v-list-tile-content class="font-weight-medium">Role:</v-list-tile-content>
                    <v-list-tile-content class="align-end" v-for="(role,i) in okrDetail.userObjective.roles" :key="i">{{ role.name }}</v-list-tile-content>
                  </v-list-tile>

                   <!-- TOTAL -->
                  <v-divider></v-divider>
                  <v-list-tile class="mt-3">
                    <v-list-tile-content class="font-weight-medium">Total User Progress:</v-list-tile-content>
                  </v-list-tile>
                  <v-list-tile class="mb-3">
                    <v-layout>
                      <v-flex d-flex xs10>
                        <v-list-tile-content>
                              <v-progress-linear
                                  background-color="primary lighten-1"
                                  color="success"
                                  height="12"
                                  v-model="okrTotal"
                                  class="progress"
                                  >
                              </v-progress-linear>
                        </v-list-tile-content>
                      </v-flex>
                      <v-flex d-flex xs2>
                        <v-list-tile-content >
                            {{okrTotal}} %
                        </v-list-tile-content>
                      </v-flex>
                    </v-layout>
                  </v-list-tile>

                   <!-- TOTAL -->
                  <v-divider></v-divider>

                  <v-list-tile class="my-5">
                    <v-layout row>
                      <v-flex xs6>
                        <v-list-tile>
                            <v-list-tile-content class="font-weight-medium">Total objective</v-list-tile-content>
                        </v-list-tile>
                        <v-flex d-flex xs10>
                          <v-list-tile-content class="pl-2 accent--text">
                              <h1>{{okrData.length}}</h1>
                          </v-list-tile-content>
                        </v-flex>
                      </v-flex>
                      <v-divider vertical class="my-4"></v-divider>
                      <v-flex xs6>
                        <v-list-tile>
                          <v-list-tile-content class="font-weight-medium">Total Key Result</v-list-tile-content>
                        </v-list-tile>
                        <v-flex d-flex xs10>
                        <v-list-tile-content class="pl-2 warning--text">
                            <h1>{{totalKR}}</h1>
                        </v-list-tile-content>
                        </v-flex>

                      </v-flex>
                    
                    </v-layout>
                  </v-list-tile>
                  <v-divider></v-divider>
                </v-list>
              </v-card> 
        </v-flex>

        <!-- OKR -->
        <v-flex d-flex md9>
          <v-layout row wrap>

            <!-- OBJECTIVE -->
            <h3 class="ml-4 primary--text">Objective</h3>
            <v-flex d-flex xs12>
              <v-card flat class="ml-4 shadow">
                <v-toolbar color ="white" flat class="shadow py-2 px-0">
                  <v-flex align-self-center>
                  <v-toolbar-title class="text-xs-left headline primary--text font-weight-medium">{{ okrDetail.description }} </v-toolbar-title>
                  <p class="text-xs-left body-1 secondary--text">Date Created: {{ okrDetail.lastModifiedDate | formatDate }}</p>

                  <!-- <v-spacer></v-spacer> -->
                  </v-flex>
                  <v-flex>
                    <Chip class="text-xs-right"
                      :idStatus="okrDetail.approvalStatus.id"
                      :status="okrDetail.approvalStatus.status">
                  </Chip>
                  </v-flex>
                </v-toolbar>

                <v-divider></v-divider>
                <v-list>
                  <v-list-tile>
                    <div class="font-weight-medium  primary--text">Aligned Objective :&nbsp;</div>
                    <!-- nanti bakal href ke aligned objective -->
                    <div v-if="okrDetail.alignedObjective != null">
                    <a :href="'/detailOKR/' + okrDetail.alignedObjective.id" class="accent--text font-weight-medium">{{ okrDetail.alignedObjective.description }} <feather type="external-link" size=16 stroke-width=2></feather></a>
                    </div>
                    <div v-else>
                      <v-list-tile-content>None</v-list-tile-content>
                    </div>
                    <!-- <p v-else>There is no</p> -->
                  </v-list-tile>
                  <v-divider></v-divider>
                  <v-list-tile>
                    <v-list-tile-content class="font-weight-medium  primary--text">Current Objective Progress :</v-list-tile-content>
                  </v-list-tile>
                  <v-list-tile>
                    <v-layout>
                      <v-flex xs10>
                        <!-- <v-list-tile-content> -->
                          <v-progress-linear
                              background-color="primary lighten-1"
                              color="success"
                              height="12"
                              v-model="okrProgress"
                              class="progress"
                              >
                          </v-progress-linear>
                        <!-- </v-list-tile-content> -->
                      </v-flex>
                      <v-flex xs2>
                        <v-list-tile-content class="align-end display-2 primary--text">
                          {{okrProgress}} %
                        </v-list-tile-content>
                      </v-flex>
                    </v-layout>
                  </v-list-tile>
                  <v-list-tile>
                    <v-list-tile-content class="body-1 secondary--text">Last Modified: {{ okrDetail.lastModifiedDate | formatDate }}</v-list-tile-content>
                    <!-- <v-list-tile-content class="align-end">{{ okrDetail.lastModifiedDate }}</v-list-tile-content> -->
                  </v-list-tile>
                </v-list>
              </v-card>
            </v-flex>

            <!-- KR LIST -->
            <v-spacer></v-spacer>

            <v-flex d-flex xs12>
              <v-layout row wrap>
                <h3 class="pt-3 ml-4 primary--text">Key Results</h3>
                <v-flex v-for="(kr,i) in okrDetail.listKR" :key="i" d-flex xs12 md12>
                  <v-layout>
                    <v-flex d-flex xs1>
                      <feather type="corner-down-right" stroke-width=1 class="ma-4"></feather>
                    </v-flex>
                    <v-flex d-flex xs11>
                      <v-card column class="ml-2 shadow" flat>
                        <v-toolbar flat dense class="py-1" color ="white" >
                          <v-toolbar-title class="title primary--text">{{ kr.description }}</v-toolbar-title>
                          <v-spacer></v-spacer>
                          <a :href="kr.document" class="text-xs-center mb-0 py-4 accent--text pr-0"> <feather type="download" size=16 stroke-width=2 class="pr-1"></feather> Download document </a>
                        </v-toolbar>

                        <v-divider></v-divider>
                        <v-list>
                          <v-list-tile>
                            <v-list-tile-content class="body-2  primary--text">Current Key Result Progress :</v-list-tile-content>
                          </v-list-tile>
                          <v-list-tile>
                            <!-- <v-list-tile-content> -->
                              <!-- <v-progress-linear color="success" height="8" v-model="kr.progress"></v-progress-linear> -->
                              <v-layout>
                                <v-flex xs10>
                                  <v-progress-linear
                                    background-color="primary lighten-1"
                                    color="success"
                                    height="8"
                                    v-model="kr.progress"
                                    class="progress" >
                                  </v-progress-linear>
                                </v-flex>
                                <v-flex xs2>
                                  <v-list-tile-content class="align-end display-1 secondary--text">
                                    {{kr.progress}} %
                                  </v-list-tile-content>
                                </v-flex>
                              </v-layout>

                            <!-- </v-list-tile-content> -->
                          </v-list-tile>
                          <v-list-tile>
                            <v-list-tile-content class="caption secondary--text">Last Modified: {{ kr.lastModifiedDate | formatDate }}</v-list-tile-content>
                          </v-list-tile>
                        </v-list>
                      </v-card>
                    </v-flex>
                  </v-layout>
                </v-flex>
              </v-layout>
            </v-flex>


            <!-- COMMENT LIST -->
            <v-flex d-flex xs12>
              <v-layout row wrap>
                <h3 class="pt-3 ml-4">Comments</h3>
                <v-flex d-flex xs12>
                  <v-layout column wrap>
                  <v-expansion-panel
                    v-for="(comment,i) in okrDetail.listComment"
                    :key="i" class="mt-2 ml-4 mr-5"
                    >
                    
                    <v-flex d-flex xs12>
                    <template v-if="(getAuthUser.id === comment.commenter.id)">
                    <v-expansion-panel-content :readonly=false>
                      <template v-slot:actions>
                        <v-icon color="">edit</v-icon>
                      </template>
                      <template v-slot:header>
                        <v-layout wrap>
                          <v-flex d-flex xs12>
                            <div>
                              <var></var>
                              <var>{{ comment.commenter.name }}</var> -
                              <var v-for="(role,i) in comment.commenter.roles" :key="i"> {{ role.name }} </var> -
                              <var>{{ comment.commenter.userDepartment.name }}</var> -
                              <var>{{ comment.date | formatDate }}</var> 
                              <var v-if="comment.edited">- edited</var>
                            </div>
                          </v-flex>
                          <v-flex>
                          <div>{{ comment.description }}</div>
                          </v-flex>
                        </v-layout>
                      </template>
                      <v-card>
                        <v-card-text>
                          <v-textarea box label="Edit Comment" v-model="commentDescription"></v-textarea>
                          <v-card-actions>
                          <v-spacer></v-spacer>
                            <v-btn round @click="deleteComment(comment.id)" color="error">Delete</v-btn>
                            <v-btn round v-on:click="editComment(comment.id, comment.commenter)" color="success" depressed>Edit Comment</v-btn>
                          </v-card-actions>
                        </v-card-text>  
                      </v-card>
                    </v-expansion-panel-content>
                    </template>

                    <template v-else>
                    <v-expansion-panel-content :readonly=true>
                      <template v-slot:actions>
                        <v-icon color="">none</v-icon>
                      </template>
                      <template v-slot:header>
                        <v-layout wrap>
                          <v-flex d-flex xs12>
                            <div>
                              <var>{{ comment.commenter.name }}</var> -
                              <var v-for="(role,i) in comment.commenter.roles" :key="i"> {{ role.name }} </var> -
                              <var>{{ comment.commenter.userDepartment.name }}</var> -
                              <var>{{ comment.date | formatDate }}</var> 
                              <var v-if="comment.edited">- edited</var>
                            </div>
                          </v-flex>
                          <v-flex>
                          <div>{{ comment.description }}</div>
                          </v-flex>
                        </v-layout>
                      </template>
                      <v-card>
                        <v-card-text>
                          <v-textarea box label="Edit Comment" v-model="commentDescription"></v-textarea>
                          <v-card-actions>
                          <v-spacer></v-spacer>
                            <v-btn round @click="deleteComment(comment.id)" color="error">Delete</v-btn>
                            <v-btn round @click="editComment(comment.id)" color="success" depressed>Edit Comment</v-btn>
                          </v-card-actions>
                        </v-card-text>  
                      </v-card>
                    </v-expansion-panel-content>
                    </template>

                    </v-flex>
                  </v-expansion-panel>
                  </v-layout>
                </v-flex>
                
               
              </v-layout>
            </v-flex>

            <!-- SUBMIT COMMENT -->
            <template v-if="(getAuthUser.id === okrDetail.userObjective.id || getAuthRole === 'Manager' || getAuthRole === 'Admin')">
            <v-flex d-flex xs12 mt-4>
              <v-card flat class="ml-4 shadow">
                <v-card-text>
                  <v-textarea box label="Comment" v-model="commentDescription"></v-textarea>
                  <v-card-actions>
                  <v-spacer></v-spacer>
                    <v-btn round v-on:click="postComment(getAuthUser)" color="success" depressed>Post Comment</v-btn>
                  </v-card-actions>
                </v-card-text>
              </v-card>
            </v-flex>
            </template>
          </v-layout>
        </v-flex>
      </v-layout>
    </template>
  </v-container>
</template>

<script>
import Modal from '@/components/Modal'
import Chip from '@/components/Chip'
import calculateProgress from '@/functions/calculateProgress'
import calculateTotal from '@/functions/calculateTotal'
import { mapGetters } from 'vuex';


export default {
  name: "detailOKR",
  components: {
    Modal,
    Chip
  },
  data () {
      return{
          isLoading: true,
          displayChangeStatus: true,
          expand: false,
          search: '',
          okrDetail: {
            userObjective: {},
            approvalStatus: {},
            userObjective: {},
            alignedObjective: {},
            listKR: [],
            listComment: [],
          },
          okrData:[],
          okrProgress: 0,
          okrTotal:0,
          totalKR:0,

          modalTitle: '',
          modalText: '',
          modalType: '',

          //buat post comment
          commentDescription:'',

          //buat edit comment
          commentDisabled:false
      }
  },

  computed: {
        ...mapGetters([
            'getAuthUser',
            'getAuthRole'
        ]),
        imgUrl() {
          return 'https://ui-avatars.com/api/?background=FDCB24&rounded=true&size=34&color=ffffff&bold=true&name=' + this.okrDetail.userObjective.name
        }
    },

  methods:{
    calculateProgress,
    calculateTotal,

    async isCommenter(idCommenter){
      console.log(idCommenter)
      console.log(this.getAuthUserId)
      console.log(this.getAuthUserId === idCommenter)
      if(this.getAuthUserId === idCommenter){
        return true
      }
      return false
    },

    async postComment(idCommenter){
      try {
        if(this.commentDescription !== ''){
          const response = await this.$axios.post(`/api/objective/${this.$route.params.id}/success-add-comment`, {
            description: this.commentDescription, commenter: idCommenter
          })
          
          this.modalTitle = 'Success'
          this.modalText = 'Success adding comment'
          this.modalType = 'notification-success'
          this.$refs.modal2.changeModalState();
          this.commentDescription = ''
          
          //biar ngambil ulang data
          const response2 = await this.$axios.get(`/api/objective/${this.$route.params.id}`)
          this.okrDetail = response2.data.result
        } else{
          this.modalTitle = 'Alert'
          this.modalText = 'Comment not valid!'
          this.modalType = 'notification-error'
          this.$refs.modal2.changeModalState();
        }
        

        //this.$router.push(`/detailOKR/${this.$route.params.id}`)
        console.log(response)
      } catch (error) {
        console.log(error);
      }
    },

    async deleteComment(idComment){
      try {
        console.log(idComment)
        const response = await this.$axios.post(`/api/objective/${this.$route.params.id}/success-delete-comment`, {
          id: idComment,
        })
        
        this.modalTitle = 'Success'
        this.modalText = 'Success deleted comment'
        this.modalType = 'notification-success'
        this.$refs.modal2.changeModalState();
        this.commentDescription = ''
        
        //biar ngambil ulang data
        const response2 = await this.$axios.get(`/api/objective/${this.$route.params.id}`)
        this.okrDetail = response2.data.result

        //this.$router.push(`/detailOKR/${this.$route.params.id}`)
        console.log(response)
      } catch (error) {
        console.log(error);
      }
    },

    async editComment(idComment, idCommenter){
      try {
        if(this.commentDescription !== ''){
          console.log(idComment)
          const response = await this.$axios.post(`/api/objective/${this.$route.params.id}/success-edit-comment`, {
            id: idComment, description: this.commentDescription, commenter: idCommenter
          })
          
          this.modalTitle = 'Success'
          this.modalText = 'Comment Edited'
          this.modalType = 'notification-success'
          this.$refs.modal2.changeModalState();
          this.commentDescription = ''
          
          //biar ngambil ulang data
          const response2 = await this.$axios.get(`/api/objective/${this.$route.params.id}`)
          this.okrDetail = response2.data.result

          //this.$router.push(`/detailOKR/${this.$route.params.id}`)
          console.log(response)
        } else{
          this.modalTitle = 'Alert'
          this.modalText = 'Comment not valid!'
          this.modalType = 'notification-error'
          this.$refs.modal2.changeModalState();
        }
      } catch (error) {
        console.log(error);
      }
    },

    changeState : function() {
      this.modalTitle = 'Change Status',
      this.modalText = 'Approve or Reject this OKR?',
      this.modalType = 'confirmation',
      this.$refs.modal.changeModalState();
    },

    async onModalGreenSubmit() {
      try {
        const requestPayload = {
          id : this.okrDetail.id,
          approvalStatus: {
            id: this.okrDetail.approvalStatus.id===1 ? 2: 3
          }
        }
        const response = await this.$axios.post('/api/objective/update', requestPayload)
        //supaya okrDetailnya updated sama status yang udah diganti
        this.okrDetail = response.data.result
        console.log('hehehe')
        this.modalText = "This OKR has been approved"
        this.modalType = "notification-success"
        this.displayChangeStatus = !this.displayChangeStatus
      } catch(error) {
          console.warn(error)
      }
    },

    async onModalRedSubmit() {
      try {
        const requestPayload = {
          id: this.okrDetail.id,
          approvalStatus: {
            id: 4
          }
        }
        const response = await this.$axios.post('/api/objective/update', requestPayload)
        this.okrDetail = response.data.result
        this.modalType = "notification-error"
        this.modalText = "This OKR has been rejected"
        this.displayChangeStatus = !this.displayChangeStatus
      } catch(error) {
          console.warn(error)

      }

    }

  },

  //fungsi buat nge retrieve data dari API
  async mounted() {
    try {
        this.isLoading = true
        const response = await this.$axios.get(`/api/objective/${this.$route.params.id}`)
        this.okrDetail = response.data.result
        console.log(response.data.result)

        // buat dapetin rata2 progress dari setiap KR dalam 1 Objective
        this.okrProgress = calculateProgress(this.okrDetail.listKR)

        //const response2 = await this.$axios.get(`/api/get-all-objective/`)
        
        const okrData = await this.$axios.get('/api/objective/user/' + this.okrDetail.userObjective.id)
        this.okrData = okrData.data.result
        
        this.okrTotal = calculateTotal(this.okrData, this.okrDetail.userObjective, "total")

        this.okrData.forEach(element => {
              this.totalKR += element.listKR.length
          });
        this.today = moment().format("MMM Do YYYY");
      
    } catch(error) {
      this.isLoading = false

    } finally {
      this.isLoading = false
    }
  },


}

</script>
<style lang="scss" scoped>
    .btn {
        background: linear-gradient(360deg, #EFF3F6 0%, #FFFFFF 100%);
        border: 1px solid #DBE0E4;
        box-sizing: border-box;
        border-radius: 4px;
    }
    .field {
        width: 40px;
    }

    .progress {
        border-radius: 14px
    }
    .card {
        border: 1px solid #DBE0E4;
    }
    .shadow {
        box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);

    }
    .divider {
      width: 24px
    }

</style>