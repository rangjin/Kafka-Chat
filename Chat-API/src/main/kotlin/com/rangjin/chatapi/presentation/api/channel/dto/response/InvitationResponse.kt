package com.rangjin.chatapi.presentation.api.channel.dto.response

import com.rangjin.chatapi.domain.user.User

data class InvitationResponse(

    val success: Boolean = true,

    val invitedUserIds: List<User>

)