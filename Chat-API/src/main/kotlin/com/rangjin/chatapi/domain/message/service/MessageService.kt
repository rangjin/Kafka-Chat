package com.rangjin.chatapi.domain.message.service

import com.rangjin.chatapi.domain.message.model.Message
import com.rangjin.chatapi.domain.message.port.`in`.usecase.SendMessageUseCase
import com.rangjin.chatapi.domain.message.port.`in`.command.SendMessageCommand
import com.rangjin.chatapi.domain.message.port.out.MessageProducer
import org.springframework.stereotype.Service

@Service
class MessageService(

    private val messageProducer: MessageProducer

): SendMessageUseCase {

    override fun sendMessage(sendMessageCommand: SendMessageCommand): Message =
        messageProducer.send(
            Message(
                channelId = sendMessageCommand.channelId,
                senderId = sendMessageCommand.senderId,
                content = sendMessageCommand.content,
                sendAt = sendMessageCommand.sendAt
            )
        )

}