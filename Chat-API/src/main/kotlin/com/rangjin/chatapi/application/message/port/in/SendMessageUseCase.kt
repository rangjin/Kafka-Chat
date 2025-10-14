package com.rangjin.chatapi.application.message.port.`in`

import com.rangjin.chatapi.domain.message.Message
import java.time.LocalDateTime

interface SendMessageUseCase {

    fun sendMessage(
        channelId: Long,
        senderId: Long,
        content: String,
        sendAt: LocalDateTime = LocalDateTime.now()
    ): Message

}