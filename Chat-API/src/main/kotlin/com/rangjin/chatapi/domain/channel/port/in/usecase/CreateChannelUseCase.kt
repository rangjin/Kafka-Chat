package com.rangjin.chatapi.domain.channel.port.`in`.usecase

import com.rangjin.chatapi.domain.channel.model.Channel
import com.rangjin.chatapi.domain.channel.port.`in`.command.CreateChannelCommand

interface CreateChannelUseCase {

    fun createChannel(createChannelCommand: CreateChannelCommand): Channel

}