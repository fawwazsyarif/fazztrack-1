<template>
    <v-layout align-content-start column>
        <v-layout shrink align-content-center justify-end row my-5>
            <v-flex xs4 class="text-xs-left">
            </v-flex>
            <v-flex xs4>
                <h3 id="title">Self Review</h3>    
            </v-flex>
            <v-flex xs4 class="text-xs-right">
                <v-btn color="accent" class="white--text" dark @click="generatePDF()">
                    <feather type="download" stroke-width=2 class="pr-2"></feather>
                    <v-divider vertical color="white" class="my-1"></v-divider>
                    <p class="ma-1 ml-3">Download result</p>
                </v-btn>
            </v-flex>
        </v-layout>
        <v-layout justify-center align-center fill-height column>    
            <v-flex xs12>
                <v-card width="700" flat class="card pa-3 mb-2 primary--text">
                    <v-layout row>
                        <v-flex xs9>
                            <p class="medium mt-4 ml-2 text-xs-left">Created By <span class="font-weight-bold">{{getAuthName}}</span></p>
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
import jsPDF from 'jspdf';
import autoTable from 'jspdf-autotable';
import { mapGetters } from 'vuex'
  export default {
    name:'sumReviewQuestion',
    data: () => ({
        category:["Start Small", "Take Lead", "Eager to Grow", "Pro Customer"],
        sumReviewQuestion:[],
        sumQuestion : [],
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
            const response = await this.$axios.get(`api/get-result/${this.getAuthUserId}/self/${this.$route.params.id}`)
            this.sumReviewQuestion = response.data.result

            const sumQuestion = response.data.result
            this.sumQuestion = sumQuestion
            var i;
            var score = 0;
            var sum =0;
            for(i=0;i<sumQuestion.length;i++){
                score += sumQuestion[i].score
                sum++

            }
            this.averageScore = (score/sum).toFixed(1)
            console.warn(response.data.result)
        } catch(error) {
            console.warn(error)
        }
    },

    methods:{
      generatePDF(){
           console.log("Generate pdf...");
          console.log(this.sumQuestion)
            let self = [
               {title: 'Component', dataKey: this.sumQuestion.questionReview.question},
               {title: 'Score', dataKey: this.sumQuestion.score }
            ];

            let doc = new jsPDF();
            doc.text('Self Review!', 10, 10);
            doc.autoTable(self, this.sumQuestion);
            doc.save("export.pdf");
      },
    }
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



