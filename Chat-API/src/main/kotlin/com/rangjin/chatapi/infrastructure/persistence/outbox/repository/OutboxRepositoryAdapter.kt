package com.rangjin.chatapi.infrastructure.persistence.outbox.repository

import com.fasterxml.jackson.databind.ObjectMapper
import com.rangjin.chatapi.application.port.out.message.MessagePublisher
import com.rangjin.chatapi.domain.event.ChannelActivity
import com.rangjin.chatapi.domain.event.MessageSent
import com.rangjin.chatapi.infrastructure.persistence.outbox.entity.AggregateType
import com.rangjin.chatapi.infrastructure.persistence.outbox.entity.OutboxJpaEntity
import org.springframework.stereotype.Component

@Component
class OutboxRepositoryAdapter(

    private val outboxJpaRepository: OutboxJpaRepository,

    private val objectMapper: ObjectMapper

): MessagePublisher {

    override fun publish(message: MessageSent): MessageSent {
        outboxJpaRepository.save(
            OutboxJpaEntity(
                aggregateType = AggregateType.MESSAGE,
                aggregateId = message.channelId.toString(),
                payload = objectMapper.writeValueAsString(message),
                type = "MessageSent",
                timestamp = message.sentAt
            )
        )

        return message
    }

    override fun publish(activity: ChannelActivity): ChannelActivity {
        outboxJpaRepository.save(
            OutboxJpaEntity(
                aggregateType = AggregateType.CHANNEL_ACTIVITY,
                aggregateId = activity.channelId.toString(),
                payload = objectMapper.writeValueAsString(activity),
                type = activity.type.value,
                timestamp = activity.occurredAt
            )
        )

        return activity
    }

    override fun publishAll(activities: List<ChannelActivity>): List<ChannelActivity> {
        val outboxEntities = activities
            .map {
                OutboxJpaEntity(
                    aggregateType = AggregateType.CHANNEL_ACTIVITY,
                    aggregateId = it.channelId.toString(),
                    payload = objectMapper.writeValueAsString(it),
                    type = it.type.value,
                    timestamp = it.occurredAt
                )
            }

        outboxJpaRepository.saveAll(outboxEntities)

        return activities
    }

}