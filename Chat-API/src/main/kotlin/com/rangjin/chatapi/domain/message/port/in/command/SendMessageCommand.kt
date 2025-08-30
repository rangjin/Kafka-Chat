package com.rangjin.chatapi.domain.message.port.`in`.command

import java.time.LocalDateTime

data class SendMessageCommand(

    val channelId: Long,

    val senderId: Long,

    val content: String,

    val sendAt: LocalDateTime = LocalDateTime.now()

)