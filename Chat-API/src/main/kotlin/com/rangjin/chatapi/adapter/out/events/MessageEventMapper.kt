package com.rangjin.chatapi.adapter.out.events

import com.rangjin.chatapi.domain.message.model.Message

fun Message.toMessageEvent() =
    MessageEvent(messageId, channelId, senderId, content, sendAt)
