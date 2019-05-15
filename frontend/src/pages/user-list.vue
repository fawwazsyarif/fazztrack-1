<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
  <v-layout align-content-start column>
    <v-layout shrink align-content-center justify-end row pb-5>
      <v-flex xs4>
          <v-text-field
              class="field"
              v-model="search"
              append-icon="search"
              label="Search user's name"
              solo
              flat
          ></v-text-field>
          </v-flex>
      <v-flex xs4>
          <h4>User List</h4>
      </v-flex>
      <v-spacer></v-spacer>
      <template>
        <div class="text-xs-center">
          <v-btn color="accent" class="white--text" dark @click="displayAddUser">
          <feather type="edit-3" stroke-width=1 class="pr-3"></feather>
          Add new user
          </v-btn>
        </div>
      </template>
    </v-layout>

    <v-card>
      <v-data-table
        :items="userList"
        :search="search"
        :headers="headers">

        <template v-slot:items="props">
          <tr>
            <td class="text-xs-left"> {{ props.item.name }} </td>
           <td class="text-xs-left">
             <!--<p>{{checkManager(props.item.userManager)}}</p> -->
              <div v-if="checkManager(props.item.userManager)===false">
                {{props.item.userManager.name}}
              </div>
              <div v-else>
                -
              </div>
            </td>
           <td class="text-xs-left">{{ props.item.userDepartment.name }}</td>
           <td class="text-xs-left">
             <div v-if="checkRole(props.item.roles)">
             -
             </div>
             <div v-else>
               <div v-if="props.item.roles[0].name === 'Admin'">
                 <v-chip color="orange" light>{{ props.item.roles[0].name }}</v-chip>
               </div>
               <div v-else-if="props.item.roles[0].name === 'Manager'">
                 <v-chip color="red" dark>{{ props.item.roles[0].name }}</v-chip>
               </div>
               <div v-else-if="props.item.roles[0].name === 'Employee'">
                 <v-chip color="blue" dark>{{ props.item.roles[0].name }}</v-chip>
               </div>
             </div>
              </td>
              <td class="text-xs-center"><v-btn flat class="btn secondary--text" :to="{name: 'user-detail', params: {id:props.item.id}}">View Detail</v-btn>
            </td>
          </tr>
        </template>

        <v-alert v-slot:no-results :value="true" color="error" icon="warning">
          Your search for "{{ search }}" found no results.
        </v-alert>
      </v-data-table>
    </v-card>
    <ModalAddUser
        ref="ModalAddUser"
        @updateTable="updateTable">
    </ModalAddUser>
  </v-layout>
</template>

<script>
import ModalAddUser from '@/components/ModalAddUser'

export default {
  name: 'usersList',

  data() {
    return {
      search: '',
      userList: [],
      headers: [

        {
          // value is used for searching
          text: 'Name',
          align: 'left',
          sortable: true,
          value: 'name',
        },
        {
          text: 'Manager',
          align: 'left',
          sortable: true,
          value: 'userManager.name',
        },
        { text: 'Department',
          align: 'left',
          sortable: true,
          value: 'userDepartment.name',
        },
        { text: 'Role',
          align: 'left',
          sortable: true,
          value: 'roles.name',
        },

        { text: '',
          align: 'left' },
      ],
    };
  },

  computed:{

  },

  components: {
    ModalAddUser,
  },

  methods: {
    checkManager(user){
      return !user;
    },

    checkRole(list){
      return list.length === 0;
    },

    displayAddUser() {
      this.$refs.ModalAddUser.changeModalState();
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

  async mounted() {
    try {
      const responseuser = await this.$axios.get('/api/user');
      this.userList = responseuser.data.result;

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
