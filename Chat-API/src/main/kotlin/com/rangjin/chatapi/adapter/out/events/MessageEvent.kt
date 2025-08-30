package com.rangjin.chatapi.adapter.out.events

import java.time.LocalDateTime

data class MessageEvent(

    val messageId: String,

    val channelId: Long,

    val senderId: Long,

    val content: String,

    val sendAt: LocalDateTime

)