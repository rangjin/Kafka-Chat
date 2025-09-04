package com.rangjin.chatapi.application.port.`in`.channel

import com.rangjin.chatapi.domain.user.User

interface InvitationUseCase {

    fun invitationToChannel(userId: Long, channelId: Long, invitedEmails: List<String>): List<User>

}