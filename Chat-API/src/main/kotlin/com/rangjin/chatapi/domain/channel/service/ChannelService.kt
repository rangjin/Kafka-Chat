package com.rangjin.chatapi.domain.channel.service

import com.rangjin.chatapi.common.error.CustomException
import com.rangjin.chatapi.common.error.ErrorCode
import com.rangjin.chatapi.domain.channel.model.Channel
import com.rangjin.chatapi.domain.channel.port.`in`.command.CreateChannelCommand
import com.rangjin.chatapi.domain.channel.port.`in`.usecase.CreateChannelUseCase
import com.rangjin.chatapi.domain.channel.port.`in`.usecase.ReadMyChannelsUseCase
import com.rangjin.chatapi.domain.channel.port.out.persistence.ChannelRepository
import com.rangjin.chatapi.domain.user.port.out.persistence.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChannelService(

    private val channelRepository: ChannelRepository,

    private val userRepository: UserRepository

) : CreateChannelUseCase, ReadMyChannelsUseCase {

    @Transactional
    override fun createChannel(command: CreateChannelCommand): Channel {
        val user = userRepository.findById(command.userId)
            ?: throw CustomException(ErrorCode.USER_NOT_FOUND)
        val channel = Channel(
            name = command.name,
            members = listOf(user)
        )

        return channelRepository.save(channel)
    }

    @Transactional(readOnly = true)
    override fun getMyChannels(userId: Long): List<Channel> {
        if (!userRepository.existsById(userId)) throw CustomException(ErrorCode.USER_NOT_FOUND)

        return channelRepository.findByUserId(userId)
    }

}