<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
  <v-layout align-content-start column>
    <v-layout shrink align-content-center justify-end row>
      <v-text-field class="field"
                    v-model="search"
                    label="Search"
                    append-icon="search"
                    single-line
                    hide-details
                    width="20%"
      ></v-text-field>
      <v-spacer></v-spacer>
      <template>
        <v-layout row justify-center>
          <v-dialog v-model="dialog" persistent max-width="600px">
            <template v-slot:activator="{ on }">
              <v-btn color="primary" dark v-on="on">Add New Account</v-btn>
            </template>
            <v-card>
              <v-card-title>
                <h3>Add New Account</h3>
                <v-spacer></v-spacer>
                <v-btn small icon light @click="dialog=false">
                  <v-icon >close</v-icon>
                </v-btn>
              </v-card-title>
              <v-divider></v-divider>
              <v-card-text>
                <v-container grid-list-md>
                  <v-layout wrap>
                    <v-flex xs12>
                      <v-text-field label="Name" required></v-text-field>
                    </v-flex>
                    <v-flex xs12>
                      <v-text-field label="NIK" required></v-text-field>
                    </v-flex>
                    <v-flex xs12>
                      <v-text-field label="Username" required></v-text-field>
                    </v-flex>
                    <v-flex xs12>
                      <v-text-field label="Password*" type="password" required></v-text-field>
                    </v-flex>
                    <v-flex xs12>
                      <v-text-field label="Confirm Password*" type="password" required></v-text-field>
                    </v-flex>
                    <v-flex xs12>
                      <v-select
                        :items="['Admin', 'Manager', 'Employee']"
                        label=Role
                        required
                      ></v-select>
                    </v-flex>
                    <v-flex xs12>
                      <v-select
                        :items="['Finance', 'Marketing', 'People Operation']"
                        label=Department
                        required
                      ></v-select>
                    </v-flex>
                  </v-layout>
                </v-container>
              </v-card-text>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn style="width:196px;margin-top: 5%" round color="#A1CD43" dark v-on="on">SAVE</v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-layout>
      </template>
      <!-- <v-card-title>
          OKR EMPLOYEE ALL DEPARTMENT
      </v-card-title> -->
    </v-layout>

    <v-card>
      <v-data-table
        :items="userList"
        :search="search"
        :headers="headers">

        <template v-slot:items="props">
          <tr @click="props.expanded = !props.expanded">
            <td class="text-xs-left"> {{ props.item.name }} </td>
            <td class="text-xs-left">{{ props.item.createdDate }}</td>
            <td class="text-xs-left">{{ props.item.lastModifiedDate }}</td>
            <td class="text-xs-left">
              <div v-if="props.item.role === 'admin'">
                <v-chip color="orange" light>{{ props.item.role }}</v-chip>
              </div>
              <div v-if="props.item.role === 'manager'">
                <v-chip color="red" dark>{{ props.item.role }}</v-chip>
              </div>
              <div v-if="props.item.role === 'employee'">
                <v-chip color="blue" dark>{{ props.item.role }}</v-chip>
              </div>
              </td>
            <td class="text-xs-center"><v-btn flat class="btn secondary--text">View Detail</v-btn></td>
          </tr>
        </template>

        <template v-slot:expand="props">
          <v-card flat>
            <v-card-text>Peek-a-boo!</v-card-text>
          </v-card>
        </template>

        <v-alert v-slot:no-results :value="true" color="error" icon="warning">
          Your search for "{{ search }}" found no results.
        </v-alert>
      </v-data-table>

    </v-card>
  </v-layout>
</template>
<script>
export default {
  name: 'profileList',
  data() {
    return {
      expand: false,
      dialog: false,
      search: '',
      userList: [],
      headers: [

        {
          // value is used for searching
          text: 'Name',
          align: 'center',
          sortable: true,
          value: 'name',
        },
        { text: 'Created',
          align: 'center',
          value: 'createdDate',
        },
        { text: 'Updated',
          align: 'center',
          value: 'lastModifiedDate',
        },
        { text: 'Role',
          align: 'center',
          value: 'role',
        },
        { text: '',
          align: 'center' },
      ],
    };
  },
  async mounted() {
    try {
      // TODO pakein parameter sesuai id departemennya axios.get('/api/getcontacts', {params: {city: this.searchvalue}})
      const response = await this.$axios.get('/user');
      this.userList = response.data.result;
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
</style>
