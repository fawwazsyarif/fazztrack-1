<template>
  <v-layout align-center class="pa-3" @click="updateActivePhase">
    <v-flex shrink align-self-center xs0 offset-xs1>
      <v-avatar size="20" color="#4B4A56">
        <v-avatar style="top: 0px" size="17" color="white">
        <div v-if="activePhaseId===phaseId">
          <v-avatar style="top: -2px" size="8" color="#4B4A56"></v-avatar>
        </div>
        </v-avatar>
      </v-avatar>
    </v-flex>
    <v-flex xs0 sm9 offset-xs1>
      <v-card hover class="card active">
        <div @click="updateActivePhase">
        </div>
        <v-card-title primary-title>
          <div>
            <h3 style="margin-left: 10px">{{phaseName}}</h3>
          </div>
        </v-card-title>
        <v-divider></v-divider>
        <v-flex class="pa-0">
          <v-card-text>
            <div>
              <p align="left" style="margin-left:10px; color: #A09EB5">{{phaseDesc}}</p>
            </div>
          </v-card-text>
        </v-flex>
      </v-card>
    </v-flex>
  </v-layout>
  </template>
<script>
export default {
  name: 'PhaseCard',
  model: {
    prop: 'activePhaseId',
    event: 'onUpdatePhase',
  },
  props: ['phaseName', 'phaseDesc', 'activePhaseId', 'phaseId'],
  methods: {
    updateActivePhase() {
      this.$emit('onUpdatePhase', this.phaseId);
      console.log(this.phaseId);
    },
  },
};
</script>

<style scoped>

</style>
