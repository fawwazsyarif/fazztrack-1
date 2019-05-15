<template>
    <v-container>
    <template>
    <Modal
        ref="modal"
        :title="modalTitle"
        :text="modalText"
        :modalType="modalType">
    </Modal>
    </template>
    <v-form @submit.prevent="saveOKRDraft">
    <h3>Add/update objective</h3>
        <v-btn round color=secondary depressed>Add from recommended OKR</v-btn>
        <v-text-field
			:counter="255"
			label="Objective"
            :rules="reqRules"
            type="text"
			required
            v-model="description"
		></v-text-field>
    </v-form>
    <form>
    <h3>Add/update key results</h3>
		<v-list v-for="(kr, i) in listKR" :key="i">
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

        <!-- <v-btn round flat
        color=error
        depressed
        @click="handleCancel"
        >Cancel</v-btn>
        <v-btn round color=accent depressed
        @click="saveKRlist">Add key results</v-btn> -->
        <v-btn round flat
        color=error
        depressed
        @click="handleCancel"
        >Cancel</v-btn>
        <v-btn round
        color=accent
        @click="saveOKRApproval"
        depressed>Add OKR Recommendation</v-btn>
    </form>
    </v-container>
</template>

<script>
import Modal from '@/components/Modal'
import { mapGetters } from 'vuex';

export default {
    name : 'add-okr-recommendation',
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
        objective:{},
        listKR:[{description:'', progress:0}]
      }
    },
    methods: {
		handleCancel: function () {
            if(!confirm("Are you sure to cancel adding OKR?")) {
                this.$router.push("/");
            }
        },

        pushEmptyObjectToListKR(data) {
            if (this.listKR.length < 6) {
                this.listKR.push({ description: '', progress: 0 })
            }    
        },

        async saveOKRApproval() {
            const request = {
                userObjective: {id: this.getAuthUser.id},
                description: this.description,
                approvalStatus: {
                    id: 6
                }
            }
            const response = await this.$axios.post('/api/objective/add', request)
            
            console.log("hehe")
            console.log(response)
            console.log(response2)
            this.id_objective = response.data.result.id
            console.log(this.id_objective)

            const response2 = await this.$axios.post((`/api/objective/addKRlist/${this.id_objective}`), this.listKR)

            this.modalTitle = 'Success'
            this.modalText = 'Success saving OKR for approval'
            this.modalType = 'notification-success'
            this.$refs.modal.changeModalState();

            this.$router.push("/view-okr-recommendation");
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
            this.isLoading = true
            this.objective = {}
            

        } catch(error) {
            this.isLoading = false

            console.warn(error)
        } finally {
            this.isLoading = false

        }
    },
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
