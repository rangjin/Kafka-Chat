package com.rangjin.chatapi.presentation.api.message.dto.response

import com.rangjin.chatapi.domain.message.Message
import java.time.LocalDateTime

data class SendMessageResponse(

    val success: Boolean = true,

    val messageId: String,

    val sentAt: LocalDateTime

) {

    companion object {
        fun from(message: Message): SendMessageResponse =
            SendMessageResponse(
                messageId = message.messageId.toString(),
                sentAt = message.sentAt
            )
    }

}