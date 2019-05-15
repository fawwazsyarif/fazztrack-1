export default [

    {
        text: 'OKRs for Approval',
        icon: 'edit',
        url: { name: 'okr-for-approval'}
    },
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
        text: 'Summary',
        icon: 'clipboard',
        child: [
            {
                text: 'Department Summary',
                url: { name: 'summary-department' }
            },
        ]
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
              text: "Manager Review",
              url: { name: 'manager-review-list' }
            },

            // {
            //     text: "Assign Employee's Peer (version 1)",
            //     url: { name: 'assign-peer' }
            // },

            {
                text: "Assign Employee's Peer",
                url: { name: 'assign-peer-list' }
            },

            {
              text: 'Review Result',
              url: {name : 'review-result'}
            },

        ]
    },
    {
        text: 'Feedback',
        icon: 'message-square',
        url: { name: 'feedback-list'}
    },

]
