<template>
    <v-container>
    <template v-if="isLoading">
        <p>loading ...</p>
    </template>
        <Modal
        ref="modal"
        :title="modalTitle"
        :text="modalText"
        :modalType="modalType">
        >
        </Modal>
    <form>
    <h3>Update key result progress</h3>
    <h3 class="body-2 pt-2">Objective</h3>
    <h3 class="body-2 font-weight-regular">{{krData.objectiveKR.description}}</h3>

    <h3 class="body-2 pt-2">Key Result</h3>
    <h3 class="body-2 font-weight-regular">{{krData.description}}</h3>


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

    <h3 class="body-1 pt-2">Attach related file to this progress:</h3>
    <v-layout column>
        <!-- <v-flex xs-15>
        <label>File
        <input type="file" id="file" ref="file" v-on:change="handleFileUpload()"/>
      </label>
      <button v-on:click="submitFile()">Submit</button>
        </v-flex> -->
        <v-flex xs-12>
            <v-btn round color="success" depressed @click="updateProgress">Save progress</v-btn>
        </v-flex>
    </v-layout>
    </form>
    </v-container>
</template>

<script>
import Modal from '@/components/Modal'

export default {
    name : 'updateKRprogress',
    components: {
        Modal,
    },
    data () {
        return{
            krData: {
                description: '',
                document: '',
                id: '',
                lastModifiedDate: '',
                objectiveKR: {},
                progress: 0,
            },
            isLoading: true,
            expand: false,
            search: '',
            modalTitle: '',
            modalText: '',
            modalType: '',
            file : '',
        }
    },

    methods: {
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
            this.$refs.modal.changeModalState();
            setTimeout(() => {
                this.$router.push("/");
            }, 1000);
        },
      //   submitFile(){
      //   /*
      //           Initialize the form data
      //       */
      //       let formData = new FormData();

      //       /*
      //           Add the form data we need to submit
      //       */
      //       formData.append('file', this.file);

      //   /*
      //     Make the request to the POST /single-file URL
      //   */
      //       axios.post( '/single-file',
      //           formData,
      //           {
      //           headers: {
      //               'Content-Type': 'multipart/form-data'
      //           }
      //         }
      //       ).then(function(){
      //     console.log('SUCCESS!!');
      //   })
      //   .catch(function(){
      //     console.log('FAILURE!!');
      //   });
      // },

      // /*
      //   Handles a change on the file upload
      // */
      // handleFileUpload(){
      //   this.file = this.$refs.file.files[0];
      // }
    },


    async mounted() {
        try {
        this.isLoading = true
        const response = await this.$axios.get(`/api/keyresult/${this.$route.params.id}`)
        // const okrData = await this.$axios.get(`/api/objective/${this.$route.params.id}`)
        this.krData = response.data.result
        console.log("hehe")
        console.log(this.krData)
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
