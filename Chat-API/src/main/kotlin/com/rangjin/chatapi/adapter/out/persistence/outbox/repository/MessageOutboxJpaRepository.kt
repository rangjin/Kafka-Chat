package com.rangjin.chatapi.adapter.out.persistence.outbox.repository

import com.rangjin.chatapi.adapter.out.persistence.outbox.entity.MessageOutboxJpaEntity
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

interface MessageOutboxJpaRepository : JpaRepository<MessageOutboxJpaEntity, Long> {

    @Query(
        "select m from MessageOutboxJpaEntity m " +
                "where m.status in ('SCHEDULED', 'FAILED') " +
                "and (m.nextRetryAt is null or m.nextRetryAt <= :now) " +
                "order by m.id asc"
    )
    fun findNextBatch(
        now: LocalDateTime,
        pageable: Pageable = PageRequest.of(0, 200)
    ): List<MessageOutboxJpaEntity>

}