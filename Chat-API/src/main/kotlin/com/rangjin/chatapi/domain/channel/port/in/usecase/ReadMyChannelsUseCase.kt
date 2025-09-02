package com.rangjin.chatapi.domain.channel.port.`in`.usecase

import com.rangjin.chatapi.domain.channel.model.Channel

interface ReadMyChannelsUseCase {

    fun getMyChannels(userId: Long): List<Channel>

}