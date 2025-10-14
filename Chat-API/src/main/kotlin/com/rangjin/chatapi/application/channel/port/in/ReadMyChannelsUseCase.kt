package com.rangjin.chatapi.application.channel.port.`in`

import com.rangjin.chatapi.domain.channel.Channel

interface ReadMyChannelsUseCase {

    fun getMyChannels(userId: Long): List<Channel>

}