package com.rangjin.chatapi.domain.message

import java.time.LocalDateTime
import java.util.*

data class Message(

    val id: Long? = null,

    val seq: Long,

    val messageId: UUID,

    val channelId: Long,

    val senderId: Long,

    val content: String,

    val sentAt: LocalDateTime

)