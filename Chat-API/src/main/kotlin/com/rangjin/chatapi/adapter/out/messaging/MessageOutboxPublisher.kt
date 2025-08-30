package com.rangjin.chatapi.adapter.out.messaging

import com.fasterxml.jackson.databind.ObjectMapper
import com.rangjin.chatapi.adapter.out.events.MessageEvent
import com.rangjin.chatapi.adapter.out.persistence.outbox.repository.MessageOutboxJpaRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class MessageOutboxPublisher(

    @Value("\${spring.kafka.topic}")
    private val topic: String,

    private val kafkaTemplate: KafkaTemplate<Long, MessageEvent>,

    private val objectMapper: ObjectMapper,

    private val messageOutboxJpaRepository: MessageOutboxJpaRepository,

    private val messageOutboxStatusUpdater: MessageOutboxStatusUpdater

) {

    @Scheduled(fixedDelay = 500)
    fun publishBatch() {
        val batch = messageOutboxJpaRepository.findNextBatch(LocalDateTime.now())
        batch.forEach { it ->
            try {
                val event = objectMapper.readValue(it.payload, MessageEvent::class.java)
                kafkaTemplate.send(topic, event.channelId, event)
                messageOutboxStatusUpdater.markSent(it)
            } catch (_: Exception) {
                messageOutboxStatusUpdater.markFailed(it)
            }
        }
    }

}