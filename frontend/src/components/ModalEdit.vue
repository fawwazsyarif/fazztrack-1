<template>
   <v-layout row justify-center>
        <v-dialog
        v-model="modalDisplayed"
        width="700"
        persistent
        scrollable>
            <v-card class="primary--text">
                <!-- if the system is currently on execution phase -->
                <template v-if="activePhase.name === 'execution'">
                    <v-card-title class="title">Update OKR</v-card-title>
                    <v-card-text style="height: 360px">
                    <v-form  v-model="valid" ref="form" lazy-validation>
                        <v-flex xs-12 class="mx-4">
                            <p class="subheading font-weight-bold text-xs-left my-2"> Objective </p>
                            <v-select
                                v-model="selectedData"
                                :items="listManagerObjective"
                                item-text="description"
                                item-value="id"
                                label="Select you manager objective to be aligned (optional)"
                                full-width
                            ></v-select>
                        </v-flex>
                        <v-flex xs-12 class="mx-4 mt-4">
                            <v-text-field
                                :counter="255"
                                label="Objective"
                                :rules="reqRules"
                                type="text"
                                required
                                v-model="okrData.description"
                            ></v-text-field>
                        </v-flex>
                        <v-flex xs-12 class="mx-4">
                            <p class="subheading font-weight-bold text-xs-left my-2"> Update Key Result</p>
                            <v-list v-for="(kr, i) in okrData.listKR" :key="i" >
                                <v-layout align-center >
                                    <v-flex xs1>
                                        {{i + 1}}.
                                    </v-flex>
                                    <v-flex xs10>
                                        <v-text-field
                                            :counter="255"
                                            label="key result"
                                            :rules="reqRules"
                                            type="text"
                                            required
                                            v-model="kr.description"
                                        ></v-text-field>
                                    </v-flex>
                                    <v-flex align-self-center offset-xs1 ml-1>
                                        <template v-if="i > 0">
                                            <v-icon
                                                size="24"
                                                @click="removeObjectFromListKR(i)">
                                                delete
                                            </v-icon>
                                        </template>
                                    </v-flex>
                                    </v-layout>
                            </v-list>
                        </v-flex>
                        <template v-if="okrData.listKR.length < 6">
                            <v-icon size="30" @click="pushEmptyObjectToListKR">add_circle</v-icon>
                        </template>
                        </v-form>
                    </v-card-text>
                    <v-divider></v-divider>
                        <!-- CTA button -->
                        <v-card-actions class="my-0">
                            <template v-if="okrData.approvalStatus.id === 6">
                                <v-spacer></v-spacer>
                                <v-btn color="error" flat  @click="handleCancel">Close</v-btn>
                                <v-btn color="success" flat @click="saveOKR(6)">Update Recommended OKR</v-btn>
                            </template>
                            <template v-else>
                                <template v-if="okrData.approvalStatus.id === 5 || okrData.approvalStatus.id === 4">
                                    <v-btn color="error" flat  @click="handleCancel">Close</v-btn>
                                    <v-spacer></v-spacer>
                                    <V-btn color="accent" flat @click="saveOKR(5)">Save as draft</V-btn>
                                    <v-btn color="success" flat @click="saveOKR(1)">Save for Approval</v-btn>
                                </template>
                                <template v-else>
                                    <v-spacer></v-spacer>
                                    <v-btn color="error" flat  @click="handleCancel">Close</v-btn>
                                    <v-btn color="success" flat @click="saveOKR(1)">Update OKR</v-btn>
                                </template>
                            </template>
                        </v-card-actions>
                </template>

                <!-- if the system is currently on evaluation phase -->
                <template v-else-if="activePhase.name === 'evaluation'">
                    <v-card-title>
                        <span class="title"> Create new OKR</span>
                    </v-card-title>
                    <v-card-text>
                        <v-img :src="require('@/assets/cancel.svg')" contain aspect-ratio="2"></v-img>
                        <p class="subheading mt-4"> Oopps! sorry :(</p>
                        <p class="subheading">You can't edit new OKR because you are currently on  <span class="font-weight-bold">evaluation phase</span> </p>
                    </v-card-text>
                    <v-btn color="error" flat  @click="handleCancel">Close</v-btn>
                </template>
            </v-card>
        </v-dialog>
    </v-layout>
</template>

<script>
import { mapGetters } from 'vuex';

  export default {
    name: 'ModalEdit',

    data: () => ({
        valid: true,
        reqRules: [v => !!v || "The input is required"],
        modalDisplayed: false,
        okrData: {},
        id_objective:'',
        description:'',
        okrData: {
            description:'',
            listKR: [],
        },
        activePhase: {},
        listManagerObjective: [],
        selectedData: '',
        request: {},
        tempKR:[]
         

    }),
    computed: {
        ...mapGetters([
            'getAuthUser',
            'getAuthRole',
            'getAuthName',
            'getAuthUserDepartment',
            'getAuthUserId',

        ]),
    },

    methods: {
        changeModalState() {
          this.modalDisplayed = !this.modalDisplayed;
        },
        pushEmptyObjectToListKR(data) {
            if (this.okrData.listKR.length < 6) {
                this.okrData.listKR.push({ description: '', progress: 0, id_objective: this.okrData.id })
            }
        },
        
        async removeObjectFromListKR(index){
            const krTemp = this.okrData.listKR[index]
            // if(krTemp.description) {
            //     const response = await this.$axios.post(`/api/objective/deleteKR/${krTemp.id}`)
            // }
            this.tempKR.push(this.okrData.listKR[index])
            this.okrData.listKR.splice(index, 1);
        },
        
        async saveOKR(idApproval) {
            console.log("jojojojoj")
            
            if(this.selectedData !== null) {
                console.log(this.selectedData)
                this.request = {
                    description: this.okrData.description,
                    approvalStatus: {
                        id: idApproval
                    }, alignedObjective: {
                        id: this.selectedData
                    }
                }
                
            } else {
                console.log("no")
                this.request = {
                    description: this.okrData.description,
                    approvalStatus: {
                        id: idApproval
                    },
                }
            }
            if(idApproval === 1 && this.getAuthRole === 'Manager') {
                this.request.approvalStatus.id = 2
            }
            
            if (this.$refs.form.validate()) {
                //update objective
                const response = await this.$axios.post(`/api/objective/update/${this.okrData.id}`, this.request)
                this.id_objective = response.data.result.id
                
                //update key result
                const request2 = this.okrData.listKR

                //buat ngereset progress
                this.okrData.listKR.forEach(element => {
                    element.progress = 0
                })
                const response2 = await this.$axios.post('/api/objective/updateKRlist/' + this.okrData.id, request2)

                this.tempKR.forEach(element => {
                    if(element.description != '') {
                        this.$axios.post(`/api/objective/deleteKR/${element.id}`)
                    }
                });

                const responseEdited = await this.$axios.get('/api/objective/' + this.okrData.id)
                console.log(responseEdited)
                
                this.$emit('updateTable')
                this.changeModalState()
            }
            
        },
    
        handleCancel: function () {
            this.changeModalState()
        },

        async fetch(id) {
            try{
                const response = await this.$axios.get('/api/objective/' + id)
                this.okrData = response.data.result
                this.selectedData = this.okrData.alignedObjective.id
                
                console.log(response)
                console.log("ini aligned")
                console.log(this.selectedData)
            } catch(error) {
                console.log(error)
            }

        },

    }, 
    async mounted() {
        try {
            const response = await this.$axios.get('/api/get-active-phase')

            this.activePhase = response.data.result

            if(this.getAuthRole != 'Manager') {
                const response2 = await this.$axios.get(`/api/objective/manager/${this.getAuthUserId}`)
                this.listManagerObjective = response2.data.result
                if(this.listManagerObjective.length > 0) {
                    var i;
                    for (i = this.listManagerObjective.length-1; i>=0; i-=1) {
                        if(this.listManagerObjective[i].approvalStatus.id !== 3) {
                            this.listManagerObjective.splice(i,1);
                        }
                    }
                }
            }
            console.log(this.activePhase)
        } catch(error) {
            console.warn(error)
        }
    },  

  }
</script>