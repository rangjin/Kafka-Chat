package com.rangjin.chatapi.adapter.out.messaging

import com.rangjin.chatapi.adapter.out.persistence.outbox.entity.MessageOutboxJpaEntity
import com.rangjin.chatapi.adapter.out.persistence.outbox.entity.MessageOutboxStatus
import com.rangjin.chatapi.adapter.out.persistence.outbox.repository.MessageOutboxJpaRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import kotlin.math.min
import kotlin.math.pow

@Component
class MessageOutboxStatusUpdater(

    private val messageOutboxJpaRepository: MessageOutboxJpaRepository

) {

    @Transactional
    fun markSent(row: MessageOutboxJpaEntity) {
        row.status = MessageOutboxStatus.SENT
        row.attempts += 1
        row.nextRetryAt = null
        messageOutboxJpaRepository.save(row)
    }

    @Transactional
    fun markFailed(row: MessageOutboxJpaEntity) {
        row.attempts += 1
        if (row.attempts >= 20) {
            row.status = MessageOutboxStatus.DEAD
            row.nextRetryAt = null
        } else {
            row.status = MessageOutboxStatus.FAILED
            val delaySec = min(3600.0, 2.0.pow(row.attempts.toDouble())).toLong()
            row.nextRetryAt = LocalDateTime.now().plusSeconds(delaySec)
        }
        messageOutboxJpaRepository.save(row)
    }

}