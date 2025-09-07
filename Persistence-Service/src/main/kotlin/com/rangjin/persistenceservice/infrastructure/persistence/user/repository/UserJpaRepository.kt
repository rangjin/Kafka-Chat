package com.rangjin.persistenceservice.infrastructure.persistence.user.repository

import com.rangjin.persistenceservice.infrastructure.persistence.user.entity.UserJpaEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository : JpaRepository<UserJpaEntity, Long> {

    fun findByIdGreaterThanOrderByIdAsc(id: Long, pageable: Pageable): List<UserJpaEntity>

}