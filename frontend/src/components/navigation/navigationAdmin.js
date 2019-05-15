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
        text: 'Company OKR',
        icon: 'globe',
        url: { name: 'table-okr'}
    },
    {
        text: 'Summary',
        icon: 'clipboard',
        child: [
          {
            text: 'Company Summary',
            url: { name: 'summary-company' }
          },
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
            }
        ]
    },
    {
      text: 'Manage User and Phase',
      icon: 'settings',
      child: [
        {
          text: 'Manage Users',
          url: { name: 'user-list'}
        },
        {
          text: 'Manage Phase',
          url: { name: 'change-phase'},
        },
      ]
    },
    {
      text: 'Feedback',
      icon: 'message-square',
      url: { name: 'feedback-list'}
    },
]
