package com.rangjin.chatapi.domain.message.port.`in`.usecase

import com.rangjin.chatapi.domain.message.model.Message
import com.rangjin.chatapi.domain.message.port.`in`.command.SendMessageCommand

interface SendMessageUseCase {

    fun sendMessage(sendMessageCommand: SendMessageCommand): Message

}