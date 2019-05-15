<template>
<v-layout align-content-start column>
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
                <h4>Your OKR Recommendation</h4>
            </v-flex>
            <v-flex xs4 class="text-xs-right">
                <template v-if="getAuthUser.role === 'Manager'">
                <v-btn color="accent" class="white--text" @click="displayAddOkrRecommend">
                    <feather type="plus" stroke-width=2 class="pr-2"></feather>
                    <v-divider vertical color="white" class="my-1"></v-divider>
                    <p class="ma-1 ml-3">Add New OKR</p>
                </v-btn>
                </template>
            </v-flex>
            
        </v-layout>
        <v-card class="card">
            <v-data-table
            :items="okrData"
            :search="search"
            :headers="headers">
                <!-- <v-list v-for="(nav,i) in navigation" :key="i"> -->
                <template v-slot:items="props">
                    <tr>
                        <td class="text-xs-left" @click="props.expanded = !props.expanded">
                            <img src="@/assets/expand.svg" :class="{ 'expanded': props.expanded }">
                            {{ props.item.description }}
                        </td>
                        <td class="text-xs-left">{{ props.item.userObjective.name }}</td>
                        <td class="text-xs-center">{{props.item.userObjective.userDepartment.name}}</td>
                        <td class="text-xs-center">
                        <Chip
                            :idStatus="props.item.approvalStatus.id"
                            :status="props.item.approvalStatus.status">
                        </Chip>
                        </td>
                        <td class="text-xs-center"> {{ props.item.lastModifiedDate | formatDate }}</td>
                        <template v-if="getAuthUser.id !== $route.params.id">
                            <td class="text-xs-center">
                            <v-btn outline color="accent" class="white--text" dark @click="displayModal(props.item)">
                                <feather type="plus" stroke-width=2 ></feather>
                                <p class="ma-1">Add to my OKR</p>
                            </v-btn>
                            </td>
                        </template>
                        <td class="text-xs-center"><v-btn flat class="btn secondary--text" :to="{ name: 'detail-okr', params: { id: props.item.id } }">View Detail</v-btn></td>
                        <template v-if="getAuthUser.id === idUserRecommendation">
                            <td class="text-xs-center">
                                <v-icon @click="displayModalEdit(props.item.id)">edit</v-icon>
                            </td>
                            <td class="text-xs-center">
                                <v-icon @click="deleteOKR(props.item.id)">delete</v-icon>
                            </td>
                        </template>
                    </tr>
                </template>

                <template v-slot:expand="props">
                    <v-card flat>
                        <v-data-table
                        :items="props.item.listKR"
                        hide-headers
                        hide-actions
                        >
                            <!-- <v-list v-for="(nav,i) in navigation" :key="i"> -->
                            <template v-slot:items="props">
                                <v-layout @click="props.expanded = !props.expanded" class="px-4" row align-center justify-start wrap>
                                    <v-flex xs5 pl-4>
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
                                    <v-flex xs3>
                                        <a :href="props.item.document" class="mb-0 text-xs-center accent--text"> <feather type="download" size=16 height=50 stroke-width=1 class=" mb-0 pr-1"></feather> Download document </a>
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
         <ModalAddOKRRecommend
            ref="modalAddOKRRecommend"
            @updateTable="updateTable">
        </ModalAddOKRRecommend>
        <Modal
            ref="modal"
            :title="modalTitle"
            :text="modalText"
            :modalType="modalType"
            greenButtonText="Save to my okr"
            redButtonText="Cancel"
            @onModalGreenSubmit="saveOKRDraft"
            @onModalRedSubmit="onModalRedSubmit">
            >
        </Modal>
        <ModalEdit
            ref="modalEdit"
            @updateTable="updateTable">
        </ModalEdit>
        <Modal
            ref="modalDelete"
            :title="modalTitle"
            :text="modalText"
            :modalType="modalType"
            greenButtonText="Delete"
            redButtonText="Cancel"
            @onModalGreenSubmit="onModalGreenSubmit"
            @onModalRedSubmit="onModalRedSubmit2">
            >
        </Modal>
    </v-layout>
</template>
<script>
import calculateProgress from '@/functions/calculateProgress'
import Chip from '@/components/Chip'
import ModalAddOKRRecommend from '@/components/ModalAddOKRRecommend'
import Modal from '@/components/Modal'
import ModalEdit from '@/components/ModalEdit'
import { mapGetters } from 'vuex'

export default {
    name: 'view-okr-recommendation',
    data () {
        return{
            idUserRecommendation: this.$route.params.id,
            idOKR:'',
            okrRecommendationData:{},
            modalTitle: '',
            modalText: '',
            modalType: '',
            expand: false,
            search: '',
            okrData: [],
            headers: [

            {
            //value is used for searching
            text: 'Objective Name',
            align: 'center',
            sortable: true,
            value: 'description'
            },

            { text: 'Owner',
            align: 'center',
            sortable: true,
            value: 'userObjective.name' },

            { text: 'Department',
            align: 'center',
            sortable: true,
            value: 'userObjective.userDepartment.name' },
            
            { text: 'Approval Status',
            align: 'center',
            value: 'approvalStatus.status',
            },
            { text: 'Date',
            sortable: true,
            align: 'center',
            value: 'lastModifiedDate'
            },
            { text:'',
            align: 'center',
            sortable: false
            },
            { text:'',
            align: 'center',
            sortable: false
            },
            { text:'',
            align: 'center',
            sortable: false
            },
            { text:'',
            align: 'center',
            sortable: false
            },

        ],
        }
    },

    components: {
        Chip,
        ModalAddOKRRecommend,
        Modal,
        ModalEdit,
    },

    methods: {
        calculateProgress,

        displayAddOkrRecommend() {
            this.$refs.modalAddOKRRecommend.changeModalState();
        },

        displayModalEdit(id) {
          this.$refs.modalEdit.changeModalState();
          this.$refs.modalEdit.fetch(id);
        },

        // munculin modal buat save as draft okr recomended
        async displayModal(okrData) {
            this.okrRecommendationData = okrData,
            this.modalTitle = 'Add OKR Recommendation as My OKR',
            this.modalText = 'Are you sure you want to add this OKR as My OKR? The OKR will be saved as draft and you can edit them later',
            this.modalType = 'confirmation-reverse',
            this.$refs.modal.changeModalState()
        },

        async saveOKRDraft() {
            if(this.alignedObjective) {
                this.request = {
                    userObjective: {id: this.getAuthUser.id},
                    description: this.okrRecommendationData.description,
                    approvalStatus: {
                        id: 5
                    },
                    alignedObjective: {
                        id: this.okrRecommendationData.alignedObjective
                    }
                }
            } else {
                this.request = {
                    userObjective: {id: this.getAuthUser.id},
                    description: this.okrRecommendationData.description,
                    approvalStatus: {
                        id: 5
                    }
                }
            }
            const response = await this.$axios.post('/api/objective/add', this.request)
            console.log(response)

            const response2 = await this.$axios.post((`/api/objective/addKRlist/${response.data.result.id}`), this.okrRecommendationData.listKR)
            this.$refs.modal.changeModalState();
            setTimeout(() => {
                this.$router.push("/");
            }, 700);
        },

        async onModalRedSubmit() {
            this.$refs.modal.changeModalState();
        },

        // ngeluarin Modal untuk delete //
        async deleteOKR(idOKR) {
            this.idOKR = idOKR
            this.modalTitle = 'Delete OKR',
            this.modalText = 'Are you sure you want to delete this OKR?',
            this.modalType = 'confirmation-reverse',
            this.$refs.modalDelete.changeModalState()
        },

        async onModalGreenSubmit() {
            try{
                const link ="/api/objective/delete/"+ this.idOKR
                const response = await this.$axios.post(link)
                this.updateTable();
                this.$refs.modalDelete.changeModalState();
            } catch(error){
                console.warn(error)
            }
        },

        async onModalRedSubmit2() {
            this.$refs.modalDelete.changeModalState();
        },

        
        
        async updateTable() {
            try{
                if( this.getAuthRole === 'Manager') {
                    console.log(this.getAuthUser)
                    const response = await this.$axios.get(`/api/get-recommended-okr/${this.getAuthUser.id}`)
                    this.okrData = response.data.result
                    console.log(this.okrData)

                } else if(this.getAuthRole === 'Employee' || this.getAuthRole === 'Admin') {
                    console.log('masuk siniiiii')
                    console.log(this.getAuthUser)
                    const response = await this.$axios.get(`/api/get-recommended-okr/${this.getAuthUser.manager}`)
                    this.okrData = response.data.result
                    console.log(this.getAuthUser)
                }
            } catch(error) {
                console.warn(error)
            }
        }
    },
    computed: {
      ...mapGetters([
          'getAuthUser',
          'getAuthRole',
          'getAuthName',
          'getAuthUserIdDepartment',
          'getAuthUserDepartment'

      ]),
    },
    async mounted() {
        try {
            if(this.$route.params.id === null){
                console.log('masuk siniiiii')
                console.log(this.getAuthUser)
                const response = await this.$axios.get(`/api/get-recommended-okr/${this.getAuthUser.id}`)
                this.okrData = response.data.result
                console.log(this.getAuthUser)
            } else{
                console.log('masuk siniiiii')
                console.log(this.getAuthUser)
                const response = await this.$axios.get(`/api/get-recommended-okr/${this.$route.params.id}`)
                this.okrData = response.data.result
                console.log(this.getAuthUser)
            }

        } catch(error) {
            console.warn(error)
        }
    }


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


