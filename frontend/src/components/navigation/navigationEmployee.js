export default [
    {
        text: 'My OKR',
        icon: 'compass',
        url: { name: 'my-okr'}
    },
    {
        text: 'Department OKR',
        icon: 'globe',
        url: { name: 'table-okr'}
    },
    {
        text: 'Review',
        icon: 'check-circle',
        child: [
            {
                text: 'Self Review',
                url: { name: 'self-review' }
            },
            {
                text: 'Peer Review',
                url: { name: 'peer-review-list' }
            },
            {
                text: 'Review Result',
                url: {name : 'review-result'}
            }
        ]
    },
    {
        text: 'Feedback',
        icon: 'message-square',
        url: { name: 'feedback-list'}
    },


]
