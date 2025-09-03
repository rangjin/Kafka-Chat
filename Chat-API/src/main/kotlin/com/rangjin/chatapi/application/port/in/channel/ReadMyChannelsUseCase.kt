package com.rangjin.chatapi.application.port.`in`.channel

import com.rangjin.chatapi.domain.channel.Channel

interface ReadMyChannelsUseCase {

    fun getMyChannels(userId: Long): List<Channel>

}