<template>
    <v-layout align-content-start column>
      <h3 id="title">Company Summary OKR</h3>
      <v-layout shrink row justify-center>
        
        <v-flex xs2>
            <v-menu
                    ref="show_start_date"
                    :close-on-content-click="false"
                    v-model="show_start_date"
                    :nudge-right="40"
                    :return-value.sync="start_date"
                    lazy
                    transition="scale-transition"
                    offset-y
                    full-width
                    min-width="290px">
                <v-text-field
                        slot="activator"
                        v-model="start_date"
                        label="From"
                        prepend-icon="event"
                        readonly
                ></v-text-field>
                <v-date-picker
                        v-model="start_date"
                        @input="filterStartDate"
                ></v-date-picker>

            </v-menu>
            <!-- <v-btn color="accent" class="white--text" dark @click="createSum">
                <feather type="plus" stroke-width=2 class="pr-2"></feather>
                <v-divider vertical color="white" class="my-1"></v-divider>
                <p class="ma-1 ml-3">Generate Summary</p>
            </v-btn> -->
        </v-flex>

        <v-flex xs2>
            <v-menu
                    ref="show_end_date"
                    :close-on-content-click="false"
                    v-model="show_end_date"
                    :nudge-right="40"
                    :return-value.sync="end_date"
                    lazy
                    transition="scale-transition"
                    offset-y
                    full-width
                    min-width="290px">
                <v-text-field
                        slot="activator"
                        v-model="end_date"
                        label="To"
                        prepend-icon="event"
                        readonly
                ></v-text-field>
                <v-date-picker
                        v-model="end_date"
                        @input="filterEndDate"
                ></v-date-picker>
            </v-menu>
          </v-flex>
      </v-layout>
      <v-layout column shrink row justify-center>
        <v-flex xs12 class="text-xs-right mb-3">
            <v-btn color="accent" class="white--text" dark @click="createSum">
                <feather type="plus" stroke-width=2 class="pr-2"></feather>
                <v-divider vertical color="white" class="my-1"></v-divider>
                <p class="ma-1 ml-3">Generate Summary</p>
            </v-btn>
        </v-flex>
        <v-flex class="mt-3">
            <v-card flat class="card">
                <v-data-table
                v-model="selected"
                        :headers="headers"
                        :items="sumComp"
                        :search="filters"
                        :custom-filter="customFilter">

                        <template v-slot:items="props">
                            <tr @click="props.expanded = !props.expanded">
                                <td class="text-xs-center"> {{ props.item.date | formatDate }} </td>
                                <td class="text-xs-center">{{ props.item.compObjective.userObjective.userDepartment.name }}</td>
                                <td class="text-xs-center">{{ props.item.compObjective.description }}</td>
                                <td class="text-xs-center">{{ props.item.avgProgress }} %</td>
                            </tr>
                        </template>
                    <v-alert v-slot:no-results :value="true" color="error" icon="warning">
                        Your search for "{{ search }}" found no results.
                    </v-alert>
                </v-data-table>
            </v-card>
            </v-flex>
        </v-layout>
    </v-layout>
</template>
<script>

export default {
  name:'sumComp',
    data: () => ({
      sumComp: [],
      show_start_date: false,
      start_date: null,
      show_end_date: false,
      end_date: null,
      filters: {
        search:'',
        start_date: null,
        end_date: null,
      },
      selected: [],
      headers: [
            {
            //value is used for searching
            text: 'Date',
            align: 'center',
            sortable: true,
            value: 'date'
            },

            { text: 'Department',
            align: 'center',
            sortable: true,
            value: 'compObjective.userObjective.userDepartment.name'
            },

            { text: 'Objective',
            align: 'center',
            sortable: true,
            value: 'compObjective.description'
            },

            { text: 'Overall Progress',
            align : 'center',
            sortable: true,
            value : 'avgProgress'
            },
        ],
    }),
    methods: {
        async createSum(){
          const responseComp = await this.$axios.post('/api/submitSumComp',null)          

          const response = await this.$axios.get('/api/sumComp')
          this.sumComp = response.data.result
          //const responseDept = await this.$axios.post('/api/submitSumDept',null)

        },
        customFilter(items, filters, filter, headers) {
            // Init the filter class.
            const cf = new this.$MultiFilters(items, filters, filter, headers);
            // Use regular function(),
            // arrow functions does not allow context binding.
            // Register the global standard filter.
            cf.registerFilter('search', function (searchWord, items) {
            if (searchWord.trim() === '') return items;
            return items.filter(item => {
                return item.name.toLowerCase().includes(searchWord.toLowerCase());
            }, searchWord);
            });

            // Use regular function(),
            // arrow functions does not allow context binding.
            // Register "start_date" filter.
            cf.registerFilter('start_date', function (start_date, items) {
            // If the filter has not been applied yet
            // just return all available items.
            if (start_date === null) return items;
            // Compare each item start_date and just return the matching ones.
            return items.filter(item => {
                return item.date >= start_date;
            }, start_date);
            });
            // Use regular function(),
            // arrow functions does not allow context binding.
            // Register "end_date" filter.
            cf.registerFilter('end_date', function (end_date, items) {
            // If the filter has not been applied yet
            // just return all available items.
            if (end_date === null) return items;
            // Compare each item end_date and just return the matching ones.
            return items.filter(item => {
                return item.date <= end_date;
            }, end_date);
            });
            // Its time to run all created filters.
            // Will be executed in the order thay were defined.
            return cf.runFilters();
        },

        /**
         * Column shorting.
         *
         * @param column
         */
        /**
         * Handler when user input something at the "Filter" text field.
         */
        filterSearch(val) {
            this.filters = this.$MultiFilters.updateFilters(this.filters, {search: val});
        },
        /**
         * Handler when select a date on "From" date picker.
         */
        filterStartDate(val) {
            // Close the date picker.
            this.$refs.show_start_date.save(val);

            //Convert the value to a timestamp before saving it.
            const timestamp = new Date(val + 'T00:00:00Z').getTime();
            this.filters = this.$MultiFilters.updateFilters(this.filters, {start_date: val});
            console.log(val)
        },
        /**
         * Handler when select a date on "To" date picker.
         */
        filterEndDate(val) {
            // Close the date picker.
            this.$refs.show_end_date.save(val);
            //Convert the value to a timestamp before saving it.
            const timestamp = new Date(val + 'T00:00:00Z').getTime();
            this.filters = this.$MultiFilters.updateFilters(this.filters, {end_date: val});
        },
    },
    //fungsi buat nge retrieve data dari API
    async mounted() {
        try {
            const response = await this.$axios.get('/api/sumComp')
            this.sumComp = response.data.result
            console.warn(response)
        } catch(error) {
            console.warn(error)
        }

    },
  };
</script>

<style lang="scss" scoped>
    #title{
        position: center;
        margin-bottom: 20px
    }
    .btn {
        background: linear-gradient(360deg, #EFF3F6 0%, #FFFFFF 100%);
        border: 1px solid #DBE0E4;
        box-sizing: border-box;
        border-radius: 4px;
    }
    .field {
        width: 40px;
    }
    .card {
        border: 1px solid #DBE0E4;
    }
</style>



