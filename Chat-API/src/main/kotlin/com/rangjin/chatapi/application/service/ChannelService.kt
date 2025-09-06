package com.rangjin.chatapi.application.service

import com.rangjin.chatapi.application.port.`in`.channel.CreateChannelUseCase
import com.rangjin.chatapi.application.port.`in`.channel.InvitationUseCase
import com.rangjin.chatapi.application.port.`in`.channel.ReadMyChannelsUseCase
import com.rangjin.chatapi.application.port.`in`.channel.WithdrawUseCase
import com.rangjin.chatapi.application.port.out.channel.ChannelRepository
import com.rangjin.chatapi.application.port.out.message.MessagePublisher
import com.rangjin.chatapi.application.port.out.user.UserRepository
import com.rangjin.chatapi.common.error.CustomException
import com.rangjin.chatapi.common.error.ErrorCode
import com.rangjin.chatapi.domain.channel.Channel
import com.rangjin.chatapi.domain.channel.ChannelActivity
import com.rangjin.chatapi.domain.membership.MembershipRole
import com.rangjin.chatapi.domain.user.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class ChannelService(

    private val channelRepository: ChannelRepository,

    private val userRepository: UserRepository,

    private val messagePublisher: MessagePublisher

) : CreateChannelUseCase, ReadMyChannelsUseCase, InvitationUseCase, WithdrawUseCase {

    @Transactional
    override fun createChannel(userId: Long, name: String): Channel {
        val user=  userRepository.findById(userId)
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

    @Transactional
    override fun invitationToChannel(
        userId: Long, channelId: Long, invitedEmails: List<String>
    ): List<User> {
        val channel: Channel = channelRepository.findById(channelId)
            ?: throw CustomException(ErrorCode.CHANNEL_NOT_FOUND)

        if (!channel.existsMember(userId))
            throw CustomException(ErrorCode.NOT_IN_CHANNEL)

        val invitedUser =
            userRepository.findByEmailIn(invitedEmails)
                .filter { it -> !channel.existsMember(it.id!!) }

        invitedUser.forEach {
            channel.addMember(it)
        }

        channelRepository.save(channel)

        messagePublisher.publishAll(
            invitedUser.map {
                    ChannelActivity.Joined(
                        userId = it.id!!,
                        channelId = channelId,
                        role = MembershipRole.MEMBER,
                        occurredAt = LocalDateTime.now()
                    )}
        )

        return invitedUser
    }

    @Transactional
    override fun withdrawFromChannel(
        userId: Long, channelId: Long
    ): Boolean {
        val channel: Channel = channelRepository.findById(channelId)
            ?: throw CustomException(ErrorCode.CHANNEL_NOT_FOUND)

        if (!channel.existsMember(userId))
            throw CustomException(ErrorCode.NOT_IN_CHANNEL)

        val user = userRepository.findById(userId)
            ?: throw CustomException(ErrorCode.USER_NOT_FOUND)

        channel.removeMember(user)

        channelRepository.save(channel)

        messagePublisher.publish(
            ChannelActivity.Left(
                userId = userId,
                channelId = channelId,
                occurredAt = LocalDateTime.now()
            )
        )

        return true
    }

}