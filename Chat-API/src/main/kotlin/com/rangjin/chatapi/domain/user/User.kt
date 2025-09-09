package com.rangjin.chatapi.domain.user

import java.time.LocalDateTime

data class User(

    val id: Long? = null,

    val username: String,

    val email: String,

    val passwordHash: String,

    val createdAt: LocalDateTime? = null,

    val updatedAt: LocalDateTime? = null,

    )