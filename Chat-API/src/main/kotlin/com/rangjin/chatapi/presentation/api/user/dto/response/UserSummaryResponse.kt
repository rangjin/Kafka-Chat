package com.rangjin.chatapi.presentation.api.user.dto.response

import com.rangjin.chatapi.domain.user.User
import java.time.LocalDateTime

data class UserSummaryResponse(

    val id: Long? = null,

    val username: String,

    val email: String,

    val createdAt: LocalDateTime?,

    val updatedAt: LocalDateTime?

) {

    companion object {
        fun from(user: User): UserSummaryResponse =
            UserSummaryResponse(
                id = user.id,
                username = user.username,
                email = user.email,
                createdAt = user.createdAt,
                updatedAt = user.updatedAt
            )
    }

}