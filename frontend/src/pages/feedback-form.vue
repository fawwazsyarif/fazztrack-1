<template>
<v-layout justify-center >
<v-card height="320px" width="650px">
	<v-card-text>
		<b>Add New Feedback</b>
		<v-textarea outline
				ref="desc"
				placeholder="Describe any issues you’re experiencing..."
				:counter="255"
				type="text"
				required
				:rules="[rules.descMax, rules.required]"
				v-model="description"
		></v-textarea>
	</v-card-text>
	<v-card-actions>
			<Modal
				ref="notif"
				title="Add new feedback"
				text="Your feedback has been submitted!"
				modalType="notification-success">
          	</Modal>
		<v-flex align-self-center>
			<v-btn round flat
			color=error
			depressed :to="{ name: 'feedback-list'}"
			>Cancel</v-btn>
			<v-btn round
				color=accent
				depressed
				v-model="user"
				@click="submitFeedback">Submit</v-btn>
				</v-flex>
		</v-card-actions>
</v-card>
</v-layout>
</template>
<script>
import Modal from '../components/Modal';
import { mapGetters } from 'vuex'
export default {
	name : 'feedbackForm',
	components: {
	Modal
	},
	data(){
		return{
			description: '',
			rules: {
				required: v => !!v || 'Please write the description',
				descMax: v => v.length <= 255 || 'The feedback must have less than 255 characters'
			},
			user: this.getAuthUser
		}
	},

	computed: {
        ...mapGetters([
            'getAuthUser',
        ]),
	},
	
    methods: {
		submitFeedback(){
			if (!this.description){
				this.$refs.form.validate()
			}
			else{
				this.$axios.post('/api/feedback/add', {
					description: this.description,
					// userObjective: {id: this.getAuthUser.id},
					userObjective:
					{id: this.getAuthUser.id}
				
			})
				.then((response) => {
				console.log(response);
				console.log(this.description)
				})
				.catch((error) => {
				console.log(error);
				});
				this.$refs.notif.changeModalState();

			}
			setTimeout(() => {
                this.$router.push("/feedback");
            }, 700);
		},

	}
}
</script>