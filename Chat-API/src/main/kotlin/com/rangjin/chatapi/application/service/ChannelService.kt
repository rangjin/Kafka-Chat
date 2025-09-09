package com.rangjin.chatapi.application.service

import com.rangjin.chatapi.application.port.`in`.channel.CreateChannelUseCase
import com.rangjin.chatapi.application.port.`in`.channel.ReadMyChannelsUseCase
import com.rangjin.chatapi.application.port.out.channel.ChannelRepository
import com.rangjin.chatapi.application.port.out.user.UserRepository
import com.rangjin.chatapi.common.error.CustomException
import com.rangjin.chatapi.common.error.ErrorCode
import com.rangjin.chatapi.domain.channel.Channel
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChannelService(

    private val channelRepository: ChannelRepository,

    private val userRepository: UserRepository,

) : CreateChannelUseCase, ReadMyChannelsUseCase {

    @Transactional
    override fun createChannel(userId: Long, name: String): Channel {
        val user = userRepository.findById(userId)
            ?: throw CustomException(ErrorCode.USER_NOT_FOUND)

        val channel = Channel(name = name)
        channel.addOwner(user)

        return channelRepository.save(channel)
    }

    @Transactional(readOnly = true)
    override fun getMyChannels(userId: Long): List<Channel> =
        if (!userRepository.existsById(userId))
            throw CustomException(ErrorCode.USER_NOT_FOUND)
        else
            channelRepository.findByUserId(userId)

}