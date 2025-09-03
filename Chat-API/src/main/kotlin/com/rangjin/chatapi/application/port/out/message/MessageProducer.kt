package com.rangjin.chatapi.application.port.out.message

import com.rangjin.chatapi.domain.message.Message

interface MessageProducer {

    fun send(message: Message): Message

}