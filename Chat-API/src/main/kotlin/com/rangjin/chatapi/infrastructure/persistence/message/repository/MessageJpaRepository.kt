package com.rangjin.chatapi.infrastructure.persistence.message.repository

import com.rangjin.chatapi.infrastructure.persistence.message.entity.MessageJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MessageJpaRepository: JpaRepository<MessageJpaEntity, Long> {
}