package com.rangjin.persistenceservice.infrastructure.persistence.channel.repository

import com.rangjin.persistenceservice.infrastructure.persistence.channel.entity.ChannelJpaEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface ChannelJpaRepository : JpaRepository<ChannelJpaEntity, Long> {

    fun findByIdGreaterThanOrderByIdAsc(id: Long, pageable: Pageable): List<ChannelJpaEntity>


}