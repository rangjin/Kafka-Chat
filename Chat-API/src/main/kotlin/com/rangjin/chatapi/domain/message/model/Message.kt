package com.rangjin.chatapi.domain.message.model

import java.time.LocalDateTime
import java.util.UUID

data class Message(

    val id: Long? = null,

    val messageId: String = UUID.randomUUID().toString(),

    val channelId: Long,

    val senderId: Long,

    val content: String,

    val sendAt: LocalDateTime,

    val createdAt: LocalDateTime? = null,

    val updatedAt: LocalDateTime? = null

)