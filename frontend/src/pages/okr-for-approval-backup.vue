<template>
    <v-layout align-content-start column>
        <v-layout shrink align-content-center justify-end row pb-5>
            <v-flex xs4>
                <!-- <v-card> -->
                <v-text-field 
                    class="field"
                    v-model="search"
                    append-icon="search"
                    label="Search objective or employee's name"
                    solo
                    flat
                ></v-text-field>
                <!-- </v-card> -->
            </v-flex>
            <v-flex xs4>
                <h4 v-if="getAuthRole === 'Admin' ">OKR Employee All Department</h4>
                <h4 v-else-if=" getAuthRole === 'Manager' || getAuthRole === 'Employee' ">OKR Employee {{getAuthUserDepartment}} Department</h4>
            </v-flex>
            <v-flex xs4>
                <!-- <h4>OKR Employee All Department</h4> -->
            </v-flex>
        </v-layout>
        
        <v-card>
            <v-data-table
            :items="okrData"
            :search="search"
            :headers="headers">
                <!-- <v-list v-for="(nav,i) in navigation" :key="i"> -->
                <template v-slot:items="props">
                    <tr @click="props.expanded = !props.expanded">
                        <td class="text-xs-left"> 
                            <img src="@/assets/expand.svg" :class="{ 'expanded': props.expanded }"> 
                            {{ props.item.description }} 
                        </td>
                        <td class="text-xs-left">{{ props.item.userObjective.name }}</td>
                        <!-- TODO: benerin v-chipnya -->
                        <td class="text-xs-center">
                            {{ calculateProgress(props.item.listKR) }}
                        </td>
                        <td class="text-xs-center">
                            <Chip
                                :idStatus="props.item.approvalStatus.id"
                                :status="props.item.approvalStatus.status">
                            </Chip>
                        </td>
                        <td class="text-xs-center"> {{ props.item.lastModifiedDate | formatDate }}</td>
                        <td class="text-xs-center"><v-btn flat class="btn secondary--text" :to="{ name: 'detail-okr', params: { id: props.item.id } }">View Detail</v-btn></td>
                    </tr>
                </template>

                <template v-slot:expand="props">
                    <v-card flat>
                        <v-data-table
                        :items="props.item.listKR"
                        hide-headers
                        hide-actions
                        >
                            <!-- <v-list v-for="(nav,i) in navigation" :key="i"> -->
                            <template v-slot:items="props">
                                <v-layout @click="props.expanded = !props.expanded" class="px-4" row align-center justify-start wrap>
                                    <v-flex xs5 pl-4>
                                        <p class="mb-0 text-xs-left"> {{ props.item.description }} </p>
                                    </v-flex>
                                    <v-flex xs3>
                                        <v-progress-linear 
                                            background-color="primary lighten-1"
                                            color="primary"
                                            height="10"
                                            xs3
                                            class ="progress"
                                            :value="props.item.progress">
                                        </v-progress-linear>
                                    </v-flex>
                                    <v-flex xs1>
                                        <p class="mb-0 text-xs-center"> {{ props.item.progress }}% </p>
                                    </v-flex>
                                    <v-flex xs3>
                                        <p class="mb-0 text-xs-center accent--text"> <feather type="download" size=16 height=50 stroke-width=1 class=" mb-0 pr-1"></feather> Download document </p>
                                    </v-flex>

                                </v-layout>
                            </template>
                           
                            </v-data-table>
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
import calculateProgress from '@/functions/calculateProgress'
import Chip from '@/components/Chip'
import { mapGetters } from 'vuex'

export default {
    name : 'okrForApproval',

    data () {
        return{
            expand: false,
            search: '',
            okrData: [],
            krData: [],
            headers: [
            
            {
            //value is used for searching
            text: 'Objective Name',
            align: 'center',
            sortable: true,
            value: 'description',
            
            },
            { text: 'Owner', 
            align: 'center',
            value: 'userObjective.name' },
            { text: 'Overall Progress',
            align: 'center',
            sortable: true,
            },
            { text: 'Approval Status', 
            value: 'approvalStatus.status',
            align: 'center'
            },
            { text: 'Date',
            align: 'center',
            value: 'lastModifiedDate'
            },
            { text:'',
            align: 'center',
            sortable: true}

        ],
        }
    },
     components: {
        Chip,
    },

    computed: {
        ...mapGetters([
            'getAuthUser',
            'getAuthRole',
            'getAuthName',
            'getAuthUserIdDepartment',
            'getAuthUserDepartment'
        ])
    },

    //fungsi buat nge retrieve data dari API
async mounted() {
        try {
            //todo: buat untuk managernya
            if(this.getAuthRole === 'Admin') {
                const response = await this.$axios.get('/api/get-objective-for-approval/company')
                this.okrData = response.data.result
            } else if(this.getAuthRole === 'Manager') {
                const response = await this.$axios.get(`/api/get-objective-for-approval/${this.getAuthUserIdDepartment}`)
                this.okrData = response.data.result
            }
            console.log(this.okrData)
    
            
            // this.krData = response.data.result.listKR
        } catch(error) {
            console.warn(error)
        }
        
    },
    methods: {
        calculateProgress(value) {

            let sum = 0;
            if(value.length == 0) {
                return sum
            } else {
                value.forEach(element => {
                    sum += element.progress
                });
            }
            console.log(sum)
            return sum/value.length
        }
    },

}

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

    .okr-expanded > td > p {
        padding-left: 24px;
        margin: 0;
    }

    .progress {
        border-radius: 14px
    }

    .expanded {
        transform: rotate(90deg);
    }
</style>



