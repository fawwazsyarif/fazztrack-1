<template>
    <v-container grid-list-md text-xs-center justify-start>
        <Modal
        ref="modal"
        :title="modalTitle"
        :text="modalText"
        :modalType="modalType"
        greenButtonText="Keep Assigning"
        redButtonText="Back to My OKR"
        @onModalGreenSubmit="onModalGreenSubmit"
        @onModalRedSubmit="onModalRedSubmit">
        >
        </Modal>
        <Modal 
        ref="modal2" 
        :title="modalTitle" 
        :text="modalText"
        :modalType="modalType">
        >
      </Modal>
        <v-layout column class="ma-auto pb-2 mb-4">
            <h2>Assign Peer</h2>
        </v-layout>
        
        <v-layout >
            <v-flex xs12 sm12 d-flex>
                <v-card class="primary--text">
                    <!-- <v-card-title class="title mx-4 mt-4">
                        SELECT REVIEWEE
                    </v-card-title> -->
                    <v-card-text>
                        <h2 class="title mx-4 mt-4">SELECT REVIEWEE</h2>
                        <h3>This is the employee that will be peer-reviewed by his/her peer-reviewer</h3>
                        <v-flex xs-12 class="mx-4">
                            <v-select
                            :items="employees"
                            item-text="name"
                            item-value="id"
                            label="Select Reviewee"
                            solo
                            v-model="idReviewee"
                            ></v-select>
                        </v-flex>
                    </v-card-text>

                    <!-- <v-card-title class="title mx-4 mt-4">
                        SELECT REVIEWER
                    </v-card-title> -->
                    <v-card-text>
                        <h2 class="title mx-4 mt-4">SELECT REVIEWER</h2>
                        <h3>This is the employee(s) that will be peer-reviewing the reviewee</h3>
                        <v-flex xs-12 class="mx-4">
                            <v-select
                            :items="employees"
                            item-text="name"
                            item-value="id"
                            label="Select Reviewer 1"
                            solo
                            v-model="idReviewer1"
                            ></v-select>
                        </v-flex>
                    </v-card-text>
                    
                    <v-card-text>
                        <v-btn round v-on:click="changeState" color="error" depressed>Cancel</v-btn>
                        <v-btn round v-on:click="submitPeer" color="success" depressed>Submit Peer</v-btn>
                    </v-card-text>
                    
                </v-card>
            </v-flex>
        </v-layout>

    </v-container>
</template>

<script>
import Modal from '@/components/Modal'
import Chip from '@/components/Chip'
import { mapGetters } from 'vuex';

export default {
    name : 'assignPeer',
    data () {
        return {
            employees: this.getAuthUser,
            idReviewee: null,
            idReviewer1: null,
            idReviewer2: null,
            idReviewer3: null,

            modalTitle: '',
            modalText: '',
            modalType: '',

            isLoading: true,
            displayChangeStatus: true,
            expand: false,
        }
    },

    components: {
        Chip,
        Modal,
    },
    computed: {
        ...mapGetters([
            'getAuthUser',
            'getAuthRole',
            'getAuthName',
            'getAuthUserDepartment',
            'getAuthUserId'
        ]),
        imgUrl() {
            return `https://ui-avatars.com/api/?background=48ACF0&rounded=true&size=36&color=ffffff&bold=true&name=${this.getAuthName}`
        },
    },

    methods: {
        async submitPeer(){
            try {
                if(this.idReviewee !== null && this.idReviewer1 !== null){
                    const response = await this.$axios.post('/api/peer-review/add-reviewee', {
                        description:'', reviewee: {id:this.idReviewee}, reviewer: {id:this.idReviewer1}
                    })
                    
                    this.modalTitle = 'Success'
                    this.modalText = "Success Assigning Employee's Peer"
                    this.modalType = 'notification-success'
                    this.$refs.modal2.changeModalState();
                    
                    //biar selection jadi null lagi
                    this.idReviewee= null
                    this.idReviewer1= null
                    this.idReviewer2= null
                    this.idReviewer3= null
                } else{
                    this.modalTitle = 'Alert'
                    this.modalText = 'Assignment not valid!'
                    this.modalType = 'notification-error'
                    this.$refs.modal2.changeModalState();
                }
                console.log(response)
            } catch (error) {
                console.log(error);
            }
        },

        changeState : function() {
            this.modalTitle = 'Cancel',
            this.modalText = 'Cancel peer assignment?',
            this.modalType = 'confirmation',
            this.$refs.modal.changeModalState();
        },

        async onModalGreenSubmit() {
            try {
                this.displayChangeStatus = !this.displayChangeStatus
                this.$refs.modal.changeModalState();
            } catch(error) {
                console.warn(error)
                console.log('hihihi')
            }
        },

        async onModalRedSubmit() {
            try {
                this.displayChangeStatus = !this.displayChangeStatus
                this.$router.push('/')
            } catch(error) {
                console.warn(error)

            }
        }
    },
    
    //fungsi buat nge retrieve data dari API
    async mounted() {
        try {
            this.isLoading = true
            const response = await this.$axios.get('/api/user/' + this.getAuthUser.username + '/employees')
            this.employees = response.data.result 

        } catch(error) {
            this.isLoading = false

            console.warn(error)
        } finally {
            this.isLoading = false

        }
    },
};
</script>

<style lang="scss" scoped>
    .btn {
            background: linear-gradient(360deg, #EFF3F6 0%, #FFFFFF 100%);
            border: 1px solid #DBE0E4;
            box-sizing: border-box;
            border-radius: 4px;
        }

    .progress {
        border-radius: 14px
    }

    .expanded {
            transform: rotate(90deg);
        }
</style>
