package com.rangjin.chatapi.application.service

import com.rangjin.chatapi.application.port.`in`.message.SendMessageUseCase
import com.rangjin.chatapi.application.port.out.channel.ChannelRepository
import com.rangjin.chatapi.application.port.out.message.MessagePublisher
import com.rangjin.chatapi.application.port.out.message.MessageRepository
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

    private val channelRepository: ChannelRepository

) : SendMessageUseCase {

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

        messagePublisher.publish(message)

        channel.addLastSeq()
        channelRepository.save(channel)

        return message
    }


}