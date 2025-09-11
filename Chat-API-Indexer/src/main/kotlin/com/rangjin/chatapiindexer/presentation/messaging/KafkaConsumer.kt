package com.rangjin.chatapiindexer.presentation.messaging

import com.fasterxml.jackson.databind.ObjectMapper
import com.rangjin.chatapiindexer.application.port.`in`.IncrementIndexer
import com.rangjin.chatapiindexer.domain.Message
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.annotation.RetryableTopic
import org.springframework.retry.annotation.Backoff
import org.springframework.stereotype.Component

@Component
class KafkaConsumer(

    private val objectMapper: ObjectMapper,

    private val indexer: IncrementIndexer

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
        val node = objectMapper.readTree(record.value())

        val effectiveNode =
            if (node.isTextual) objectMapper.readTree(node.asText())
            else node

        val envelope = objectMapper.treeToValue(effectiveNode, EventEnvelope::class.java)

        when (envelope.className) {
            "Message" -> {
                val msg = objectMapper.treeToValue(envelope.payload, Message::class.java)
                indexer.upsert(msg, msg.channelId.toString())
            }
            else -> return
        }
    }

}