package com.rangjin.chatapi.application.channel.port.`in`

import com.rangjin.chatapi.domain.channel.Channel

interface CreateChannelUseCase {

    fun createChannel(userId: Long, name: String): Channel

}