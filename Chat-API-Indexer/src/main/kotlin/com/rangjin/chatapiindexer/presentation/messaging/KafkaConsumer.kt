package com.rangjin.chatapiindexer.presentation.messaging

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.rangjin.chatapiindexer.domain.Message
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.annotation.RetryableTopic
import org.springframework.retry.annotation.Backoff
import org.springframework.stereotype.Component

@Component
class KafkaConsumer(

    private val objectMapper: ObjectMapper

) {

    @KafkaListener(
        topics = ["chat.event"],
        groupId = "indexer",
    )
    @RetryableTopic(
        attempts = "3",
        backoff = Backoff(delay = 1000, multiplier = 2.0),
        dltTopicSuffix = ".DLT",
        autoCreateTopics = "true"
    )
    fun onEvent(record: ConsumerRecord<String, String>) {
        val raw = record.value()

        if (objectMapper.readTree(raw).get("className")?.asText() != "Message") return

        val envelope: EventEnvelope<Message> = objectMapper.readValue(
            raw,
            object : TypeReference<EventEnvelope<Message>>() {}
        )

        // todo: 증분색인
    }

}