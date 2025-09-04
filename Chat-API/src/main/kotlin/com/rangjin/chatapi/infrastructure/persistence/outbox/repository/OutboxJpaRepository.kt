package com.rangjin.chatapi.infrastructure.persistence.outbox.repository

import com.rangjin.chatapi.infrastructure.persistence.outbox.entity.OutboxJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface OutboxJpaRepository : JpaRepository<OutboxJpaEntity, Long>