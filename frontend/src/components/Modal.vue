<template>
  <div class="text-xs-center">
    <v-dialog
      v-model="modalDisplayed"
      width="500"
    >
      <v-card>
        <v-card-text class="text-sm-left font-weight-medium subheading">{{ title }}</v-card-text>
        <v-divider></v-divider>

        <!-- buat notification -->
        <template v-if="modalType === 'notification-error' ||  modalType === 'notification-success'">
            <template v-if="modalType === 'notification-error'">
                <feather class="pt-4 error--text" type="x-circle" stroke-width=2 size="40" ></feather>
            </template>
            <template v-if="modalType === 'notification-success'">
                <feather class="pt-4 accent--text" type="check-circle" stroke-width=2 size="40"></feather>
            </template>
            <v-card-text class="pb-20"> {{ text }} </v-card-text>
            <br>
            <v-divider></v-divider>
        </template>

        <!-- confirmation yang button ijonya di kanan -->
        <template v-else-if="modalType === 'confirmation'">
            <v-card-text> {{ text }} </v-card-text>
            <v-card-actions class="mr-3">
            <v-spacer></v-spacer>
            <v-btn
                color="error"
                class="white--text"
                @click="$emit('onModalRedSubmit')"
            >
                {{ redButtonText }}

            </v-btn>
            <v-btn
                color="success"
                class="white--text"
                @click="$emit('onModalGreenSubmit')"
            >
                {{ greenButtonText }}
            </v-btn>
            </v-card-actions>
            <br>
        </template>

        <!-- confirmation yang button merahnya di kanan -->
        <template v-else-if="modalType === 'confirmation-reverse'">
            <v-card-text> {{ text }} </v-card-text>
            <v-card-actions class="mr-3">
            <v-spacer></v-spacer>
            <v-btn
                color="success"
                class="white--text"
                @click="$emit('onModalGreenSubmit')"
            >
                {{ greenButtonText }}
            </v-btn>
            <v-btn
                color="error"
                class="white--text"
                @click="$emit('onModalRedSubmit')"
            >
                {{ redButtonText }}
            </v-btn>
            </v-card-actions>
            <br>
        </template>

      </v-card>
    </v-dialog>
  </div>
</template>
<script>
export default {
  name: 'Modal',

  data() {
    return {
      modalDisplayed: false,
    };
  },

  methods: {
      changeModalState() {
          this.modalDisplayed = !this.modalDisplayed;
      },

  },

  props: {
    // ini buat nentuin modalnya buat konfirmasi atau notifikasi
    //   modalType ada 4:
    //   confirmation,
    //   confirmation-reverse (ini buat button merahnya di kanan),
    //   notification-error (lambangnya x),
    //   notification-success (lambangnya check)
    modalType: {
      type: String,
      default: '',
    },
    // title di paling atas dialog
    title: {
      type: String,
      default: '',
    },
    // isi textnya
    text: {
      type: String,
      default: '',
    },
    // buat text button warna merah
    redButtonText: {
      type: String,
      default: 'cancel',
    },
    // buat text button warna ijo
    greenButtonText: {
      type: String,
      default: 'yes',
    },
  },

};
</script>
<style>
</style>
