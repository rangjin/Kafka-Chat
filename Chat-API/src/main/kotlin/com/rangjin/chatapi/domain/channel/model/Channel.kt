package com.rangjin.chatapi.domain.channel.model

import com.rangjin.chatapi.domain.user.model.User
import java.time.LocalDateTime

data class Channel (

    val id: Long? = null,

    val name: String,

    val members: List<User> = emptyList(),

    val createdAt: LocalDateTime? = null,

    val updatedAt: LocalDateTime? = null

) {
}