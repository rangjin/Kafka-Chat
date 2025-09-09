package com.rangjin.chatapi.infrastructure.persistence.outbox.repository

import com.fasterxml.jackson.databind.ObjectMapper
import com.rangjin.chatapi.application.event.DomainEvent
import com.rangjin.chatapi.application.port.out.message.MessagePublisher
import com.rangjin.chatapi.infrastructure.persistence.outbox.entity.OutboxJpaEntity
import org.springframework.stereotype.Component

@Component
class OutboxRepositoryAdapter(

    private val outboxJpaRepository: OutboxJpaRepository,

    private val objectMapper: ObjectMapper

) : MessagePublisher {

    override fun <T> publish(event: DomainEvent<T>) {
        outboxJpaRepository.save(
            OutboxJpaEntity(
                aggregateType = "event",
                aggregateId = event.aggregateId,
                payload = objectMapper.writeValueAsString(event),
                type = event.className,
                timestamp = event.timestamp
            )
        )
    }

    override fun <T> publishAll(events: List<DomainEvent<T>>) {
        val outboxEntities = events.map {
            OutboxJpaEntity(
                aggregateType = "event",
                aggregateId = it.aggregateId,
                payload = objectMapper.writeValueAsString(it),
                type = it.className,
                timestamp = it.timestamp
            )
        }

        outboxJpaRepository.saveAll(outboxEntities)
    }

}