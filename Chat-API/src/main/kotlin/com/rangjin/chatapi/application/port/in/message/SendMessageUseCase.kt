package com.rangjin.chatapi.application.port.`in`.message

import com.rangjin.chatapi.domain.event.MessageSent
import java.time.LocalDateTime

interface SendMessageUseCase {

    fun sendMessage(
        channelId: Long,
        senderId: Long,
        content: String,
        sendAt: LocalDateTime = LocalDateTime.now()
    ): MessageSent

}