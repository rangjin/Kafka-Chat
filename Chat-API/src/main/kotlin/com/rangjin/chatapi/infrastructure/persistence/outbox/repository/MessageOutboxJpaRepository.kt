package com.rangjin.chatapi.infrastructure.persistence.outbox.repository

import com.rangjin.chatapi.infrastructure.persistence.outbox.entity.MessageOutboxJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MessageOutboxJpaRepository : JpaRepository<MessageOutboxJpaEntity, Long>