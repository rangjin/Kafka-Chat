package com.rangjin.chatapi.application.port.out.message

import com.rangjin.chatapi.domain.message.Message

interface MessageSearch {

    fun searchByChannelIdAndContent(channelId: Long, keyword: String): List<Message>

}