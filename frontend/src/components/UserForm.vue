<template>
  <div class="text-xs-center">
    <v-dialog max-width="600px" v-model="formDisplayed">
      <v-card>

        <v-card-text class="text-sm-left font-weight-medium subheading">{{ title }}</v-card-text>
        <v-divider></v-divider>
        <v-card-text>

          <v-container grid-list-md>
            <v-layout wrap>
              <v-flex xs12>
                <v-text-field label="Name"
                v-model="user.name"
                required
                :rules="rules.required">
                </v-text-field>
              </v-flex>
              <v-flex xs12>
                <v-text-field label="NIK"
                v-model="user.nik"
                required
                :rules="rules.required">
                </v-text-field>
              </v-flex>
              <v-flex xs12>
                <v-text-field label="Username"
                v-model="user.username"
                required
                :rules="rules.required">
                </v-text-field>
              </v-flex>
              <v-flex xs12>
                <v-text-field label="Password"
                v-model="user.password"
                :rules="[rules.required,rules.passLength,rules.passAlphaNum]"
                :counter="12"
                :append-icon="show1 ? 'visibility' : 'visibility_off'"
                @click:append="show1 = !show1"
                :type="show1 ? 'text' : 'password'">
                </v-text-field>
              </v-flex>
              <v-flex xs12>
                <v-select
                  :items="roleList"
                  v-model="user.roles[0].id"
                  item-text="name"
                  item-value="id"
                  label=Role
                  required
                ></v-select>
              </v-flex>
              <v-flex xs12>
                <v-select
                  :items="deptList"
                  v-model="user.userDepartment.id"
                  @change="getManager"
                  item-text="name"
                  item-value="id"
                  label=Department
                  required
                ></v-select>
              </v-flex>
              <v-flex xs12>
                <v-select
                  :items="listMgr"
                  v-model="userMan.id"
                  item-text="name"
                  item-value="id"
                  label=Manager
                ></v-select>
              </v-flex>
            </v-layout>
          </v-container>

        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>

          <Modal
            ref="notif"
            title="Update user account"
            text="The user account has been updated!"
            modalType="notification-success">
          </Modal>
          <v-btn style="width:196px;margin-top: 5%" round color="#A1CD43" dark @click="submitFormUpdate">SAVE</v-btn>

        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import Modal from '../components/Modal';

export default {
  name: 'UserForm',
  components: {
    Modal,
  },
  model: {
    prop: {
      user: null,
    },
  },
  props: ['formType', 'user'],

  data() {
    return {
      formDisplayed: false,
      title: 'Update user account',
      show1: false,
      listMgr: [],
      userMan: {
        id: ''
      },
      rules: {
        required: v => !!v || 'Please fill the field',
        passAlphaNum: v => /^[a-zA-Z0-9]+$/.test(v) || 'Password must be alphanumeric',
        passLength: v => (v.length >= 8 && v.length <= 12) || 'Password must have 8 - 12 characters',
      },
    };
  },

  methods: {
    changeFormState() {
      this.formDisplayed = !this.formDisplayed;
    },

    async getManager() {
      console.log(this.department);
      try {
        const response2mng = await this.$axios.get(`/api/get-manager/${this.user.userDepartment.id}`);
        this.listMgr = response2mng.data.result;
        // console.log(this.deptMngList);
      } catch (error) {
        // console.warn(error);
      }
    },

    submitFormUpdate() {
      const set = new Set();
      set.add(this.roleList.filter(item => item.id === this.user.roles[0].id)[0])
      console.log(set)
      this.$axios.post(`/api/user/${this.user.id}/update`, {
        name: this.user.name,
        nik: this.user.nik,
        username: this.user.username,
        password: this.user.password,
        roles: set,
        userDepartment: {
          id: this.user.userDepartment.id
        },
        userManager: {
          id: this.user.userManager.id
        }

      })
        .then((response) => {
          console.log(response);
        })
        .catch((error) => {
          console.log(error);
        });
        this.changeFormState();
        this.$refs.notif.changeModalState();
        setTimeout(() => {
                this.$router.push({path: `/user/${this.user.id}`});
            }, 700);
        console.log('udah')
    },
  },

  async mounted() {
    try {
      const response = await this.$axios.get('/api/get-roles');
      const response2 = await this.$axios.get('/api/get-departments');
      console.log(this.user);
      this.roleList = response.data.result;
      this.deptList = response2.data.result;

      if (this.user.userManager!==null){
        this.userMan.id = this.user.userManager.id
      }

      console.log(response);
      console.log(response2);
    } catch (error) {
      console.warn(error);
    }
  },
};

</script>

<style scoped>

</style>
