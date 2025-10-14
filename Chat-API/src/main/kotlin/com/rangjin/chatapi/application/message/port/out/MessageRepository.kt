package com.rangjin.chatapi.application.message.port.out

import com.rangjin.chatapi.domain.message.Message

interface MessageRepository {

    fun save(message: Message): Message

    fun findAllByChannelAndSeqAfter(channelId: Long, seq: Long): List<Message>

}