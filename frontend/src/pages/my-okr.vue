<template>
    <v-container grid-list-md text-xs-center justify-start>
    <ModalEdit
        ref="modalEdit"
        @updateTable="updateTable">
    </ModalEdit>
    <ModalAddOKR
        ref="modalAddOKR"
        @updateTable="updateTable">
    </ModalAddOKR>
    <Modal
        ref="modal"
        :title="modalTitle"
        :text="modalText"
        :modalType="modalType"
        greenButtonText="Delete"
        redButtonText="Cancel"
        @onModalGreenSubmit="onModalGreenSubmit"
        @onModalRedSubmit="onModalRedSubmit">
        >
    </Modal>
    <ModalUpdateProgress
        ref="modalUpdateProgress"
        @updateTable="updateTable">
    </ModalUpdateProgress>
        <v-layout column class="ma-auto pb-2 mb-4">
            <h2>Hello!</h2>
            <h3>What do you want to achieve today?</h3>
            <v-flex xs12 sm6>
                <v-btn color="accent" class="white--text" dark @click="displayAddOkr">
                    <feather type="plus" stroke-width=2 class="pr-2"></feather>
                    <v-divider vertical color="white" class="my-1"></v-divider>
                    <p class="ma-1 ml-3">Add New OKR</p>
                </v-btn>
            </v-flex>
        </v-layout>
        <template>
            <v-layout column class="ma-auto pb-2 mb-4">
                <p class="subtitle">Confused? Let's add your OKR from you Manager's Recommendation!</p>
                <v-flex xs12 sm6>
                    <v-btn color="accent" outline class="white--text" dark :to="{ name: 'view-okr-recommendation', params: { id: getAuthUser.manager }}">
                        <p class="ma-1 ml-3">view recommendation</p>
                    </v-btn>
                </v-flex>
            </v-layout>
        </template>
        <v-layout class="ma-auto pb-2">
            <h3>My OKR Overview</h3>
        </v-layout>
        <v-layout row align-content class="pb-4">
            <v-layout row class="ma-0 pa-auto">
                <v-flex xs5>
                    <v-layout column>
                        <v-flex xs12 mt-1>
                            <v-card height="77px" flat class="card">
                                <v-layout row mb-0>
                                    <v-flex xs5 pa-0 ml-2 class="text-xs-left">
                                        <v-icon color="accent" class="ma-2 pa-2" large>work</v-icon>
                                    </v-flex>
                                    <v-flex xs3 class="title text-xs-right my-4">
                                        <h2 class="title">{{okrData.length}}</h2>
                                    </v-flex>
                                    <v-divider vertical class="ma-2"></v-divider>
                                    <v-flex xs4 class="text-xs-left">
                                        <p class="mb-0 mt-3">Total Objective</p>
                                        <p class="pa-0 primary--text text--lighten-4">{{today}}</p>
                                    </v-flex>
                                </v-layout>
                            </v-card>
                        </v-flex>
                        <v-flex xs12 mt-2>
                            <v-card height="77px" flat class="card">
                                <v-layout row mb-0>
                                    <v-flex xs5 pa-0 ml-2  class="text-xs-left">
                                        <v-icon color="warning" class="ma-2 pa-2" large>check_circle</v-icon>
                                    </v-flex>
                                    <v-flex xs3 class="text-xs-right my-4">
                                        <h2 class="title">{{totalKR}}</h2>
                                    </v-flex>
                                    <v-divider vertical class="ma-2"></v-divider>
                                    <v-flex xs4 class="text-xs-left">
                                        <p class="mb-0 mt-3">Total key result</p>
                                        <p class="pa-0 primary--text text--lighten-4">{{today}}</p>
                                    </v-flex>
                                </v-layout>
                            </v-card>
                        </v-flex>
                    </v-layout>
                </v-flex>
                <v-flex xs7 class="ma-10">
                    <v-card dark color="white" height="170px" flat class="card">
                        <v-container>
                            <v-layout row>
                                <v-flex xs6>
                                    <h3 class="text-sm-left primary--text my-2">Total OKR Progress</h3>
                                </v-flex>
                                <v-flex xs6>
                                    <p class="primary--text text-xs-right mb-0">Current phase: {{activePhase.name}}</p>
                                </v-flex>
                            </v-layout>
                            <v-layout column>
                                 <v-flex xs12>
                                    <v-progress-linear
                                    v-model="okrTotalProgress"
                                    class="progress"
                                    background-color="primary lighten-1"
                                    color="success"
                                    ></v-progress-linear>
                                </v-flex>
                                <v-flex xs12>
                                    <h3 class="text-sm-right primary--text pa-auto">{{okrTotalProgress}}%</h3>
                                </v-flex>
                            </v-layout>
                           
                        </v-container>
                    </v-card>
                </v-flex>
            </v-layout>
        </v-layout>
        <v-layout shrink align-content-center justify-end>
            <v-flex xs3>
                <v-text-field
                    class="field"
                    v-model="search"
                    append-icon="search"
                    label="Search your objective name"
                    flat
                ></v-text-field>
            </v-flex>
            <v-flex xs6>
                <h2>My OKR</h2>
            </v-flex>
            <v-flex xs3>
            </v-flex>
        </v-layout>
    <v-layout align-content-start column>    
        <v-card flat class="card">
            <v-data-table
            :items="okrData"
            :search="search"
            :headers="headers">
                <template v-slot:items="props">
                    <tr >
                        <td class="text-xs-left font-weight-medium" @click="props.expanded = !props.expanded">
                            <img src="@/assets/expand.svg" :class="{ 'expanded': props.expanded }">
                            {{ props.item.description }}
                        </td>
                        <td class="text-xs-center">
                            {{ calculateProgress(props.item.listKR) }} %
                        </td>
                        <td class="text-xs-center">
                        <Chip
                            :idStatus="props.item.approvalStatus.id"
                            :status="props.item.approvalStatus.status">
                        </Chip>
                        </td>
                        <td class="text-xs-center"> {{ props.item.lastModifiedDate | formatDate }}</td>
                        <td class=" mb-0 text-xs-center"><v-btn flat class="btn secondary--text" :to="{ name: 'detail-okr', params: { id: props.item.id } }">View Detail</v-btn></td>
                        <td class="text-xs-center">
                            <v-icon @click="displayModal(props.item.id)">edit</v-icon>
                            
                        </td>
                        <td class="text-xs-center">
                            <v-icon @click="deleteOKR(props.item.id)">delete</v-icon>
                        </td>
                    </tr>
                </template>

                <template v-slot:expand="props">
                    <v-card flat>
                        <v-data-table
                        :items="props.item.listKR"
                        hide-headers
                        hide-actions
                        >
                            <template v-slot:items="props">
                                <v-layout @click="props.expanded = !props.expanded" class="px-4 my-2" row align-center justify-start wrap>
                                    <v-flex xs5 pl-4>
                                        <p class="mb-0 text-xs-left"> {{ props.item.description }} </p>
                                    </v-flex>
                                    <v-flex xs4>
                                        <v-progress-linear
                                            background-color="primary lighten-1"
                                            color="primary"
                                            height="10"
                                            xs3
                                            class ="progress"
                                            :value="props.item.progress">
                                        </v-progress-linear>
                                    </v-flex>
                                    <v-flex xs1>
                                        <p class="mb-0 mr-0 text-xs-center"> {{ props.item.progress }} % </p>
                                    </v-flex>
                                    <v-flex xs2 class="text-xs-right pr-0">
                                        <v-btn outline small class="primary--text" @click="displayUpdateProgress(props.item.id)">Edit progress</v-btn>
                                    </v-flex>
                                </v-layout>
                                <v-divider></v-divider>
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
    </v-container>
</template>

<script>
import calculateProgress from '@/functions/calculateProgress'
import calculateTotal from '@/functions/calculateTotal'
import Chip from '@/components/Chip'
import ModalEdit from '@/components/ModalEdit'
import ModalAddOKR from '@/components/ModalAddOKR'
import Modal from '@/components/Modal'
import ModalUpdateProgress from '@/components/ModalUpdateProgress'
import { mapGetters } from 'vuex';
import moment from 'moment'


export default {
    name : 'myOKR',
    data () {
        return {
            info: null,
            activePhase: {},
            today: '',
            idOKR: 0,
            expand: false,
            search: '',
            okrData: [],
            okrAllData: [],
            okrTotalProgress: 0,
            totalKR: 0,
            modalTitle: '',
            modalText: '',
            modalType: '',
            headers: [
            {
            //value is used for searching
            text: 'Objective Name',
            align: 'center',
            sortable: true,
            value: 'description'
            },
            { text: 'Progress',
            align: 'center',
            sortable: true,
            value: 'userObjective.name'
            },
            { text: 'Approval Status',
            align: 'center',
            },
            { text: 'Date',
            sortable: true,
            value: 'approvalStatus.status',
            align: 'center'
            },
            { text: 'Detail',
            sortable: false,
            align: 'center',
            value: 'lastModifiedDate'
            },
            { text:'Edit',
            align: 'center',
            sortable: false},
            { text:'Delete',
            align: 'center',
            sortable: false}
        ],
        }
    },

    components: {
        Chip,
        ModalEdit,
        ModalAddOKR,
        Modal,
        ModalUpdateProgress
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
        calculateProgress,

        // ngeluarin Modal untuk delete //
        async deleteOKR(idOKR) {
            this.idOKR = idOKR
            this.modalTitle = 'Delete OKR',
            this.modalText = 'Are you sure you want to delete this OKR?',
            this.modalType = 'confirmation-reverse',
            this.$refs.modal.changeModalState()
        },
        async onModalGreenSubmit() {
            try{
                const link ="/api/objective/delete/"+ this.idOKR
                const response = await this.$axios.post(link)
                const deletedData = await this.$axios.get('/api/objective/user/' + this.getAuthUserId)
                
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

        // ngeluarin Modal untuk edit OKR //
        displayModal(id) {
          this.$refs.modalEdit.changeModalState();
          this.$refs.modalEdit.fetch(id);
        },

        // ngeluarin Modal untuk add OKR baru //
        displayAddOkr() {
            this.$refs.modalAddOKR.changeModalState();
        },

        // ngeluarin Modal untuk update progress //
        displayUpdateProgress(id) {
            this.$refs.modalUpdateProgress.changeModalState();
            this.$refs.modalUpdateProgress.fetch(id);
        },
        
        async updateTable() {
            try{
                console.log("masuk updateTable")
                const newData = await this.$axios.get('/api/objective/user/' + this.getAuthUserId)
                this.okrData = newData.data.result
                console.log(newData)
                this.okrTotalProgress = calculateTotal(this.okrData, this.getAuthUser, "total")

                //remove okr recommendation
                var i;
                for(i=0; i<this.okrData.length; i++){
                    if(this.okrData[i].approvalStatus.id === 6) {
                        this.okrData.splice(i, 1);
                    }
                }
            } catch(error) {
                console.warn(error)
            }
        }
    },
    
    
    //fungsi buat nge retrieve data dari API
    async mounted() {
        try {
            this.isLoading = true
            const response = await this.$axios.get('/api/objective/user/' + this.getAuthUserId)
            this.okrData = response.data.result    
            console.log(this.okrData)    
            
            //remove okr recommendation
            var i;
            for(i=0; i<this.okrData.length; i++){
                if(this.okrData[i].approvalStatus.id === 6) {
                    this.okrData.splice(i, 1);
                }
            }
            
            const response2 = await this.$axios.get('/api/get-active-phase')
            this.activePhase = response2.data.result

            console.log("test") 
            console.log(this.okrData)
            this.okrTotalProgress = calculateTotal(this.okrData, this.getAuthUser, "total")

            this.okrData.forEach(element => {
              this.totalKR += element.listKR.length
            });

            console.log("hehe")
            console.log(this.totalKR)
            this.today = moment().format("MMM Do YYYY");
            console.log(this.today)

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

    .card {
        box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
    }
</style>
