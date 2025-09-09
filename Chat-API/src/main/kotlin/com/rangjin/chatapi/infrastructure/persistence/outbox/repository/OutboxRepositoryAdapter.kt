package com.rangjin.chatapi.infrastructure.persistence.outbox.repository

import com.fasterxml.jackson.databind.ObjectMapper
import com.rangjin.chatapi.application.port.out.message.MessagePublisher
import com.rangjin.chatapi.domain.channel.ChannelEvent
import com.rangjin.chatapi.infrastructure.persistence.outbox.entity.OutboxJpaEntity
import org.springframework.stereotype.Component

@Component
class OutboxRepositoryAdapter(

    private val outboxJpaRepository: OutboxJpaRepository,

    private val objectMapper: ObjectMapper

) : MessagePublisher {

    override fun <T> publish(event: ChannelEvent<T>) {
        outboxJpaRepository.save(
            OutboxJpaEntity(
                aggregateType = "event",
                aggregateId = event.aggregateId,
                payload = objectMapper.writeValueAsString(event),
                type = event.payload!!::class.simpleName!!,
                timestamp = event.timestamp
            )
        )
    }

    override fun <T> publishAll(events: List<ChannelEvent<T>>) {
        val outboxEntities = events.map {
            OutboxJpaEntity(
                aggregateType = "event",
                aggregateId = it.aggregateId,
                payload = objectMapper.writeValueAsString(it),
                type = it.payload!!::class.simpleName!!,
                timestamp = it.timestamp
            )
        }

        outboxJpaRepository.saveAll(outboxEntities)
    }

}