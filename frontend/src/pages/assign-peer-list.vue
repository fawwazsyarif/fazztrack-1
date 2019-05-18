<template>
    <v-layout align-content-start column>
        <v-layout shrink align-content-center justify-end row pb-5>
            <ModalAssignPeer
                ref="modalAssignPeer"
                @updateTable="updateTable">
            </ModalAssignPeer>
            <Modal
                ref="modal"
                :title="modalTitle"
                :text="modalText"
                :modalType="modalType"
                greenButtonText="Delete"
                redButtonText="Cancel"
                @onModalGreenSubmit="onModalGreenSubmit"
                @onModalRedSubmit="onModalRedSubmit">
            </Modal>
            <Modal
                ref="modal2"
                :title="modalTitle"
                :text="modalText"
                :modalType="modalType">
            </Modal>
            <v-flex xs4>
                <!-- <v-card> -->
                <v-text-field 
                    class="field"
                    v-model="search"
                    append-icon="search"
                    label="Search employee's name or department"
                    solo
                    flat
                ></v-text-field>
                <!-- </v-card> -->
            </v-flex>
            <v-flex xs4>
                <h4>Peer Review List</h4>
            </v-flex>
            <v-flex xs4>
                <!-- <h4>OKR Employee All Department</h4> -->
            </v-flex>
        </v-layout>
        
        <v-card>
            <v-data-table
            :items="employees"
            :search="search"
            :headers="headers">
                <!-- <v-list v-for="(nav,i) in navigation" :key="i"> -->
                <template v-slot:items="props">
                    <tr>
                        <td class="text-xs-left" @click="props.expanded = !props.expanded">
                            <img src="@/assets/expand.svg" :class="{ 'expanded': props.expanded }">
                            {{ props.item.name }}
                        </td>
                        <td class="text-xs-center"> {{ props.item.userDepartment.name }}</td>
                        <td class="text-xs-center"><v-btn flat class="btn secondary--text" @click="displayAssignPeer(props.item.id)">Assign Reviewee</v-btn></td>
                    </tr>
                </template>

                <template v-slot:expand="props">
                    <v-card flat>
                        <v-data-table
                        :items="props.item.listReviewerReview"
                        hide-headers
                        hide-actions
                        >
                            <!-- <v-list v-for="(nav,i) in navigation" :key="i"> -->
                            <template v-slot:items="props">
                                <template v-if="props.item.type === 'peer-review'">
                                    <v-layout @click="props.expanded = !props.expanded" class="px-4" row align-center justify-start wrap>
                                        <v-flex xs10 pl-4>
                                            <p class="mb-0 pa-2 text-xs-left"> {{ props.item.reviewee.name }} </p>
                                        </v-flex>
                                        <v-flex xs2 pl-4>
                                            <p class="mb-0 pa-2 text-xs-left">
                                                <v-icon @click="deleteReview(props.item.id, props.item.status)">delete</v-icon>
                                            </p>
                                        </v-flex>
                                    </v-layout>
                                </template>
                            </template>
                        </v-data-table>
                    </v-card>
                </template>

                <v-alert v-slot:no-results :value="true" color="error" icon="warning">
                    Your search for "{{ search }}" found no results.
                </v-alert>
            </v-data-table>
        </v-card>
    </v-layout>    
</template>
<script>
import calculateProgress from '@/functions/calculateProgress'
import Chip from '@/components/Chip'
import { mapGetters } from 'vuex'
import Modal from '@/components/Modal'
import ModalAssignPeer from '@/components/ModalAssignPeer'

export default {
    name : 'assign-peer-list',

    data () {
        return{
            idReview: '',
            info: null,
            expand: false,
            modalTitle: '',
            modalText: '',
            modalType: '',

            search: '',
            employees: [],
            employeesDropdown: [],
            headers: [
                {
                //value is used for searching
                text: 'My Employees (as Reviewer)',
                align: 'center',
                sortable: true,
                value: 'reviewee.name'
                },
                { text: 'Department', 
                align: 'center',
                value: 'reviewee.userDepartment.name'
                },
                { text:'',
                align: 'center',
                sortable: true
                }
            ],
        }
    },
     components: {
        Chip,
        Modal,
        ModalAssignPeer,
    },

    computed: {
        ...mapGetters([
            'getAuthUser',
            'getAuthRole',
            'getAuthName',
            'getAuthUserIdDepartment',
            'getAuthUserDepartment'
        ])
    },

    //fungsi buat nge retrieve data dari API
    async mounted() {
        try {
            const response = await this.$axios.get(`/api/user/${this.getAuthUser.username}/employees`)
            this.employees = response.data.result
            console.log(this.employees)
        } catch(error) {
            console.warn(error)
        }
        
    },
    
    methods:{
        displayAssignPeer(idReviewer) {
            this.$refs.modalAssignPeer.changeModalState();
            this.$refs.modalAssignPeer.fetch(idReviewer);
        },

        deleteReview(idReview, status){
            if(status==="incomplete"){
                this.idReview = idReview,
                this.modalTitle = 'Delete Peer',
                this.modalText = 'Are you sure you want to delete this Peer?',
                this.modalType = 'confirmation-reverse',
                this.$refs.modal.changeModalState()
            } else{
                this.modalTitle = 'Delete Peer',
                this.modalText = "This peer has completed his/her Peer Review, so you can't delete it",
                this.modalType = 'notification-error',
                this.$refs.modal2.changeModalState()
            }
        },
        async onModalGreenSubmit() {
            try{
                const response = await this.$axios.post(`/api/peer-review/delete/${this.idReview}`)
                this.updateTable();
                this.$refs.modal.changeModalState();

            } catch(error){
                console.warn(error)
            }
        },
        async onModalRedSubmit() {
            console.log("masuk onmodalred")
            this.$refs.modal.changeModalState();
        },

        async updateTable() {
            try{
                const response = await this.$axios.get(`/api/user/${this.getAuthUser.username}/employees`)
                this.employees = response.data.result
                console.log(this.employees)
            } catch(error) {
                console.warn(error)
            }
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
        width: 400px;
        height: 35px;
        border-radius: 4px;
    }

    .okr-expanded > td > p {
        padding-left: 24px;
        margin: 0;
    }

    .progress {
        border-radius: 14px
    }

    .expanded {
        transform: rotate(90deg);
    }
    .card {
        border: 1px solid #DBE0E4;
    }
</style>



