package com.rangjin.chatapi.infrastructure.events

import com.rangjin.chatapi.domain.message.Message

fun Message.toMessageEvent() =
    MessageEvent(messageId, channelId, senderId, content, sendAt)
