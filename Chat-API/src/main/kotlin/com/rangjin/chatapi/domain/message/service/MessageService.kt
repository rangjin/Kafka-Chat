package com.rangjin.chatapi.domain.message.service

import com.rangjin.chatapi.domain.message.model.Message
import com.rangjin.chatapi.domain.message.port.`in`.command.SendMessageCommand
import com.rangjin.chatapi.domain.message.port.`in`.usecase.SendMessageUseCase
import com.rangjin.chatapi.domain.message.port.out.MessageProducer
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MessageService(

    private val messageProducer: MessageProducer

) : SendMessageUseCase {

    @Transactional
    override fun sendMessage(command: SendMessageCommand): Message =
        messageProducer.send(
            Message(
                channelId = command.channelId,
                senderId = command.senderId,
                content = command.content,
                sendAt = command.sendAt
            )
        )

}