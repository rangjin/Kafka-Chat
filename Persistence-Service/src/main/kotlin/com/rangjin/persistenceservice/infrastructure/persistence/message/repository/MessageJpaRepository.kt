package com.rangjin.persistenceservice.infrastructure.persistence.message.repository

import com.rangjin.persistenceservice.infrastructure.persistence.message.entity.MessageJpaEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface MessageJpaRepository : JpaRepository<MessageJpaEntity, Long> {

    fun findByIdGreaterThanOrderByIdAsc(id: Long, pageable: Pageable): List<MessageJpaEntity>

}