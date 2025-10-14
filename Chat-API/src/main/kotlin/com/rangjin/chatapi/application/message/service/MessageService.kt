package com.rangjin.chatapi.application.message.service

import com.rangjin.chatapi.application.event.DomainEvent
import com.rangjin.chatapi.application.event.DomainEventType
import com.rangjin.chatapi.application.message.port.`in`.SearchMessageUseCase
import com.rangjin.chatapi.application.message.port.`in`.SendMessageUseCase
import com.rangjin.chatapi.application.channel.port.out.ChannelRepository
import com.rangjin.chatapi.application.membership.port.out.MembershipRepository
import com.rangjin.chatapi.application.message.port.out.MessagePublisher
import com.rangjin.chatapi.application.message.port.out.MessageRepository
import com.rangjin.chatapi.application.message.port.out.MessageSearch
import com.rangjin.chatapi.common.error.CustomException
import com.rangjin.chatapi.common.error.ErrorCode
import com.rangjin.chatapi.domain.message.Message
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.UUID

@Service
class MessageService(

    private val messageRepository: MessageRepository,

    private val messagePublisher: MessagePublisher,

    private val channelRepository: ChannelRepository,

    private val membershipRepository: MembershipRepository,

    private val messageSearch: MessageSearch

) : SendMessageUseCase, SearchMessageUseCase {

    @Transactional
    override fun sendMessage(
        channelId: Long,
        senderId: Long,
        content: String,
        sendAt: LocalDateTime
    ): Message {
        val channel = channelRepository.findById(channelId)
            ?: throw CustomException(ErrorCode.CHANNEL_NOT_FOUND)

        val message = messageRepository.save(
            Message(
                seq = channel.lastSeq + 1,
                messageId = UUID.randomUUID(),
                channelId = channelId,
                senderId = senderId,
                content = content,
                sentAt = sendAt
            )
        )

        channel.addLastSeq()
        channelRepository.save(channel)

        messagePublisher.publish(
            DomainEvent(
                aggregateId = message.channelId.toString(),
                className = Message::class.simpleName!!,
                type = DomainEventType.CREATE,
                payload = message
            )
        )

        return message
    }

    override fun searchAfterSeq(
        userId: Long,
        channelId: Long,
        seq: Long
    ): List<Message> =
        if (membershipRepository.existsByUserIdAndChannelId(userId, channelId))
            messageRepository.findAllByChannelAndSeqAfter(channelId, seq)
        else
            throw CustomException(ErrorCode.NOT_IN_CHANNEL)

    override fun searchByChannelIdAndContent(
        userId: Long,
        channelId: Long,
        keyword: String
    ): List<Message> =
        if (membershipRepository.existsByUserIdAndChannelId(userId, channelId))
            messageSearch.searchByChannelIdAndContent(channelId, keyword)
        else
            throw CustomException(ErrorCode.NOT_IN_CHANNEL)

}