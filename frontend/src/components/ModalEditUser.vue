<template>
    <div class="text-xs-center">
        <!-- <v-dialog v-model="modalDisplayed" ref="form" max-width="600px"> -->
        <v-dialog
        v-model="modalDisplayed"
        max-width="700px"
        scrollable
        >
        <v-card>
            <v-card-text class="text-sm-left font-weight-medium subheading">Edit user account</v-card-text>
            <v-divider></v-divider>
            <v-card-text>
            <v-container grid-list-md>
                <v-layout wrap>
                <v-flex xs12>
                    <v-text-field label="Name" 
                    v-model="name" 
                    required
                    :rules="[rules.required]">
                    </v-text-field>
                </v-flex>
                <v-flex xs12>
                    <v-text-field label="NIK"
                    v-model="nik"
                    required
                    :rules="[rules.required]"></v-text-field>
                </v-flex>
                <v-flex xs12>
                    <v-text-field label="Username"
                    v-model="username"
                    required
                    :rules="[rules.required]"></v-text-field>
                </v-flex>
                <v-flex xs12>
                    <v-text-field label="Password*"
                    v-model="password"
                    :rules="[rules.required,rules.passLength,rules.passAlphaNum]"
                    :counter="12"
                    :append-icon="show1 ? 'visibility' : 'visibility_off'"
                    @click:append="show1 = !show1"
                    :type="show1 ? 'text' : 'password'">
                    </v-text-field>
                </v-flex>
                <v-flex xs12>
                    <v-text-field label="Confirm Password*"
                    v-model="passConfirm"
                    :type="show2 ? 'text' : 'password'"
                    required
                    :append-icon="show2 ? 'visibility' : 'visibility_off'"
                    @click:append="show2 = !show2"
                    :rules="[rules.required,passMatch]">
                    </v-text-field>
                </v-flex>
                <v-flex xs12>
                    <v-select
                    :items="roleList"
                    v-model="role"
                    item-text="name"
                    item-value="id"
                    label=Role
                    required
                    ></v-select>
                </v-flex>
                <v-flex xs12>
                    <v-select
                    :items="isActiveL"
                    v-model="isActive"
                    item-text="name"
                    item-value="id"
                    label=Status
                    required
                    ></v-select>
                </v-flex>
                <v-flex xs12>
                    <v-select
                    :items="deptList"
                    v-model="department.id"
                    v-on:change="getManager(department.id)"
                    item-text="name"
                    item-value="id"
                    label=Department
                    required
                    ></v-select>
                </v-flex>
                <v-flex xs12>
                    <v-select
                        :items="deptMngList"
                        v-model="manager.id"
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
                ref="modal"
                title="Add new account"
                text="The user has been saved!"
                modalType="notification-success">
            </Modal>
            <Modal
                ref="modal2"
                title="Add new account"
                text="Input Invalid!"
                modalType="notification-error">
            </Modal>
            <v-btn style="width:196px;margin-top: 5%" round color="#A1CD43" dark @click="submitFormAdd">SAVE</v-btn>
            </v-card-actions>
        </v-card>
        </v-dialog>
    </div>
</template>

<script>
import Modal from '../components/Modal';

export default {
    name: 'ModalEditUser',

    data() {
        return {
            expand: false,
            search: '',
            modalDisplayed: false,
            item: null,
            show1: false,
            show2: false,
            userList: [],
            deptList: [],
            roleList: [],
            deptMngList: [],
            user: {},
            name: 'jo',
            nik: '',
            username: '',
            password: '',
            role: '',
            isActiveL: [
                {id: 0, name: 'Inactive'},
                {id: 1, name: 'Active'},
            ],
            isActive: {id: 1, name: 'Active'},
            department: {
                id : '',
            },
            manager: {
                id : '',
            },
            rules: {
                required: v => !!v || 'Please fill the field',
                passAlphaNum: v => /^[a-zA-Z0-9]+$/.test(v) || 'Password must be alphanumeric',
                passLength: v => (v.length >= 8 && v.length <= 12) || 'Password must have 8 - 12 characters',
            
            },
            passConfirm: '',
        };
    },

    components:{
        Modal,
    },

    computed: {
        passMatch() {
            return [
                () => (this.password === this.passConfirm) || 'Password must match',
            ];
        },
    },

    methods: {
        async fetch(user) {
            try{
                const response = await this.$axios.get('/api/objective/' + user.id)
                this.id = user.id
                this.user = user
                this.name = user.name
                this.nik = user.nik
                this.username = user.username
                this.password = user.password
                this.passConfirm = user.password
                this.role = user.roles[0].id
                this.isActive = user.isActive
                this.getManager(user.userDepartment.id)
                this.manager.id = user.userManager.id
                this.department.id = user.userDepartment.id

                console.log("haha")
                console.log(this.role)
                console.log(this.name)
            } catch(error) {
                console.log(error)
            }

        },

        changeModalState() {
            this.modalDisplayed = !this.modalDisplayed;
        },

        async getManager(department) {
            console.log(this.department);
            try {
                const response2mng = await this.$axios.get(`/api/get-manager/${department}`);
                this.deptMngList = response2mng.data.result;
                console.log(this.deptMngList);
            } catch (error) {
                console.warn(error);
            }
        },

        async submitFormAdd() {
            if(this.password.length >= 8 && this.passConfirm === this.password){
                const set = new Set();
                set.add(this.roleList.filter(item => item.id === this.role)[0])
                const response = await this.$axios.post('/api/user/add', {
                    id: this.id,
                    name: this.name,
                    nik: this.nik,
                    username: this.username,
                    password: this.password,
                    roles: set,
                    isActive: this.isActive.id,
                    userDepartment: {
                    id: this.department.id
                    },
                    userManager: {
                    id: this.manager.id
                    }
                })

                this.$refs.modal.changeModalState();
                this.$emit('updateTable')
                this.changeModalState()
            } else{
                this.$refs.modal2.changeModalState();
            }
        },
    },

    async mounted() {
        try {
            const response = await this.$axios.get('/api/get-roles');
            const response2 = await this.$axios.get('/api/get-departments');
            const responseuser = await this.$axios.get('/api/user');

            this.roleList = response.data.result;
            this.deptList = response2.data.result;
            this.userList = responseuser.data.result;

            console.log(this.roleList);
            console.log(this.deptList);
            console.log(this.userList);
        } catch (error) {
            console.warn(error);
        }
    },
};
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
</style>