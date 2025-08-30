package com.rangjin.chatapi.domain.channel.service

import com.rangjin.chatapi.common.error.CustomException
import com.rangjin.chatapi.common.error.ErrorCode
import com.rangjin.chatapi.domain.channel.model.Channel
import com.rangjin.chatapi.domain.channel.port.`in`.command.CreateChannelCommand
import com.rangjin.chatapi.domain.channel.port.`in`.usecase.CreateChannelUseCase
import com.rangjin.chatapi.domain.channel.port.out.persistence.ChannelRepository
import com.rangjin.chatapi.domain.user.port.out.persistence.UserRepository
import org.springframework.stereotype.Service

@Service
class ChannelService(

    private val channelRepository: ChannelRepository,

    private val userRepository: UserRepository

) : CreateChannelUseCase {

    override fun createChannel(createChannelCommand: CreateChannelCommand): Channel {
        val user = userRepository.findById(createChannelCommand.userId)
            ?: throw CustomException(ErrorCode.USER_NOT_FOUND)
        val channel = Channel(
            name = createChannelCommand.name,
            members = listOf(user)
        )

        return channelRepository.save(channel)
    }

}