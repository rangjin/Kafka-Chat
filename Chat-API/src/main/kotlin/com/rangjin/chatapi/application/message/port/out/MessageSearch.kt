package com.rangjin.chatapi.application.message.port.out

import com.rangjin.chatapi.domain.message.Message

interface MessageSearch {

    fun searchByChannelIdAndContent(channelId: Long, keyword: String): List<Message>

}