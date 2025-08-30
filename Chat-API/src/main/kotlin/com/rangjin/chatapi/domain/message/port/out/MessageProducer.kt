package com.rangjin.chatapi.domain.message.port.out

import com.rangjin.chatapi.domain.message.model.Message

interface MessageProducer {

    fun send(message: Message): Message

}