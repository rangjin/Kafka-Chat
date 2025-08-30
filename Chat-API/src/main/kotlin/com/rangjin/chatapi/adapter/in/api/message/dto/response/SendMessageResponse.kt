package com.rangjin.chatapi.adapter.`in`.api.message.dto.response

import java.time.LocalDateTime

data class SendMessageResponse(

    val success: Boolean = true,

    val messageId: String,

    val sendAt: LocalDateTime

)