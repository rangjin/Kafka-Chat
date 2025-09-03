package com.rangjin.chatapi.infrastructure.persistence.user.mapper

import com.rangjin.chatapi.domain.user.User
import com.rangjin.chatapi.infrastructure.persistence.user.entity.UserJpaEntity

object UserMapper {

    fun toJpa(domain: User): UserJpaEntity {
        return UserJpaEntity(
            domain.id,
            domain.username,
            domain.email,
            domain.passwordHash
        )
    }

    fun toDomain(jpa: UserJpaEntity): User =
        User(
            id = jpa.id,
            username = jpa.username,
            email = jpa.email,
            passwordHash = jpa.password,
            createdAt = jpa.createdAt,
            updatedAt = jpa.updatedAt
        )

}