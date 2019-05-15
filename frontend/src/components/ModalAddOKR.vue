<template>
   <v-layout row justify-center>
        <v-dialog
        v-model="modalDisplayed"
        max-width="700px"
        persistent
        scrollable
        >
            <v-card class="primary--text">
                <template v-if="activePhase.name === 'execution'">
                    <v-card-title class="title">Create New OKR</v-card-title>
                    <v-card-text style="height: 360px">
                    <v-form  v-model="valid" ref="form" lazy-validation>
                        <v-flex xs-12 class="mx-4">
                            <p class="subheading font-weight-bold text-xs-left my-2"> Objective </p>
                            <v-layout column>
                                <v-flex xs12>
                                    <v-select
                                        v-model="alignedObjective"
                                        :items="listManagerObjective"
                                        hint="aligned objective"
                                        item-text="description"
                                        item-value="id"
                                        label="Select you manager objective to be aligned (optional)"
                                        persistent-hint
                                        single-line
                                    ></v-select>
                                </v-flex>
                                <v-layout v-if="alignedObjective">
                                    <v-flex xs8></v-flex>
                                    <v-flex xs4 class="text-xs-right">
                                        <v-btn color="red" small @click="clearSelection" flat class="mr-0 pt-0">clear selection</v-btn>
                                    </v-flex>
                                </v-layout>
                            </v-layout>
                           
                        </v-flex>
                        <v-flex xs-12 class="mx-4 mt-2">
                            <v-text-field
                                :counter="255"
                                label="Objective"
                                :rules="reqRules"
                                type="text"
                                required
                                v-model="description"
                            ></v-text-field>
                        </v-flex>
                    
                        <v-flex xs-12 class="mx-4">
                            <p class="subheading font-weight-bold text-xs-left my-2"> Key Result</p>
                            <v-list v-for="(kr, i) in listKR" :key="i" >
                                <v-layout align-center >
                                    <v-flex xs1>
                                        {{i + 1}}.
                                    </v-flex>
                                    <v-flex xs10>
                                        <v-text-field
                                            :counter="255"
                                            label="Key result"
                                            :rules="reqRules"
                                            type="text"
                                            required
                                            v-model="kr.description"
                                            append-outericon="delete"
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
                        <v-icon v-if="listKR.length < 6" size="30" @click="pushEmptyObjectToListKR">add_circle</v-icon>
                       
                    </v-form>
                    </v-card-text>
                     <v-divider></v-divider>

                    <v-card-actions>
                        <v-btn color="error" flat  @click="handleCancel">Close</v-btn>
                        <v-spacer></v-spacer>
                        <V-btn color="accent" flat @click="saveOKRDraft">Save as draft</V-btn>
                        <v-btn color="success" flat @click="saveOKRApproval">Save OKR</v-btn>
                    </v-card-actions>
                </template>
                <template v-else>
                    <v-card-title>
                    <span class="title"> Create new OKR</span>
                    </v-card-title>
                    <v-divider></v-divider>
                    <v-card-text>
                        <v-img :src="require('@/assets/cancel.svg')" contain aspect-ratio="2" class="mt-2"></v-img>
                        <p class="subheading mt-4"> Oopps! sorry :(</p>
                        <p class="subheading">You can't create new OKR because you are currently on  <span class="font-weight-bold">evaluation phase</span></p>
                    </v-card-text>
                    <v-btn color="error" flat  @click="changeModalState">Close</v-btn>
                </template>
            </v-card>
        </v-dialog>
    </v-layout>
</template>

<script>
import { mapGetters } from 'vuex';

export default {
    name : 'ModalAddOKR',

    data () {
      return {
        valid: true,
        reqRules: [
            v => !!v || "This input is required"],
        modalDisplayed: false,
        description:'',
        id_objective:'',
        objective:{},
        activePhase: {},
        listKR:[{description:'', progress:0}],
        listManagerObjective: [],
        alignedObjective: '',
        request: {}
      }
    },
    computed: {
        ...mapGetters([
            'getAuthUser',
            'getAuthRole',
            'getAuthName',
            'getAuthUserDepartment',
            'getAuthUserId',

        ]),
        imgUrl() {
            return `https://ui-avatars.com/api/?background=48ACF0&rounded=true&size=36&color=ffffff&bold=true&name=${this.getAuthName}`
        },
    },

    methods: {
        changeModalState() {
            this.modalDisplayed = !this.modalDisplayed;
        },

		handleCancel: function () {
            this.$refs.form.reset()
            this.listKR.splice(1,this.listKR.length)
            this.modalDisplayed = !this.modalDisplayed;
        },

        pushEmptyObjectToListKR(data) {
            if (this.listKR.length < 6) {
                this.listKR.push({ description: '', progress: 0 })
            }
        },

        removeObjectFromListKR(index){
            this.listKR.splice(index, 1);
        },

        async saveOKRDraft() {

            if(this.alignedObjective) {
                this.request = {
                    userObjective: {id: this.getAuthUser.id},
                    description: this.description,
                    approvalStatus: {
                        id: 5
                    },
                    alignedObjective: {
                        id: this.alignedObjective
                    }
                }

            } else {
                this.request = {
                    userObjective: {id: this.getAuthUser.id},
                    description: this.description,
                    approvalStatus: {
                        id: 5
                    }
                }
            }
            
            if (this.$refs.form.validate()) {
                const response = await this.$axios.post('/api/objective/add', this.request)
                this.objective = this.request
                this.id_objective = response.data.result.id
                console.log(response)

                const response2 = await this.$axios.post((`/api/objective/addKRlist/${this.id_objective}`), this.listKR)
                this.$emit('updateTable')
                this.$refs.form.reset()
                this.listKR.splice(1,this.listKR.length)
                this.changeModalState()
            }
            
        },

        async saveOKRApproval() {
            if(this.alignedObjective) {
                this.request = {
                    userObjective: {id: this.getAuthUser.id},
                    description: this.description,
                    approvalStatus: {
                        id: 1
                    },
                    alignedObjective: {
                        id: this.alignedObjective
                    }
                }
            } else {
                this.request = {
                    userObjective: {id: this.getAuthUser.id},
                    description: this.description,
                    approvalStatus: {
                        id: 1
                    }
                }
            }

            if(this.getAuthRole === 'Manager') {
                this.request.approvalStatus.id = 2
            }

            if (this.$refs.form.validate()) {
                const response = await this.$axios.post('/api/objective/add', this.request)
                this.id_objective = response.data.result.id
                const response2 = await this.$axios.post((`/api/objective/addKRlist/${this.id_objective}`), this.listKR)
                console.log(response)
                this.$emit('updateTable')
                this.listKR.splice(1,this.listKR.length)
                this.$refs.form.reset()
                this.changeModalState();
            }
            
        },
        async clearSelection() {
            this.alignedObjective = ''
        }

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
        } catch(error) {
            this.isLoading = false
            console.warn(error)
        } finally {
            this.isLoading = false

        }
    },
}
</script>
