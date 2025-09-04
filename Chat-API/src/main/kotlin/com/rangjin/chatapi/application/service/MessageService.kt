package com.rangjin.chatapi.application.service

import com.rangjin.chatapi.application.port.`in`.message.SendMessageUseCase
import com.rangjin.chatapi.application.port.out.message.MessagePublisher
import com.rangjin.chatapi.domain.event.MessageSent
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.UUID

@Service
class MessageService(

    private val messagePublisher: MessagePublisher

) : SendMessageUseCase {

    @Transactional
    override fun sendMessage(
        channelId: Long,
        senderId: Long,
        content: String,
        sendAt: LocalDateTime
    ): MessageSent =
        messagePublisher.publish(MessageSent(
            messageId = UUID.randomUUID(),
            channelId = channelId,
            senderId = senderId,
            content = content,
            sentAt = sendAt
        ))

}