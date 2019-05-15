<template>
   <v-layout row justify-center>
        <v-dialog
        v-model="modalDisplayed"
        width="700"
        persistent
        scrollable>
            <v-card class="primary--text">
                <template>
                    <v-card-title class="title mx-4 mt-4">
                        Assign Peer Reviewee
                    </v-card-title>
                    <v-card-text>
                        <v-flex xs-12 class="mx-4">
                            <v-select
                            :items="employees"
                            item-text="name"
                            item-value="id"
                            label="Select Reviewee"
                            v-model="idReviewee"
                            ></v-select>
                        </v-flex>
                    </v-card-text>
                    <v-divider></v-divider>
                    <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn color="error" flat  @click="handleCancel">Close</v-btn>
                        <V-btn color="success" flat @click="submitPeer">Submit Peer</V-btn>
                    </v-card-actions>
                </template>
            </v-card>
        </v-dialog>
    </v-layout>
</template>

<script>
import { mapGetters } from 'vuex';

export default {
    name : 'ModalAssignPeer',

    data () {
      return {
        reqRules: [v => !!v || "The input is required"],
        modalDisplayed: false,

        employees: [],
        idReviewee: null,
        idReviewer: null,
      }
    },
    methods: {
        changeModalState() {
            this.modalDisplayed = !this.modalDisplayed;
        },

		handleCancel: function () {
          this.modalDisplayed = !this.modalDisplayed;
        },

        async fetch(idReviewer) {
            try{
                this.idReviewer = idReviewer
                const response = await this.$axios.get(`/api/user/${this.getAuthUser.username}/employees/${this.idReviewer}/exclude-peer`)
                this.employees = response.data.result
                console.log(this.idReviewer)
            } catch(error) {
                console.log(error)
            }
        },

        async submitPeer(){
            try {
                if(this.idReviewee !== null){
                    const response = await this.$axios.post('/api/peer-review/add-reviewee', {
                        reviewee: {id:this.idReviewee}, reviewer: {id:this.idReviewer}
                    })
                    
                    this.$emit('updateTable')
                    this.modalDisplayed = !this.modalDisplayed;
                    this.idReviewee = null
                } else{
                    this.modalDisplayed = !this.modalDisplayed;
                }
                console.log(response)
            } catch (error) {
                console.log(error);
            }
        },

    },
    computed: {
        ...mapGetters([
            'getAuthUser',
            'getAuthRole',
            'getAuthName',
            'getAuthUserDepartment'
        ]),
        imgUrl() {
            return `https://ui-avatars.com/api/?background=48ACF0&rounded=true&size=36&color=ffffff&bold=true&name=${this.getAuthName}`
        },
    },
    
    async mounted() {
        try {
            const response = await this.$axios.get(`/api/user/${this.getAuthUser.username}/employees/`)
            this.employees = response.data.result
        } catch(error) {
            this.isLoading = false
            console.warn(error)
        } finally {
            this.isLoading = false

        }
    },
}
</script>