<template>
    <v-layout align-content-start column>
      <v-layout shrink align-content-center justify-end row pb-5>
        <v-flex xs4>
        </v-flex>
        <v-flex xs4>
          <h3 id="title">Review Result</h3>
        </v-flex>
        <v-flex xs4 class="text-xs-right">
          <v-btn color="accent" class="white--text" dark @click="generatePDF()">
            <feather type="download" stroke-width=2 class="pr-2"></feather>
            <v-divider vertical color="white" class="my-1"></v-divider>
            <p class="ma-1 ml-3">Download result</p>
          </v-btn>
        </v-flex>
      </v-layout>

        <v-card flat class="card">
            <v-data-table
            v-model="selected"
            :items="sumReview"
            :headers="headers"
            :custom-filter="customFilter">
                <template v-slot:items="props">
                  <template v-if="props.item.status === 'completed'">
                  <tr @click="props.expanded = !props.expanded">
                    <td class="text-xs-center"> {{ props.item.date | formatDate}} </td>
                    <td class="text-xs-center">{{ props.item.type}}</td>
                    <td v-if= "props.item.type === 'peer-review'" class="text-xs-center">Anonymous</td>
                    <td v-else-if= "props.item.type === 'manager-review' || props.item.type === 'self-review'" class="text-xs-center">{{ props.item.reviewer.name}}</td>
                    <td class="text-xs-center">{{ props.item.avgScore}}</td>
                    <td v-if= "props.item.type === 'self-review'" class="text-xs-center"><v-btn flat class="btn secondary--text" :to="{ name: 'self-review-result', params: { id: props.item.id }}">View Detail</v-btn></td>
                    <td v-else-if= "props.item.type === 'peer-review'" class="text-xs-center"><v-btn flat class="btn secondary--text" :to="{ name: 'peer-review-result', params: { id: props.item.id } }">View Detail</v-btn></td>
                    <td v-else-if= "props.item.type === 'manager-review'" class="text-xs-center"><v-btn flat class="btn secondary--text" :to="{ name: 'manager-review-result', params: { id: props.item.id }}">View Detail</v-btn></td>
                  </tr>
                  </template>
                </template>
                <v-alert v-slot:no-results :value="true" color="error" icon="warning">
                    Your search for "{{ search }}" found no results.
                </v-alert>
            </v-data-table>
        </v-card>

    </v-layout>
</template>

<script>
import { mapGetters } from 'vuex'
import jsPDF from 'jspdf';
import autoTable from 'jspdf-autotable';

  export default {
    name:'sumReview',
    data: () => ({
      sumReview:[],
      selfReview:[],
      managerReview:[],
      peerReview:[],
      average : 0,

      selected: [],
      headers: [
            {
            //value is used for searching
            text: 'Date',
            align: 'center',
            sortable: true,
            value: 'date'
            },

            { text: 'Type',
            align: 'center',
            sortable: true,
            value: 'type'
            },

            { text: 'Owner',
            align: 'center',
            sortable: true,
            value: 'reviewer.name'
            },

            { text: 'Avg Score',
            align : 'center',
            sortable: true,
            value : 'avgScore'
            },

            { text:'',
            align: 'center'},
        ],
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
            const response = await this.$axios.get('api/get-result/' + this.getAuthUserId)
            // const responseYear = await this.$axios.get('api/get-year')
            //
            //
            // const responsePeerReview = this.$axios.get('api/get-result/' +this.getAuthUserId + '/peer-' + this.$route.params.id)

            this.sumReview = response.data.result
            //
            //
            // this.peerReview = responsePeerReview.data.result
            // const year = responseYear.data.result
            // var i;
            // for(i=0;i<year.length;i++){
            //   this.year.push(year[i])
            // }
            console.warn(response)
        } catch(error) {
            console.warn(error)
        }

    },

    methods: {
      customFilter(items, filters, filter, headers) {
        // Init the filter class.
        const cf = new this.$MultiFilters(items, filters, filter, headers);
        // Use regular function(),
        // arrow functions does not allow context binding.
        // Register "added_by" filter.
        // cf.registerFilter('year', function (year, items) {
        //   // If the filter has not been applied yet
        //   // just return all available items.
        //   if (year.trim() === '') return items;
        //   // Compare each item added_by and just return the matching ones.
        //   return items.filter(item => {
        //     var yearConvert = item.review.date.getYear;


        //     return item.review.item.objective.userObjective.userDepartment.name.toLowerCase() === year;
        //   }, department);
        // });

        // // Its time to run all created filters.
        // // Will be executed in the order thay were defined.
        // return cf.runFilters();
      },

      /**
       * Column shorting.
       *
       * @param column
       */

      /**
       * Handler when user  select some author at the "Author" select.
       */
      // filterYear(val) {
      //   this.filters = this.$MultiFilters.updateFilters(this.filters, {year: val});
      // },

      async generatePDF(){
           console.log("Generate pdf...");
            var i;
            var j;
            let doc = new jsPDF();

            doc.setFontSize(25);
            doc.text('Review Result',85,10);

            doc.setFontSize(10);
            doc.text("Name           :    " + this.sumReview[0].reviewee.name,10,30);

            doc.setFontSize(10);
            doc.text("Department  :    " + this.sumReview[0].reviewee.userDepartment.name,10,35)

            for(i=0;i<this.sumReview.length;i++){
              if(this.sumReview[i].type=="self-review" && this.sumReview[i].status=="completed"){
                console.log("aman")
                console.log(this.sumReview[i].id)

                // var responseSelfReview =  await this.$axios.get('api/get-result/' +this.getAuthUserId + '/self/' +this.sumReview[i].id )
                // console.log(responseSelfReview)
                // var jsonList = [];
                // for(i=0;i<this.sumReview[i].listReviewQuestion.length;i++){
                //   var obj = new Object();
                //   obj.component = this.sumReview[i].listReviewQuestion.question;
                //   obj.score  = this.sumReview[i].score;

                //   var jsonString= JSON.stringify(obj);
                //   jsonList.push(JSON.parse(jsonString))
                // //   var json = "{component: '"+this.sumReview[i].listReviewQuestion.question+"', score : '"+this.sumReview[i].score+"'}"
                // //   //   console.log(json)

                // }
                // this.selfReview = jsonList
                this.selfReview = this.sumReview[i].listReviewQuestion
                console.log(this.selfReview)

              }
              else if(this.sumReview[i].type=="manager-review" && this.sumReview[i].status=="completed"){
                console.log("makin")
                this.managerReview = this.sumReview[i].listReviewQuestion

              }

              else if(this.sumReview[i].type=="peer-review" && this.sumReview[i].status=="completed"){
                const responsePeerReview = await this.$axios.get('api/get-result/' +this.getAuthUserId + '/peer-' + this.sumReview[i].id )
                this.peerReview.push(responsePeerReview.data.result)

              }
            }

            

            //Self Review
            if(this.selfReview.length>0){
              let selfReview1 = this.selfReview.map(u => {
                let questionReview1 = u.questionReview.question;
                let score1 = u.score;
                return {questionReview1 : questionReview1,score1 : score1};
              });

              let self = [
                {title: 'Component', dataKey: "questionReview1"},
                {title: 'Score', dataKey: "score1" }
              ];

              doc.setFontSize(16);
              doc.text('Self Review',90,60);

              var score = 0;
              var sum =0;
                for(i=0;i<this.selfReview.length;i++){
                  score += this.selfReview[i].score
                  sum++
                }
              this.average = (score/sum).toFixed(1)
              doc.setFontSize(13);
              doc.text("Average Score : " + this.average,15, 70  );
              this.average = 0;

              doc.autoTable(self, selfReview1,{
                startY :75,
                // createdCell:function(cell,data){

                // }
              });
              if(this.managerReview.length>0 || this.peerReview.length>0){
                doc.addPage();
              }
            }
            
            if(this.managerReview.length>0){
              let managerReview1 = this.managerReview.map(u => {
                let questionReview1 = u.questionReview.question;
                let score1 = u.score;
                return {questionReview1 : questionReview1,score1 : score1};
              });

              let manager = [
                    {title: 'Component', dataKey: "questionReview1"},
                    {title: 'Score', dataKey: "score1"}
                  ];

                  if(this.selfReview.length>0){
                    doc.setFontSize(16);
                    doc.text('Manager Review',85,10);

                    
                    var score = 0;
                    var sum =0;
                    for(i=0;i<this.managerReview.length;i++){
                        score += this.managerReview[i].score
                        sum++

                    }
                    this.average = (score/sum).toFixed(1)
                    doc.setFontSize(13);
                    doc.text("Average Score : " + this.average,15, 20  );
                    doc.autoTable(manager, managerReview1,{
                      startY :25
                    });

                  }
                  else{
                    doc.setFontSize(16);
                    doc.text('Manager Review',90,60);

                    var score = 0;
                    var sum =0;
                    for(i=0;i<this.managerReview.length;i++){
                        score += this.managerReview[i].score
                        sum++

                    }
                    this.average = (score/sum).toFixed(1)
                    doc.setFontSize(13);
                    doc.text("Average Score : " + this.average,15, 70  );
                    doc.autoTable(manager, managerReview1,{
                      startY :75
                    });
                  }

                  if(this.peerReview.length>0){
                    doc.addPage();
                  }

            }

                for(i=0;i< this.peerReview.length;i++){
                  let peerReview1 = this.peerReview[i].map(u => {
                    let questionReview1 = u.questionReview.question;
                    let score1 = u.score;
                    return {questionReview1 : questionReview1,score1 : score1};
                  });

                  let peer = [
                    {title: 'Component', dataKey: "questionReview1"},
                    {title: 'Score', dataKey: "score1"}
                  ];

                  if(this.selfReview.length>0){
                    doc.setFontSize(16);
                    doc.text('Peer Review',85,10);
                   
                    var score = 0;
                    var sum =0;
                    console.log(this.peerReview[i].length)
                    for(j=0;j< this.peerReview[i].length;j++){
                        score += this.peerReview[i][j].score
                        sum++

                    }


                    this.average = (score/sum).toFixed(1)
                    doc.setFontSize(13);
                    doc.text("Average Score : " + this.average,15,20  );
                     doc.autoTable(peer, peerReview1,{
                      startY :25
                    });
                  }

                  else{
                    doc.setFontSize(16);
                    doc.text('Peer Review',90,60);
                    
                    var score = 0;
                    var sum =0;
                    console.log(this.peerReview[i].length)
                    for(j=0;j< this.peerReview[i].length;j++){
                        score += this.peerReview[i][j].score
                        sum++

                    }


                    this.average = (score/sum).toFixed(1)
                    doc.setFontSize(13);
                    doc.text("Average Score : " + this.average,15,70  );
                    doc.autoTable(peer, peerReview1,{
                      startY :75
                    });
                  }

                  if(i<this.peerReview.length-1){
                    doc.addPage();
                  }
                }

            doc.save("Review Result.pdf");
      },
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



