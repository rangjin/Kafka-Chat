package com.rangjin.chatapi.application.service

import com.rangjin.chatapi.application.event.DomainEvent
import com.rangjin.chatapi.application.event.DomainEventType
import com.rangjin.chatapi.application.event.MembershipEvent
import com.rangjin.chatapi.application.port.`in`.channel.InvitationUseCase
import com.rangjin.chatapi.application.port.`in`.channel.WithdrawUseCase
import com.rangjin.chatapi.application.port.out.channel.ChannelRepository
import com.rangjin.chatapi.application.port.out.membership.MembershipRepository
import com.rangjin.chatapi.application.port.out.message.MessagePublisher
import com.rangjin.chatapi.application.port.out.user.UserRepository
import com.rangjin.chatapi.common.error.CustomException
import com.rangjin.chatapi.common.error.ErrorCode
import com.rangjin.chatapi.domain.membership.Membership
import com.rangjin.chatapi.domain.membership.MembershipRole
import com.rangjin.chatapi.domain.user.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MembershipService(

    private val channelRepository: ChannelRepository,

    private val userRepository: UserRepository,

    private val messagePublisher: MessagePublisher,

    private val membershipRepository: MembershipRepository

) : InvitationUseCase, WithdrawUseCase {

    @Transactional
    override fun invitationToChannel(
        userId: Long, channelId: Long, invitedEmails: List<String>
    ): List<User> {
        val channel = channelRepository.findById(channelId)
            ?: throw CustomException(ErrorCode.CHANNEL_NOT_FOUND)

        if (!membershipRepository.existsByUserIdAndChannelId(userId, channelId))
            throw CustomException(ErrorCode.NOT_IN_CHANNEL)

        val existingEmails = membershipRepository.findAllByChannelId(channelId)
            .map { it.user.email }

        val invitedUserMembership =
            userRepository.findByEmailIn(invitedEmails)
                .filter { it.email !in existingEmails }
                .map {
                    Membership(
                        channel = channel,
                        user = it,
                        role = MembershipRole.MEMBER
                    )
                }

        val memberships = membershipRepository.saveAll(invitedUserMembership)

        messagePublisher.publishAll(
            memberships.map {
                DomainEvent(
                    aggregateId = channelId.toString(),
                    className = Membership::class.simpleName!!,
                    type = DomainEventType.CREATE,
                    payload = MembershipEvent.fromDomain(it)
                )
            }
        )

        return memberships.map { it.user }
    }

    @Transactional
    override fun withdrawFromChannel(
        userId: Long, channelId: Long
    ): Boolean {
        if (!channelRepository.existsById(channelId))
            throw CustomException(ErrorCode.CHANNEL_NOT_FOUND)

        val membership = membershipRepository.findByUserIdAndChannelId(userId, channelId)
            ?: throw CustomException(ErrorCode.NOT_IN_CHANNEL)

        membershipRepository.delete(membership)

        messagePublisher.publish(
            DomainEvent(
                aggregateId = channelId.toString(),
                className = Membership::class.simpleName!!,
                type = DomainEventType.DELETE,
                payload = MembershipEvent.fromDomain(membership)
            )
        )

        return true
    }

}