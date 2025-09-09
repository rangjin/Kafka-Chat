package com.rangjin.chatapi.application.service

import com.rangjin.chatapi.application.port.`in`.channel.InvitationUseCase
import com.rangjin.chatapi.application.port.`in`.channel.WithdrawUseCase
import com.rangjin.chatapi.application.port.out.channel.ChannelRepository
import com.rangjin.chatapi.application.port.out.membership.MembershipRepository
import com.rangjin.chatapi.application.port.out.message.MessagePublisher
import com.rangjin.chatapi.application.port.out.user.UserRepository
import com.rangjin.chatapi.common.error.CustomException
import com.rangjin.chatapi.common.error.ErrorCode
import com.rangjin.chatapi.domain.channel.Channel
import com.rangjin.chatapi.domain.channel.ChannelEvent
import com.rangjin.chatapi.domain.channel.ChannelEventType
import com.rangjin.chatapi.domain.membership.Membership
import com.rangjin.chatapi.domain.membership.MembershipRole
import com.rangjin.chatapi.domain.user.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

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
        val channel: Channel = channelRepository.findById(channelId)
            ?: throw CustomException(ErrorCode.CHANNEL_NOT_FOUND)

        if (!channel.existsMember(userId))
            throw CustomException(ErrorCode.NOT_IN_CHANNEL)

        val invitedUserMembership =
            userRepository.findByEmailIn(invitedEmails)
                .filter { it -> !channel.existsMember(it.id!!) }
                .map {
                    Membership(
                        channelId = channelId,
                        user = it,
                        role = MembershipRole.MEMBER,
                        joinedAt = LocalDateTime.now()
                    )
                }

        val memberships = membershipRepository.saveAll(invitedUserMembership)

        messagePublisher.publishAll(
            memberships.map {
                ChannelEvent(
                    aggregateId = channelId.toString(),
                    type = ChannelEventType.CREATE,
                    payload = it
                )
            }
        )

        return memberships.map { it.user }
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

        val deleteMembership = channel.removeMember(user)

        channelRepository.save(channel)

        messagePublisher.publish(
            ChannelEvent(
                aggregateId = deleteMembership.channelId!!.toString(),
                type = ChannelEventType.DELETE,
                payload = deleteMembership,
            )
        )

        return true
    }

}