<template>
    <v-layout align-content-start column>
      <h3 id="title">Manager Review</h3>
      <v-layout justify-center align-center fill-height column>
          <v-flex xs12>
              <v-card width="700" flat class="card pa-3 mb-2 primary--text">
                    <v-layout row>
                        <v-flex xs9>
                            <p class="medium mt-4 ml-2 text-xs-left">Created By <span class="font-weight-bold">{{this.sumReviewQuestion[0].review.reviewer.name}}</span></p>
                        </v-flex>
                        <v-divider vertical class="my-1"></v-divider>
                        <v-flex xs3 class="text-xs-left ml-4">
                            <p>Average score: </p>
                            <h1>{{averageScore}}</h1>
                        </v-flex>
                    </v-layout>
                </v-card>
          </v-flex>
          <v-flex xs12>
              <v-card width="700" flat class="card mt-2 pa-3 primary--text">
                     <v-layout column>
                        <v-layout column v-for="(q,i) in sumReviewQuestion" :key="i"> 
                            <v-flex xs12 class="text-xs-left">
                                <template v-if="i === 0 || i===2 || i===4 || i===6">
                                    <h3>{{category[Math.floor(i/2)]}}</h3>
                                    <v-divider class="my-3"></v-divider>
                                </template>
                            </v-flex>
                            <v-flex xs12>
                                <v-layout row>
                                    <v-flex xs10 class="text-xs-left">
                                        <p>{{ q.questionReview.question }}</p>
                                    </v-flex>
                                    <v-flex xs2>
                                        <p>{{q.score}}</p>
                                    </v-flex>

                                </v-layout>
                            </v-flex>
                        </v-layout>
                    </v-layout>
                </v-card>
          </v-flex>
      </v-layout>

    </v-layout>
</template>

<script>
import { mapGetters } from 'vuex'
  export default {
    name:'sumReviewQuestion',
    data: () => ({
        category:["Start Small", "Take Lead", "Eager to Grow", "Pro Customer"],
        sumReviewQuestion:[],
        averageScore : 0,
        selected: [],
    }),
    computed: {
      ...mapGetters([
          'getAuthUser',
          'getAuthUserId',
          'getAuthRole',
          'getAuthName',

      ]),
    },

    // fungsi buat nge retrieve data dari API
    async mounted() {
        try {
            const response = await this.$axios.get(`api/get-result/ ${this.getAuthUserId}/manager/${this.$route.params.id}`)
            this.sumReviewQuestion = response.data.result
            const sumQuestion = response.data.result
            var i;
            var score = 0;
            var sum =0;
            console.log(sumQuestion.length)
            for(i=0;i<sumQuestion.length;i++){
                score += sumQuestion[i].score
                sum++

            }
            this.averageScore = (score/sum).toFixed(1)
            console.log("hadeh")
            console.warn(this.sumReviewQuestion)
        } catch(error) {
            console.log(this.sumReviewQuestion)
            console.warn(error)
        }
    },
  };
</script>

<style lang="scss" scoped>
    #title{
        position: center;
        margin-bottom: 20px;
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
        box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
    }
</style>



