package com.rangjin.chatapi.adapter.`in`.api.message.mapper

import com.rangjin.chatapi.adapter.`in`.api.message.dto.request.SendMessageRequest
import com.rangjin.chatapi.adapter.`in`.api.message.dto.response.SendMessageResponse
import com.rangjin.chatapi.domain.message.model.Message
import com.rangjin.chatapi.domain.message.port.`in`.command.SendMessageCommand

fun SendMessageRequest.toSendMessageCommand(channelId: Long, senderId: Long): SendMessageCommand =
    SendMessageCommand(channelId, senderId, message)

fun Message.toSendMessageResponse() =
    SendMessageResponse(
        messageId = messageId,
        sendAt = sendAt
    )