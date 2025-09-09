package com.rangjin.chatapi.presentation.api.channel.dto.response


data class InvitationResponse(

    val success: Boolean = true,

    val invitedUserIds: List<Long>

)