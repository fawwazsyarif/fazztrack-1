<template v-slot:items="props" xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
  <div id="app" class="min-h-screen bg-grey-light font-sans">
    <div class="md:w-custom mx-auto py-8 md:flex md:justify-between md:flex-wrap">
      <v-flex xs10 sm7 offset-xs0>
      <div>
        <h3 style="margin-bottom: 10px">Select phase to activate</h3>
        <div v-if="this.errormsg!==''">
          <v-alert
            :value="true"
            color="error"
            icon="warning"
            outline
          >
            {{errormsg}}
          </v-alert>
        </div>
      </div>
      </v-flex>
      <v-flex xs0 sm9 offset-xs1>
      </v-flex >

      <div v-for="phase in phases">
        <PhaseCard :phase-name=phase.name
                   :phase-desc=phase.description
                   :phase-id=phase.id
                   v-model="activePhaseId">
        </PhaseCard>
      </div>
      <v-flex xs0 xs5 offset-xs8>
      <Modal
        ref="modal"
        title="Change status"
        text="Are you sure want to change the phase?"
        modalType="confirmation"
        greenButtonText="Change"
        redButtonText="Cancel"
        @onModalGreenSubmit="onModalGreenSubmit"
        @onModalRedSubmit="onModalRedSubmit">
      </Modal>
        <Modal
          ref="notif"
          title="Change status"
          text="Your change has been saved!"
          modalType="notification-success"
        >
        </Modal>
      <v-btn style="width:196px" round color="#A1CD43" dark @click="changeConfirmation()">SAVE</v-btn>
      </v-flex>
    </div>

  </div>
</template>

<script>
import PhaseCard from '@/components/PhaseCard';
import Modal from '@/components/Modal';

export default {
  name: 'changePhase',
  components: {
    PhaseCard, Modal,
  },
  data() {
    return {
      errormsg: '',
      msg: '',
      phases: [],
      activePhaseId: 0,
      activePhaseName: '',
      currPhaseId: 0,
    };
  },

  async mounted() {
    try {
      const response = await this.$axios.get('/api/get-phases');
      this.phases = response.data.result;
      this.activePhaseId = this.phases.filter(p => p.status === 'active')[0].id;
      this.activePhaseName = this.phases.filter(p => p.status === 'active')[0].name;
      this.currPhaseId = this.activePhaseId;
      this.phases[0].description = 'When you turn on execution phase, employee will be able to manage their OKRs.';
      this.phases[1].description = 'When you turn on evaluation phase, execution phase will be automatically turned off. Employee won’t be able to manage their OKRs but they can access the review feature.';
      console.log(this.activePhaseId);
    } catch (error) {
      console.warn(error);
    }
  },

  methods: {

    changeConfirmation() {
      this.$refs.modal.changeModalState();
    },

    onModalRedSubmit() {
      this.$refs.modal.changeModalState();
    },

    onModalGreenSubmit() {
      if (!this.activePhaseId) {
        this.errormsg = 'Select one of the phase.';
      }
      if (this.activePhaseId === this.currPhaseId) {
        this.errormsg = 'You have selected the current phase. Try again.';
      } else {
        this.$axios.post('/api/change-active-phase', null,
          { params: { id: this.activePhaseId } },
        )
          .then((response) => {
            console.log(response);
          })
          .catch((error) => {
            console.log(error);
          });
        this.$refs.notif.changeModalState();
        this.currPhaseId = this.activePhaseId;
        this.errormsg = '';
      }
      this.$refs.modal.changeModalState();
      setTimeout(() => {
              this.$router.push("/");
          }, 1000);
    },
  },
};

</script>

<style>
</style>
