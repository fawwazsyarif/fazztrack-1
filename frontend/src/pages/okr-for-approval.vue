<template>
    <v-layout align-content-start column>
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
        <v-layout shrink align-content-center justify-end row pb-5>
            <v-flex xs4>
                <v-text-field 
                    class="field"
                    v-model="search"
                    append-icon="search"
                    label="Search objective or employee's name"
                    solo
                    flat
                ></v-text-field>
            </v-flex>
            <v-flex xs4>
                <h4 v-if="getAuthRole === 'Admin' ">OKR Employee All Department</h4>
                <h4 v-else-if=" getAuthRole === 'Manager' || getAuthRole === 'Employee' ">OKR Employee {{getAuthUserDepartment}} Department</h4>
            </v-flex>
            <v-flex xs4>
            </v-flex>
        </v-layout>
        
        <v-card flat class="card">
            <v-data-table
            :items="okrData"
            :search="search"
            :headers="headers"
            v-model="selected"
            select-all>
                <!-- <v-list v-for="(nav,i) in navigation" :key="i"> -->
                <template v-slot:items="props">
                    <tr>
                        <td>
                            <v-checkbox
                            v-model="props.selected"
                            primary
                            hide-details
                            ></v-checkbox>
                        </td>
                        <td class="text-xs-left font-weight-medium"  @click="props.expanded = !props.expanded"> 
                            <img src="@/assets/expand.svg" :class="{ 'expanded': props.expanded }"> 
                            {{ props.item.description }} 
                        </td>
                        <td class="text-xs-left">{{ props.item.userObjective.name }}</td>
                        <!-- TODO: benerin v-chipnya -->
                        <td class="text-xs-center">
                            {{ calculateProgress(props.item.listKR) }}
                        </td>
                        <td class="text-xs-center">
                            <Chip
                                :idStatus="props.item.approvalStatus.id"
                                :status="props.item.approvalStatus.status">
                            </Chip>
                        </td>
                        <td class="text-xs-center"> {{ props.item.lastModifiedDate | formatDate }}</td>
                        <td class="text-xs-center"><v-btn flat class="btn secondary--text" :to="{ name: 'detail-okr', params: { id: props.item.id } }">View Detail</v-btn></td>
                    </tr>
                </template>

                <template v-slot:expand="props">
                    <v-card flat class="card">
                        <v-data-table
                        :items="props.item.listKR"
                        hide-headers
                        hide-actions
                        >
                            <!-- <v-list v-for="(nav,i) in navigation" :key="i"> -->
                            <template v-slot:items="props">
                                <v-layout @click="props.expanded = !props.expanded" class="px-4" row align-center justify-start wrap>
                                    <v-flex xs1>
                                    </v-flex>
                                    <v-flex xs4>
                                        <p class="mb-0 text-xs-left"> {{ props.item.description }} </p>
                                    </v-flex>
                                    <v-flex xs3>
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
                                        <p class="mb-0 text-xs-center"> {{ props.item.progress }}% </p>
                                    </v-flex>
                                    <v-flex xs2 class="pl-5">
                                        <p class="mb-0 text-xs-center accent--text"> <feather type="download" size=16 height=50 stroke-width=1 class=" mb-0 pr-1"></feather> Download document </p>
                                    </v-flex>

                                </v-layout>
                                <v-divider></v-divider>
                            </template>
                            </v-data-table>
                    </v-card>
                </template>    
            </v-data-table>
        </v-card>
        <v-card  v-if="selected.length > 0" width="100%" class="bottomnav">
            <v-layout row>
                <v-flex xs9>
                    <p class="text-xs-left pt-2 pl-4"><v-chip color="success" class="white--text font-weight-bold">{{selected.length}}</v-chip><span>Selected objective</span></p>
                </v-flex>
                <v-flex xs3 class="pt-2 mr-0">
                    <v-btn 
                    color="accent"
                    class="white--text"
                    @click="changeState"
                    >
                    <feather type="edit-3" stroke-width=1 class="pr-3"></feather>Change status
                    </v-btn>
                </v-flex>

            </v-layout>
        </v-card>
    </v-layout>
</template>
<script>
import calculateProgress from '@/functions/calculateProgress'
import Chip from '@/components/Chip'
import Modal from '@/components/Modal'
import { mapGetters } from 'vuex'

export default {
    name : 'okrForApproval',
    components: {
        Modal,
        Chip
    },
    data () {
        return{
            expand: false,
            search: '',
            okrData: [],
            krData: [],
            selected: [],
            modalTitle: '',
            modalText: '',
            modalType: '',
            headers: [
            
            {
            //value is used for searching
            text: 'Objective Name',
            align: 'center',
            sortable: true,
            value: 'description',
            width: "35%"
            },
            { text: 'Owner', 
            align: 'center',
            value: 'userObjective.name',
            width: "15%"},
            { text: 'Overall Progress',
            align: 'center',
            sortable: true,
            },
            { text: 'Approval Status', 
            value: 'approvalStatus.status',
            align: 'center'
            },
            { text: 'Date',
            align: 'center',
            value: 'lastModifiedDate'
            },
            { text:'Detail',
            align: 'center',
            sortable: true}

        ],
        }
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
            //todo: buat untuk managernya
            if(this.getAuthRole === 'Admin') {
                const response = await this.$axios.get('/api/get-objective-for-approval/company')
                this.okrData = response.data.result
            } else if(this.getAuthRole === 'Manager') {
                const response = await this.$axios.get(`/api/get-objective-for-approval/${this.getAuthUserIdDepartment}`)
                this.okrData = response.data.result
            }
            console.log(this.okrData)
            console.log(this.selected)
    
            
            // this.krData = response.data.result.listKR
        } catch(error) {
            console.warn(error)
        }
        
    },
    methods: {
        calculateProgress(value) {
            let sum = 0;
            if(value.length == 0) {
                return sum
            } else {
                value.forEach(element => {
                    sum += element.progress
                });
            }
            console.log(sum)
            return sum/value.length
        },
         changeState : function() {
            this.modalTitle = 'Change Status',
            this.modalText = 'Approve or Reject these ' + this.selected.length + ' OKRs?',
            this.modalType = 'confirmation',
            this.$refs.modal.changeModalState();
        },

        
        async onModalGreenSubmit() {
            try {
                this.selected.forEach(element => {
                    element.approvalStatus.id = element.approvalStatus.id === 1 ? 2 : 3
                    delete element.userObjective
                    delete element.listKR
                })
                const requestPayload = {
                    objective: this.selected
                }
                console.log(requestPayload)
                const response = await this.$axios.post('/api/objective/update/selected', requestPayload)
                console.log(response)
                // supaya okrDetailnya updated sama status yang udah diganti
                if(this.getAuthRole === 'Admin') {
                    const response = await this.$axios.get('/api/get-objective-for-approval/company')
                    this.okrData = response.data.result
                } else if(this.getAuthRole === 'Manager') {
                    const response = await this.$axios.get(`/api/get-objective-for-approval/${this.getAuthUserIdDepartment}`)
                    this.okrData = response.data.result
                }
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
                this.selected.forEach(element => {
                    element.approvalStatus.id = 4
                    delete element.userObjective
                    delete element.listKR
                });
                const requestPayload = {
                    objective: this.selected
                    
                }
                const response = await this.$axios.post('/api/objective/update/selected', requestPayload)
                console.log(response)
                //supaya okrDetailnya updated sama status yang udah diganti
                if(this.getAuthRole === 'Admin') {
                    const response = await this.$axios.get('/api/get-objective-for-approval/company')
                    this.okrData = response.data.result
                } else if(this.getAuthRole === 'Manager') {
                    const response = await this.$axios.get(`/api/get-objective-for-approval/${this.getAuthUserIdDepartment}`)
                    this.okrData = response.data.result
                }

                this.modalText = "This OKR has been rejected"
                this.modalType = "notification-error"
                this.displayChangeStatus = !this.displayChangeStatus
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
    .bottomnav {
        bottom: 0;
        left:0;
        position: fixed;
    }
   .card {
        border: 1px solid #DBE0E4;
    }
</style>



