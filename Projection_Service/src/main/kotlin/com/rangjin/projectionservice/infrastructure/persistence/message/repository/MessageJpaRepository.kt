package com.rangjin.projectionservice.infrastructure.persistence.message.repository

import com.rangjin.projectionservice.infrastructure.persistence.message.entity.MessageJpaEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface MessageJpaRepository : JpaRepository<MessageJpaEntity, Long> {

    fun findByIdGreaterThanOrderByIdAsc(id: Long, pageable: Pageable): List<MessageJpaEntity>

}