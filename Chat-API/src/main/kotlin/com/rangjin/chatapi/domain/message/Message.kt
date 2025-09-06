package com.rangjin.chatapi.domain.message

import java.time.LocalDateTime
import java.util.UUID

data class Message(

    val id: Long? = null,

    val messageId: UUID,

    val channelId: Long,

    val senderId: Long,

    val content: String,

    val sentAt: LocalDateTime

)