package com.rangjin.chatapi.domain.event

import java.time.LocalDateTime
import java.util.UUID

data class MessageSent(

    val messageId: UUID,

    val channelId: Long,

    val senderId: Long,

    val content: String,

    val sentAt: LocalDateTime

)
