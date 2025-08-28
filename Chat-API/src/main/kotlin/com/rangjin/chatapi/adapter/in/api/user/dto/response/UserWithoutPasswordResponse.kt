package com.rangjin.chatapi.adapter.`in`.api.user.dto.response

import java.time.LocalDateTime

data class UserWithoutPasswordResponse (

    val id: Long? = null,

    val username: String,

    val email: String,

    val createdAt: LocalDateTime,

    val updatedAt: LocalDateTime

) {
}