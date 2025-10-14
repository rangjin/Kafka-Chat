package com.rangjin.chatapi.application.channel.service

import com.rangjin.chatapi.application.channel.port.`in`.CreateChannelUseCase
import com.rangjin.chatapi.application.channel.port.`in`.ReadMyChannelsUseCase
import com.rangjin.chatapi.application.event.DomainEvent
import com.rangjin.chatapi.application.event.DomainEventType
import com.rangjin.chatapi.application.event.MembershipEvent
import com.rangjin.chatapi.application.channel.port.out.ChannelRepository
import com.rangjin.chatapi.application.membership.port.out.MembershipRepository
import com.rangjin.chatapi.application.message.port.out.MessagePublisher
import com.rangjin.chatapi.application.user.port.out.UserRepository
import com.rangjin.chatapi.common.error.CustomException
import com.rangjin.chatapi.common.error.ErrorCode
import com.rangjin.chatapi.domain.channel.Channel
import com.rangjin.chatapi.domain.membership.Membership
import com.rangjin.chatapi.domain.membership.MembershipRole
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChannelService(

    private val channelRepository: ChannelRepository,

    private val userRepository: UserRepository,

    private val membershipRepository: MembershipRepository,

    private val messagePublisher: MessagePublisher

) : CreateChannelUseCase, ReadMyChannelsUseCase {

    @Transactional
    override fun createChannel(userId: Long, name: String): Channel {
        val user = userRepository.findById(userId)
            ?: throw CustomException(ErrorCode.USER_NOT_FOUND)

        val channel = channelRepository.save(Channel(name = name))
        val membership = membershipRepository.save(
            Membership(
                user = user,
                channel = channel,
                role = MembershipRole.OWNER,
            )
        )

        messagePublisher.publish(
            DomainEvent(
                aggregateId = membership.channel.id!!.toString(),
                className = Membership::class.simpleName!!,
                type = DomainEventType.CREATE,
                payload = MembershipEvent.Companion.fromDomain(membership)
            )
        )

        return membership.channel
    }

    @Transactional(readOnly = true)
    override fun getMyChannels(userId: Long): List<Channel> =
        if (!userRepository.existsById(userId))
            throw CustomException(ErrorCode.USER_NOT_FOUND)
        else
            membershipRepository.findAllByUserId(userId).map { it.channel }

}