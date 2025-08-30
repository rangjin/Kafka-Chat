package com.rangjin.chatapi.adapter.out.persistence.outbox.producer

import com.fasterxml.jackson.databind.ObjectMapper
import com.rangjin.chatapi.adapter.out.events.MessageEvent
import com.rangjin.chatapi.adapter.out.events.toMessageEvent
import com.rangjin.chatapi.adapter.out.persistence.outbox.entity.MessageOutboxJpaEntity
import com.rangjin.chatapi.adapter.out.persistence.outbox.repository.MessageOutboxJpaRepository
import com.rangjin.chatapi.domain.message.model.Message
import com.rangjin.chatapi.domain.message.port.out.MessageProducer
import org.springframework.stereotype.Component

@Component
class MessageOutboxProducer(

    private val objectMapper: ObjectMapper,

    private val messageOutboxJpaRepository: MessageOutboxJpaRepository

): MessageProducer {

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