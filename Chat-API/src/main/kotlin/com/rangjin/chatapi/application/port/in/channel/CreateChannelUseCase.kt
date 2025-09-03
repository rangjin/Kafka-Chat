package com.rangjin.chatapi.application.port.`in`.channel

import com.rangjin.chatapi.domain.channel.Channel

interface CreateChannelUseCase {

    fun createChannel(userId: Long, name: String): Channel

}