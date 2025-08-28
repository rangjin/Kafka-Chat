package com.rangjin.chatapi.adapter.out.persistence.user.mapper

import com.rangjin.chatapi.adapter.out.persistence.user.entity.UserJpaEntity
import com.rangjin.chatapi.domain.user.model.User

fun UserJpaEntity.toDomain() = User(
    id = id,
    username = username,
    email = email,
    passwordHash = password,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun User.toEntity() = UserJpaEntity(
    id = id, username = username, email = email, password = passwordHash
)