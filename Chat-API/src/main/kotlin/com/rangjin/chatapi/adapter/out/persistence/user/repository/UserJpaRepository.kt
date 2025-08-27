package com.rangjin.chatapi.adapter.out.persistence.user.repository

import com.rangjin.chatapi.adapter.out.persistence.user.entity.UserJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository: JpaRepository<UserJpaEntity, Long> {

    fun findByEmail(email: String): UserJpaEntity?

}