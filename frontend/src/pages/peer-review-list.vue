<template>
    <v-layout>
        <template v-if="activePhase.name === 'execution'">
            <v-container>
                <v-layout justify-center align-center fill-height>
                    <v-flex xs11>
                    <v-card flat class="card mx-auto py-5" max-height="600">
                        <v-img :src="require('@/assets/cancel.svg')" contain aspect-ratio="4" ></v-img>
                        <v-card-text>
                            <p class="font-weight-bold primary--text pt-4 mb-0">Oops Sorry :(</p>
                            <p class="font-weight-bold primary--text pt-4 mb-0">You can't do peer-review because you're currently on execution phase</p>
                        </v-card-text>
                        <v-btn color="accent" round class="px-5 mb-4" :to="{ name: 'my-okr' }">GO TO MY OKR</v-btn>
                    </v-card>
                    </v-flex>
                </v-layout>
            </v-container>
        </template>

        <template  v-else-if="activePhase.name === 'evaluation'">
            <v-layout align-content-start column>
                <v-layout shrink align-content-center justify-end row pb-5>
                    <v-flex xs4>
                        <!-- <v-card> -->
                        <v-text-field 
                            class="field"
                            v-model="search"
                            append-icon="search"
                            label="Search employee's name or department"
                            solo
                            flat
                        ></v-text-field>
                        <!-- </v-card> -->
                    </v-flex>
                    <v-flex xs4>
                        <h4>Peer Review List</h4>
                    </v-flex>
                    <v-flex xs4>
                        <!-- <h4>OKR Employee All Department</h4> -->
                    </v-flex>
                </v-layout>
                <v-card class="card flat">
                    <v-data-table
                    :items="reviewee"
                    :search="search"
                    :headers="headers">
                        <!-- <v-list v-for="(nav,i) in navigation" :key="i"> -->
                        <template v-slot:items="props">
                            <tr @click="props.expanded = !props.expanded">
                                <!-- <td class="text-xs-left"> 
                                    <img src="@/assets/expand.svg" :class="{ 'expanded': props.expanded }"> 
                                    {{ props.item.description }} 
                                </td> -->
                                <td class="text-xs-left">{{ props.item.reviewee.name }}</td>
                                <td class="text-xs-center"> {{ props.item.reviewee.userDepartment.name }}</td>
                                <template v-if="props.item.status === 'incomplete'">
                                    <td class="text-xs-center"><v-btn flat class="btn secondary--text" :to="{ name: 'peer-review', params: { idReviewer: getAuthUser.id, idReviewee:props.item.reviewee.id } }">Give Review</v-btn></td>
                                </template>
                                <template v-else>
                                    <td class="text-xs-center">
                                        <Chip
                                            :idStatus=3
                                            :status="props.item.status">
                                        </Chip>
                                    </td>
                                </template>
                                </tr>
                        </template>

                        <v-alert v-slot:no-results :value="true" color="error" icon="warning">
                            Your search for "{{ search }}" found no results.
                        </v-alert>
                    </v-data-table>
                </v-card>
            </v-layout>
        </template>
    </v-layout>
</template>
<script>
import calculateProgress from '@/functions/calculateProgress'
import Chip from '@/components/Chip'
import { mapGetters } from 'vuex'

export default {
    name : 'peer-review-list',

    data () {
        return{
            search: '',
            reviewee: [],
            activePhase:{},
            headers: [
                {
                //value is used for searching
                text: 'Peer Reviewee',
                align: 'center',
                sortable: true,
                value: 'reviewee.name'
                },
                { text: 'Department', 
                align: 'center',
                value: 'reviewee.userDepartment.name'
                },
                { text:'status',
                align: 'center',
                sortable: true,
                value: 'status'
                }
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

    async mounted() {
        try {
            const response = await this.$axios.get(`/api/review/get/peer-review/${this.getAuthUser.id}`)
            this.reviewee = response.data.result

            const phase = await this.$axios.get('/api/get-active-phase')
            this.activePhase = phase.data.result

            console.log(this.okrData)
        } catch(error) {
            console.warn(error)
        }
        
    },
    methods: {},

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

    .card {
        border: 1px solid #DBE0E4;
    }
</style>



