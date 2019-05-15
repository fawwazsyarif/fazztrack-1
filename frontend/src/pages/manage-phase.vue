<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
  <div id="app" class="min-h-screen bg-grey-light font-sans">
    <div class="md:w-custom mx-auto py-8 md:flex md:justify-between md:flex-wrap">
      <v-flex xs10 sm7 offset-xs0>
      <div>
        <h3 style="margin-bottom: 10px">Select phase to activate</h3>
      </div>
      </v-flex>
      <PhaseCard
        phase-name="Execution Phase"
        phase-desc="When you turn on execution phase, employee will be able to manage their OKRs."
        v-model="activePhase"
      ></PhaseCard>
      <PhaseCard
        phase-name="Evaluation Phase"
        phase-desc="When you turn on evaluation phase, execution phase will be automatically turned off. Employee won’t be able to manage their OKRs but they can access the review feature."
        v-model="activePhase"
      ></PhaseCard>
    </div>
    <template>
      <v-layout row justify-end>
        <v-flex sm4>
        <v-dialog v-model="dialog" persistent width="600px">
          <template v-slot:activator="{ on }">
            <v-btn style="width:196px;margin-top: 5%" round color="#A1CD43" dark v-on="on">SAVE</v-btn>
          </template>
          <v-card>
            <v-card-title>
              <h3>Change Phase</h3>
              <v-spacer></v-spacer>
              <v-btn small icon light @click="dialog=false">
                <v-icon >close</v-icon>
              </v-btn>
            </v-card-title>
            <v-divider></v-divider>
            <v-flex>
            <v-card-text>
              <div style="padding-bottom: 10px">
                <p>Are you sure you want to activate this phase?</p>
              </div>
            </v-card-text>
            </v-flex>
            <v-flex offset-sm2>
            <v-card-actions>
              <div style="margin-top:-15px;padding-bottom: 20px">
              <v-btn style="width:196px" round color="#757575" dark @click="dialog = false">CANCEL</v-btn>
                <v-dialog v-model="SavedDialog" persistent width="600px">
                  <template v-slot:activator="{ on }">
                    <v-btn style="width:196px" round color="#48ACF0" dark @click="dialog = false, SavedDialog=true">YES</v-btn>
                  </template>
                  <v-card>
                    <v-card-title>
                      <h3>Change Phase</h3>
                      <v-spacer></v-spacer>
                      <v-btn small icon light @click="SavedDialog=false">
                        <v-icon >close</v-icon>
                      </v-btn>
                    </v-card-title>
                    <v-divider></v-divider>
                    <v-flex>
                      <v-card-text>
                        <div style="padding-bottom: 10px">
                          <p>Your change has been saved</p>
                        </div>
                      </v-card-text>
                    </v-flex>
                    <v-flex offset-sm2>
                    </v-flex>
                  </v-card>
                </v-dialog>
              </div>
            </v-card-actions>
            </v-flex>
          </v-card>
        </v-dialog>
        </v-flex>
      </v-layout>
    </template>
  </div>
</template>

<script>
import PhaseCard from '../components/PhaseCard';

export default {
  name: 'managePhase',
  components: {
    PhaseCard,
  },
  data() {
    return {
      activePhase: '',
      dialog: false,
      SavedDialog: false,
    };
  },
};
</script>

<style>
</style>
