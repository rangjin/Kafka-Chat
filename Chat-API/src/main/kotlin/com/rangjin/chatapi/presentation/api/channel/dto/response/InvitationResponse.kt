package com.rangjin.chatapi.presentation.api.channel.dto.response

import com.rangjin.chatapi.domain.user.User

data class InvitationResponse(

    val result: Boolean = true,

    val invitedUserIds: List<User>

)