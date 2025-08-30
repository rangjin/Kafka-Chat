package com.rangjin.chatapi.adapter.out.persistence.outbox.repository

import com.rangjin.chatapi.adapter.out.persistence.outbox.entity.MessageOutboxJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MessageOutboxJpaRepository : JpaRepository<MessageOutboxJpaEntity, Long> {

}