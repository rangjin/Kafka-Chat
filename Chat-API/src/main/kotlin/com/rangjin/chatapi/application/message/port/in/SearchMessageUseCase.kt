package com.rangjin.chatapi.application.message.port.`in`

import com.rangjin.chatapi.domain.message.Message

interface SearchMessageUseCase {

    fun searchAfterSeq(userId: Long, channelId: Long, seq: Long): List<Message>

    fun searchByChannelIdAndContent(userId: Long, channelId: Long, keyword: String): List<Message>

}