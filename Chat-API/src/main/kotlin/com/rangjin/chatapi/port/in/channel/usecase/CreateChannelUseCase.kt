package com.rangjin.chatapi.port.`in`.channel.usecase

import com.rangjin.chatapi.domain.channel.model.Channel
import com.rangjin.chatapi.port.`in`.channel.command.CreateChannelCommand

interface CreateChannelUseCase {

    fun createChannel(createChannelCommand: CreateChannelCommand): Channel

}