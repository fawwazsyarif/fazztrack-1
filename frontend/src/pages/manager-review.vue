<template >
    <v-container>
        <template v-if="isLoading">
            <p>Loading ...</p>
        </template>
        
        <template v-else>
            <template v-if="activePhase.name === 'execution'">
                <v-layout justify-center align-center fill-height>
                    <v-flex xs11>
                    <v-card flat class="card mx-auto py-5" max-height="600">
                        <v-img :src="require('@/assets/cancel.svg')" contain aspect-ratio="4" ></v-img>
                        <v-card-text>
                            <p class="font-weight-bold primary--text pt-4 mb-0">Oops Sorry :(</p>
                            <p class="font-weight-bold primary--text pt-4 mb-0">You can't do manager-review because you're currently on execution phase</p>
                        </v-card-text>
                        <v-btn color="accent" round class="px-5 mb-4" :to="{ name: 'my-okr' }">GO TO MY OKR</v-btn>
                    </v-card>
                    </v-flex>
                </v-layout>
            </template>
            <template v-else-if="activePhase.name === 'evaluation'">
                <!-- check if user has done self-review before -->
                <template v-if="userSelfReviewData !== null">
                    <v-layout justify-center align-center fill-height>
                        <v-flex xs11>
                        <v-card flat class="mx-auto py-5" max-height="600">
                            <v-img :src="require('@/assets/finish.svg')" contain aspect-ratio="4" ></v-img>
                            <v-card-text>
                                <p class="font-weight-bold primary--text pt-4 mb-0">You have completed your manager-review for this employee </p>
                            </v-card-text>
                            <v-btn color="accent" round class="px-5 mb-4" :to="{ name: 'manager-review-list' }">GO TO REVIEW LIST</v-btn>
                        </v-card>
                        </v-flex>
                    </v-layout>
                </template>

                <!-- if user has not done self-review for this term -->
                <template v-else>
                    <v-layout column>
                        <Modal
                        ref="modal"
                        :title="modalTitle"
                        :text="modalText"
                        :modalType="modalType">
                    </Modal>
                        <v-layout shrink align-center column>
                            <h2 class="mb-2">Manager Review</h2>
                            <v-card width="700" flat class="card pa-3">
                                <v-card-title class="text-xs-left primary--text">
                                    <span class="font-weight-bold mb-2">Overall Evaluation Grade should be given once the entire form is completed</span>
                                </v-card-title>
                                <v-card-title>Score guideline</v-card-title>
                                <v-card-title class="text-xs-left pt-0 primary--text">
                                    <div>
                                    <span>1 - Below Expectations</span><br>
                                    <span>2 - Needs Improvement</span><br>
                                    <span>3 - Meet Expectation</span><br>
                                    <span>4 - Exceeds Expectation</span><br>
                                    <span>5 - Outstanding</span><br>
                                    </div>
                                </v-card-title>
                            </v-card>
                            <p class="text-xs-left mt-4">How was your employee performance for this term?</p>
                            <v-flex>
                                <v-form v-model="valid" ref="form">                
                                    <v-card flat class="card mt-2 pa-3 primary--text" width="700" >
                                        <v-layout column>
                                            <v-layout column  v-for="(q,i) in questionData" :key="i"> 
                                                <v-flex xs12 class="text-xs-left">
                                                    <template v-if="i === 0 || i===2 || i===4 || i===6">
                                                        <h3>{{category[Math.floor(i/2)]}}</h3>
                                                        <v-divider class="my-3"></v-divider>
                                                    </template>
                                                    <p><span class="font-weight-bold">Q{{i+1}}: </span>{{ q.question }}</p>
                                                </v-flex>
                                                <v-flex xs6>
                                                    <v-layout row>
                                                    <p class="font-weight-bold">Bellow Expectation</p>
                                                    <v-radio-group 
                                                        row class="ma-0 ml-3 d-flex grow" 
                                                        v-model="listReviewQuestion[i].score"
                                                        :rules="[v => !!v || 'Item is required']" required>
                                                        <v-layout justify-space-around>
                                                            <v-flex shrink v-for="n in 5" :key="n">
                                                                <v-radio
                                                                :value="n"
                                                                class="mr-4"
                                                                color="primary">
                                                                </v-radio>
                                                                <p class="ml-2">{{n}}</p>
                                                            </v-flex>
                                                        </v-layout>   
                                                    </v-radio-group>
                                                    <p class="mr-4 text-xs-left font-weight-bold">Outstanding</p>
                                                    </v-layout>
                                                </v-flex>
                                            </v-layout>

                                        </v-layout>
                                        <v-divider></v-divider>

                                        <v-card-actions class="mt-2">
                                            <v-spacer></v-spacer>
                                            <v-btn flat color="error" @click="handleCancel">CANCEL</v-btn>
                                            <v-btn @click="changeState" :disabled="!valid" round color="success" class="px-5">SAVE</v-btn>
                                        </v-card-actions>
                                    </v-card>
                                </v-form>
                            </v-flex>
                        </v-layout>
                    </v-layout>
                </template>
            </template>
        </template>
    </v-container>
</template>
<script>
import { mapGetters } from 'vuex';
import Modal from '@/components/Modal'

export default {
    name: 'selfReview',

    components: {
        Modal,
    },

    computed: {
        ...mapGetters([
            'getAuthUser',
            'getAuthUserId',
        ]),
    },

    data() {
        return {
            questionData: [],
            listReviewQuestion: [],
            userSelfReviewData: [],
            category:["Start Small", "Take Lead", "Eager to Grow", "Pro Customer"],
            counter:0,
            valid: false,
            review:{},
            test: true,
            isLoading: true,
            activePhase: {},

            modalTitle: '',
            modalText: '',
            modalType: '',
        }
    },
    
    methods: {
        async addSelfReview() {
            const request= {
                reviewer: {
                    id: this.getAuthUserId
                },
                reviewee: {
                    id: this.$route.params.idReviewee
                },
                avgScore: 0,
                status: 'completed',
                type: 'manager-review',
                listReviewQuestion: this.listReviewQuestion,
            }
            if (this.$refs.form.validate()) {
                const response = await this.$axios.post('api/review/add', request)
                this.review= response.data.result
                console.log(response)

            }
        },

        changeState : function() {
            this.addSelfReview()
            this.modalTitle = 'Save Manager Review',
            this.modalText = 'Your manager-review has been saved',
            this.modalType = 'notification-success',
            this.$refs.modal.changeModalState();
            setTimeout(() => {
                this.$router.go("/manager-review-list");
            }, 700);
        },

        handleCancel: function() {
            setTimeout(() => {
              this.$router.push("/");
            }, 100);
        }
        

    },

    async mounted() {
        try {
            this.isLoading = true
            //API to check if user has done self review for this term
            const createdReview= await this.$axios.get(`/api/review/get/manager-review/${this.getAuthUserId}/${this.$route.params.idReviewee}`)
            this.userSelfReviewData = createdReview.data.result

            //API to get all questions on question model
            const response = await this.$axios.get('/api/question/all')
            this.questionData = response.data.result
            var i = 0;
            
            //API to check active phase
            const phase = await this.$axios.get('/api/get-active-phase')
            this.activePhase = phase.data.result

            //add questionReview to listReviewQueestion
            for (var i = 0; i < this.questionData.length; i++) {
                if(this.listReviewQuestion.length < this.questionData.length) {
                    this.listReviewQuestion.push({questionReview: this.questionData[i], score:0})
                }
            }
            console.log(this.userSelfReviewData)
        } catch(error) {
            this.isLoading = true

        } finally {
            this.isLoading = false
        }
    }
}
</script>
