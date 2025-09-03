package com.rangjin.chatapi.infrastructure.persistence.outbox.producer

import com.fasterxml.jackson.databind.ObjectMapper
import com.rangjin.chatapi.domain.message.Message
import com.rangjin.chatapi.application.port.out.message.MessageProducer
import com.rangjin.chatapi.infrastructure.events.toMessageEvent
import com.rangjin.chatapi.infrastructure.persistence.outbox.entity.MessageOutboxJpaEntity
import com.rangjin.chatapi.infrastructure.persistence.outbox.repository.MessageOutboxJpaRepository
import org.springframework.stereotype.Component

@Component
class MessageOutboxProducer(

    private val objectMapper: ObjectMapper,

    private val messageOutboxJpaRepository: MessageOutboxJpaRepository

) : MessageProducer {

    override fun send(message: Message): Message {
        val json = objectMapper.writeValueAsString(message.toMessageEvent())
        val messageOutboxJpaEntity = MessageOutboxJpaEntity(
            messageId = message.messageId,
            payload = json,
            sendAt = message.sendAt
        )

        messageOutboxJpaRepository.save(messageOutboxJpaEntity)
        return message
    }

}