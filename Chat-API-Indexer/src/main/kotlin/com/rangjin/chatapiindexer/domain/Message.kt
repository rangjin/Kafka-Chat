package com.rangjin.chatapiindexer.domain

import java.time.LocalDateTime

data class Message(

    val id: Long,

    val seq: Long,

    val messageId: String,

    val channelId: Long,

    val senderId: Long,

    val content: String,

    val sentAt: LocalDateTime,

    val createdAt: LocalDateTime,

    val updatedAt: LocalDateTime

)