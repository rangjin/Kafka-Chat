package com.rangjin.chatapi.infrastructure.persistence.user.repository

import com.rangjin.chatapi.infrastructure.persistence.user.entity.UserJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository : JpaRepository<UserJpaEntity, Long> {

    fun findByEmail(email: String): UserJpaEntity?

}