package com.rangjin.chatapi.application.service

import com.rangjin.chatapi.application.port.`in`.message.SendMessageUseCase
import com.rangjin.chatapi.application.port.out.message.MessageProducer
import com.rangjin.chatapi.domain.message.Message
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class MessageService(

    private val messageProducer: MessageProducer

) : SendMessageUseCase {

    @Transactional
    override fun sendMessage(
        channelId: Long, senderId: Long, content: String, sendAt: LocalDateTime
    ): Message =
        messageProducer.send(
            Message(
                channelId = channelId,
                senderId = senderId,
                content = content,
                sendAt = sendAt
            )
        )

}