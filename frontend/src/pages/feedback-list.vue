<template>
  <v-layout align-content-start column>
    <v-layout shrink align-content-center justify-end row pb-5>
      <v-flex xs4>
        <v-text-field 
          class="field"
          v-model="search"
          append-icon="search"
          label="Search feedback"
          solo
          flat
        ></v-text-field>  
      </v-flex>
      <v-flex xs4>
        <h4>Employee's Feedback</h4>
      </v-flex>
      <v-flex xs4 class="text-xs-right"> 
        <v-btn color="accent" class="white--text" dark :to="{name: 'feedback-form'}">
              <feather type="plus" stroke-width=2 class="pr-2"></feather>
              <v-divider vertical color="white" class="my-1"></v-divider>
              <p class="ma-1 ml-3">Add feedback</p>
        </v-btn>
      </v-flex>
    </v-layout>

    <v-data-iterator
    :items="feedbackList"
    row wrap>
     <template v-slot:item="props">
    <v-card>
        <v-card-text>
          <div style="text-align:left">
            <span style="margin-right:20px;font-size:12px" v-if="props.item.userObjective===null">
              <b>Anonymous</b>
            </span>
            <span style="margin-right:20px;font-size:12px" v-else>
              <b>{{props.item.userObjective.name}}</b>
            </span>
            <span style="color:#4B4A56;font-size:12px">{{props.item.date}}</span>
            <p><i>{{props.item.description}}</i></p>
          </div>
        </v-card-text>
    </v-card>
    </template>
    </v-data-iterator>
 </v-layout>       
</template>

              
<script>
import { mapGetters } from 'vuex'
export default {
    name : 'feedbackList',
    data(){
      return{
        search: '',
        item: null,
        feedbackList: [],
      }
    },

    async mounted() {
    try {
      const response = await this.$axios.get('/api/feedback');
      this.feedbackList = response.data.result;

      console.log(this.feedbackList);
    } catch (error) {
      console.warn(error);
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