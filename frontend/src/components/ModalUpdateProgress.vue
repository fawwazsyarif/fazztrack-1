<template>

    <v-layout>
        <v-dialog
        v-model="modalDisplayed"
        width="500">
        <v-card class="primary--text">
            <template v-if="krData.objectiveKR.approvalStatus.id === 3">
                <v-card-title>
                    <span class="title"> Update Key Result Progress</span>
                </v-card-title>
                <v-card-text>
                    <v-list two-line>
                        <template>
                            <v-list-tile>
                                <v-list-tile-content>
                                    <v-list-tile-title class="subheading primary--text">Objective:</v-list-tile-title>
                                    <v-list-tile-sub-title class="subheading">{{krData.objectiveKR.description}}</v-list-tile-sub-title>
                                </v-list-tile-content>
                            </v-list-tile>
                            <v-list-tile>
                                <v-list-tile-content>
                                    <v-list-tile-title class="subheading primary--text">Key result:</v-list-tile-title>
                                    <v-list-tile-sub-title class="subheading">{{krData.description}}</v-list-tile-sub-title>
                                </v-list-tile-content>
                            </v-list-tile>
                            <v-slider
                                label="Progress"
                                v-model="krData.progress"
                                :value="krData.progress"
                                always-dirty
                                min="0"
                                max="100"
                                step="5"
                                class="pt-4"
                                thumb-label="always">
                            </v-slider>
                        </template>
                    </v-list>
                </v-card-text>
                <v-card-actions>
                    <v-btn color="error" flat  @click="handleCancel">Close</v-btn>
                    <v-btn color="success" flat @click="updateProgress">Update progress</v-btn>
                </v-card-actions>
            </template>
            <template v-else>
                <v-card-title>
                    <span class="title"> Update Key Result Progress</span>
                </v-card-title>
                <v-card-text>
                    <v-img :src="require('@/assets/cancel.svg')" contain aspect-ratio="2"></v-img>
                    <p class="subheading mt-4"> Oopps! sorry :(</p>
                    <p class="subheading">You can't edit progress of this key result because your OKR is still <span class="font-weight-bold">under review</span> or <span class="font-weight-bold">has been rejected</span></p>
                </v-card-text>
            </template>
        </v-card>
    </v-dialog>
    </v-layout>
</template>
<script>
export default {
    name : 'ModalUpdateProgress',
    data () {
        return {
            modalDisplayed : false,
            krData: {
                description: '',
                document: '',
                id: '',
                lastModifiedDate: '',
                objectiveKR: {},
                progress: 0,
            },

        }
    
    },
    methods: {
        changeModalState() {
            this.modalDisplayed = !this.modalDisplayed;
        },

        handleCancel: function () {
            this.changeModalState()
        },
        async fetch(id) {
            try{
                const response = await this.$axios.get('/api/keyresult/' + id)
                this.krData = response.data.result
            } catch(error) {
                console.log(error)
            }
        },
        async updateProgress(){
            const newKRProgress = {
                description: this.krData.description,
                progress: this.krData.progress,
                document: "no link"
            }
            const response = await this.$axios.post((`/api/objective/update-kr-progress/`+this.krData.id), newKRProgress)
            console.log(response)
            this.modalTitle = 'Success'
            this.modalText = 'Success for updating key result progress'
            this.modalType = 'notification-success'
            
            this.$emit('updateTable')
            this.changeModalState()

        }
    }
}


</script>
<style>
</style>
