<template>
  <v-layout>
    <v-flex>
      <v-card class="primary--text">
        <v-card-title primary-title>
          <div class="ml-4">
            <h2>{{this.user.name}}</h2>
          </div>
          <v-spacer></v-spacer>
          <div v-if="this.user.roles[0].name === 'Admin'">
          <v-chip color="warning">{{ this.user.roles[0].name }}</v-chip>
          </div>
          <div v-else-if="this.user.roles[0].name === 'Manager'">
            <v-chip color="success">{{ this.user.roles[0].name }}</v-chip>
          </div>
          <div v-else-if="this.user.roles[0].name === 'Employee'">
            <v-chip color="primary">{{ this.user.roles[0].name }}</v-chip>
          </div>
        </v-card-title>
        <v-divider></v-divider>
        <v-flex align-self-start xs1 sm8 offset-xs0>
        <v-card-text>
          <v-layout align-content-start column ml-4>
            <v-flex xs-12 class="text-xs-left">
              <p>Name</p>
              <p class="font-weight-bold">{{user.name}}</p>
            </v-flex>
            <v-flex xs-12 class="text-xs-left">
              <p>Username</p>
              <p class="font-weight-bold">{{user.username}}</p>
            </v-flex>
            <v-flex xs-12 class="text-xs-left">
              <p>NIK</p>
              <p class="font-weight-bold">{{user.nik}}</p>
            </v-flex>
            <v-flex xs-12 class="text-xs-left">
              <p>Role</p>
              <p class="font-weight-bold">{{user.roles[0].name}}</p>
            </v-flex>
            <v-flex xs-12 class="text-xs-left">
              <p>Status</p>
              <div v-if="this.user.isActive==1">
                <p class="font-weight-bold">Active</p>
              </div>
              <div v-else>
                <p class="font-weight-bold">Inactive</p>
              </div>
            </v-flex>
          </v-layout>
        </v-card-text>
        </v-flex>
        <v-divider></v-divider>
        <v-flex align-self-center xs12 sm1 offset-sm4>
        <v-card-actions>
          <ModalEditUser
              ref="ModalEditUser"
              @updateTable="updateTable">
          </ModalEditUser>
          <Modal
            ref="notif"
            title="Update user account"
            text="The user account has been updated!"
            modalType="notification-success">
          </Modal>
          <v-btn class="btn px-5 secondary--text" flat @click="changeFormState()">Edit</v-btn>
          <Modal
            ref="modal"
            title="Change User Status"
            text="Are you sure want to change this user's status?"
            modalType="confirmation-reverse"
            greenButtonText="Change"
            redButtonText="Cancel"
            @onModalGreenSubmit="onModalGreenSubmit"
            @onModalRedSubmit="onModalRedSubmit">
          </Modal>
          <Modal
            ref="notifDel"
            title="Change User Status"
            text="The user's status has been changed!"
            modalType="notification-success"
          >
          </Modal>
          <v-btn color="success" @click="changeConfirmation()" class="mx-3">Change Status</v-btn>
        </v-card-actions>
        </v-flex>
      </v-card>
    </v-flex>
  </v-layout>
</template>

<script>
import Modal from '../components/Modal';
import ModalEditUser from '@/components/ModalEditUser';

export default {
  name: 'userDetail',
  components: {
    Modal, ModalEditUser,
  },

  data() {
    return {
      user: null,
      id: 0,
    };
  },

  created() {
    this.id = this.$route.params.id;
  },

  async mounted() {
    console.log('test');
    try {
      // TODO pakein parameter sesuai id departemennya axios.get('/api/getcontacts', {params: {city: this.searchvalue}})

      const response = await this.$axios.get(`/api/user/${this.id}`);
      this.user = response.data.result;
      console.log(this.user);
    } catch (error) {
      console.warn(error);
    }
  },

  methods: {
    changeFormState() {
      this.$refs.ModalEditUser.changeModalState();
      this.$refs.ModalEditUser.fetch(this.user);
    },
    changeConfirmation() {
      this.$refs.modal.changeModalState();
    },
    onModalRedSubmit() {
      this.$refs.modal.changeModalState();
    },
    onModalGreenSubmit() {
      this.$axios.get(`/api/user/${this.user.id}/change-status`, null,
        
      )
        .then((response) => {
          console.log(response);
        })
        .catch((error) => {
          console.log(error);
        });
      this.$refs.modal.changeModalState();
      this.$refs.notifDel.changeModalState();
    },
    async updateTable() {
      try {
        console.log("masuk ut1")

        setTimeout(() => {
          this.$router.go("/");
        }, 700);

        console.log("masuk ut")
        console.log(this.userList);

      } catch (error) {
        console.warn(error);
      }
    }
  },

};
</script>

<style>
  .btn {
    background: linear-gradient(360deg, #EFF3F6 0%, #FFFFFF 100%);
    border: 1px solid #DBE0E4;
    box-sizing: border-box;
    border-radius: 4px;
  }


</style>