package com.rangjin.chatapi.application.membership.port.`in`

import com.rangjin.chatapi.domain.user.User

interface InvitationUseCase {

    fun invitationToChannel(userId: Long, channelId: Long, invitedEmails: List<String>): List<User>

}