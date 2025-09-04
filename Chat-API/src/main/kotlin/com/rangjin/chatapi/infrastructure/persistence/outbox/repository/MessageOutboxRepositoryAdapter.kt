package com.rangjin.chatapi.infrastructure.persistence.outbox.repository

import com.fasterxml.jackson.databind.ObjectMapper
import com.rangjin.chatapi.application.port.out.message.MessagePublisher
import com.rangjin.chatapi.domain.event.ChannelActivity
import com.rangjin.chatapi.domain.event.MessageSent
import com.rangjin.chatapi.infrastructure.persistence.outbox.entity.AggregateType
import com.rangjin.chatapi.infrastructure.persistence.outbox.entity.MessageOutboxJpaEntity
import org.springframework.stereotype.Component

@Component
class MessageOutboxRepositoryAdapter(

    private val messageOutboxJpaRepository: MessageOutboxJpaRepository,

    private val objectMapper: ObjectMapper

): MessagePublisher {

    override fun publish(message: MessageSent): MessageSent {
        messageOutboxJpaRepository.save(
            MessageOutboxJpaEntity(
                aggregateType = AggregateType.MESSAGE,
                aggregateId = message.channelId,
                payload = objectMapper.writeValueAsString(message),
                timestamp = message.sentAt
            )
        )

        return message
    }

    override fun publish(activity: ChannelActivity): ChannelActivity {
        messageOutboxJpaRepository.save(
            MessageOutboxJpaEntity(
                aggregateType = AggregateType.CHANNEL_ACTIVITY,
                aggregateId = activity.channelId,
                payload = objectMapper.writeValueAsString(activity),
                timestamp = activity.occurredAt
            )
        )

        return activity
    }

}