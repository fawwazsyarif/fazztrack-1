<template>
    <v-container>
        <Modal
        ref="modal"
        :title="modalTitle"
        :text="modalText"
        :modalType="modalType"
        >
      </Modal>
    <template>
    </template>
    <v-form @submit.prevent="saveOKRDraft">
    <h3>Update objective</h3>
        <v-text-field
			:counter="255"
			label="Objective"
            :rules="reqRules"
            type="text"
			required
            v-model="okrData.description"
		></v-text-field>
        <!-- <v-btn round flat
        color=error
        depressed
        @click="handleCancel"
        >Cancel</v-btn>
        <v-btn round
        color=accent
        @click="saveOKRApproval"
        depressed>Update objective</v-btn> -->
    </v-form>
    <form>
    <h3>Update key results</h3>

    <v-list v-for="(kr, i) in okrData.listKR" :key="i" >
        <v-text-field
			:counter="255"
			label="key result"
            :rules="reqRules"
            type="text"
			required
            v-model="kr.description"
		></v-text-field>
    </v-list>
    <v-btn flat @click="pushEmptyObjectToListKR">
        <feather type="plus-circle" stroke-width=1 class="accent-text"></feather>
    </v-btn>

        <v-btn round flat
        color=error
        depressed
        @click="handleCancel"
        >Cancel</v-btn>
        <v-btn round color=accent depressed
        @click="saveOKRApproval">Update OKR</v-btn>
    </form>
    </v-container>
</template>

<script>
import Modal from '@/components/Modal'

export default {
    name : 'updateOKR',
    components: {
        Modal,
    },
    data () {
      return {
        reqRules: [v => !!v || "The input is required"],
        description:'',
        id_objective:'',
        modalTitle: '',
        modalText: '',
        modalType: '',
        okrData: {
            description:'',
            listKR: [],
        },
      }
    },
    methods: {
        pushEmptyObjectToListKR(data) {
            if (this.okrData.listKR.length < 6) {
                this.okrData.listKR.push({ description: '', progress: 0 })
            }    
        },

		handleCancel: function () {
            if(!confirm("Are you sure to cancel adding OKR?")) {
                this.$router.push("/");
            }
        },

        async saveOKRApproval() {
            const request = {
                description: this.okrData.description,
                approvalStatus: {
                    id: 1
                }
            }
            const response = await this.$axios.post(`/api/objective/update/${this.okrData.id}`, request)
            console.log("hehe")
            console.log(response)
            this.id_objective = response.data.result.id
            console.log(this.id_objective)

            const request2 = this.okrData.listKR
            console.log(request2)
            const response2 = await this.$axios.post(`/api/objective/updateKRlist/${this.okrData.id}`, request2)
            console.log(response2)

            this.modalTitle = 'Success'
            this.modalText = 'Success saving OKR for approval'
            this.modalType = 'notification-success'
            this.$refs.modal.changeModalState();

            this.$router.push("/");
        },
    },

    async mounted() {
        try {
        this.isLoading = true
        const response = await this.$axios.get(`/api/objective/${this.$route.params.id}`)
        this.okrData = response.data.result
        console.log("hehe")
        console.log(this.okrData)
        // buat dapetin rata2 progress dari setiap KR dalam 1 Objective
        // this.okrProgress = calculateProgress(this.okrDetail.listKR)
        } catch(error) {
        this.isLoading = false

        } finally {
        this.isLoading = false
        }
    }
}
</script>

<style>
form {
    padding: 2em;
    margin: auto;
	width: 98%;
	max-width: 500px;
	background: white;
	box-shadow: 0 3px 6px rgba(0,0,0,.1);
}

.v-btn {
    margin-top: 1em;
}
.btn {
    background: linear-gradient(360deg, #EFF3F6 0%, #FFFFFF 100%);
    border: 1px solid #DBE0E4;
    box-sizing: border-box;
    border-radius: 4px;
}

</style>
